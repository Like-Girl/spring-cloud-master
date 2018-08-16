package cn.likegirl.rt.config.security;


import cn.likegirl.rt.config.security.filter.JwtAuthenticationTokenFilter;
import cn.likegirl.rt.config.security.handler.AuthLogoutSuccessHandler;
import cn.likegirl.rt.config.security.provider.AuthUserAuthenticationProvider;
import cn.likegirl.rt.config.security.service.AuthUserDetailsService;
import cn.likegirl.rt.config.security.service.PasswordEncoderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
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
        http.cors()
                .and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/login-error", "/register", "/user/register/**", "/forgot/**", "/captchas/**", "/help")
                .anonymous()
                // 设置
                // 其他所有资源都需要权限
                .anyRequest().authenticated()
                // Filter
                .and()
                .addFilter(new JwtAuthenticationTokenFilter())
                // 登出项配置
                .logout()
                .invalidateHttpSession(true)
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();

    }

    /**
     * configure(AuthenticationManagerBuilder): 身份验证配置，用于注入自定义身份验证Bean和密码校验规则
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider())
                .userDetailsService(authUserService).passwordEncoder(passwordEncoderService);
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler(){
        return new AuthLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        return new AuthUserAuthenticationProvider();
    }
}
