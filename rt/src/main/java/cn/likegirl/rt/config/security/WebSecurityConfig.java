package cn.likegirl.rt.config.security;


import cn.likegirl.rt.config.security.filter.AjaxAwareUsernamePasswordAuthenticationFilter;
import cn.likegirl.rt.config.security.filter.AuthenticationTokenFilter;
import cn.likegirl.rt.config.security.handler.AjaxAwareAuthenticationFailureHandler;
import cn.likegirl.rt.config.security.handler.AjaxAwareAuthenticationSuccessHandler;
import cn.likegirl.rt.config.security.handler.AuthenticationLogoutSuccessHandler;
import cn.likegirl.rt.config.security.handler.AuthenticationAccessDeniedHandler;
import cn.likegirl.rt.config.security.point.RestAuthenticationEntryPoint;
import cn.likegirl.rt.config.security.provider.AuthUserAuthenticationProvider;
import cn.likegirl.rt.config.security.service.AuthUserDetailsService;
import cn.likegirl.rt.config.security.service.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthUserAuthenticationProvider authenticationProvider;

    @Autowired
    AuthUserDetailsService authUserService;

    @Autowired
    PasswordEncoderService passwordEncoderService;

    @Autowired
    RestAuthenticationEntryPoint authenticationEntryPoint;



    /**
     * configure(WebSecurity): Web层面的配置，一般用来配置无需安全检查的路径
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/session/status", "/static/**", "/error/**", "/druid/**");
    }

    /**
     * configure(HttpSecurity): Request层面的配置，对应XML Configuration中的<http>元素
     * <p>
     * anonymous ：允许匿名访问
     * permitAll ：任何人都允许访问
     * hasRole ：指定角色允许访问
     * hasAuthority ：拥有权限允许访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //解决不允许显示在iframe的问题
            .headers().frameOptions().disable()
            .and()
            .cors()
            .and()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            // 取消 session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 匿名访问
            .authorizeRequests()
            .antMatchers("/login","/login-01", "/login-error", "/register", "/user/**", "/forgot/**", "/captchas/**", "/help", "/my-websocket", "/my-websocket/**")
            .anonymous()
//                .permitAll()
            // 设置
            // 其他所有资源都需要权限
            .anyRequest().authenticated()
            .and()
            // 用重写的Filter替换掉原有的UsernamePasswordAuthenticationFilter
            .addFilterAt(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            //使用jwt的Authentication
            .addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
            // 这里不需要配置 formLogin()，若配置，则会开启原有的UsernamePasswordAuthenticationFilter验证
            // 和我们自定义的 usernamePasswordAuthenticationFilter()并不冲突，两套验证都能使用
            // 自定义 usernamePasswordAuthenticationFilter() 完全兼容
            // UsernamePasswordAuthenticationFilter,使用 from-data
            // usernamePasswordAuthenticationFilter(),使用 application/json
//            .formLogin()
//            .loginPage("/login")
//            .successHandler(successHandler())
//            .failureHandler(failureHandler())
//            .permitAll()
//            .and()
            // 登出项配置
            .logout()
            .invalidateHttpSession(true)
            .logoutUrl("/logout")
            .logoutSuccessHandler(logoutSuccessHandler())
            .permitAll();

        // 禁用headers缓存
        http.headers().cacheControl();

    }

    /**
     * configure(AuthenticationManagerBuilder): 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // security 可以配置多认证器（provider）
        // 只需要有一个provider认证通过，即认证成功
        auth/*.authenticationProvider(authenticationProvider())*/
                .authenticationProvider(daoAuthenticationProvider());
        // 设置 userDetailsService 和 passwordEncoder
        // 会启用security 默认配置的 provider => DaoAuthenticationProvider
        // 在这里我们将不适用系统默认的DaoAuthenticationProvider,不必配置此项
//        auth.userDetailsService(authUserService).passwordEncoder(passwordEncoderService)
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return new AjaxAwareAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler failureHandler(){
        return new AjaxAwareAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AuthenticationAccessDeniedHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new AuthenticationLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthUserAuthenticationProvider();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(authUserService);
        provider.setPasswordEncoder(passwordEncoderService);
        provider.setHideUserNotFoundExceptions(Boolean.FALSE);
        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        // 加点盐
        saltSource.setUserPropertyToUse("credentialsSalt");
        provider.setSaltSource(saltSource);
        return provider;
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter(){
        return new AuthenticationTokenFilter();
    }

    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authentication = new AjaxAwareUsernamePasswordAuthenticationFilter();
        authentication.setAuthenticationSuccessHandler(successHandler());
        authentication.setAuthenticationFailureHandler(failureHandler());
        authentication.setAuthenticationManager(this.authenticationManager());
        authentication.setFilterProcessesUrl("/login");
        return authentication;
    }

    /**
     * share AuthenticationManager for web and oauth
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




}
