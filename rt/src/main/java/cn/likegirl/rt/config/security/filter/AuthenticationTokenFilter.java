package cn.likegirl.rt.config.security.filter;

import cn.likegirl.rt.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;

/**
 *  token 校验
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");
        if (authToken != null && authToken.startsWith(JwtUtil.JWT_SEPARATOR)) {
            try {
                // The part after "Bearer "
                Key key = JwtUtil.generateKey(JwtUtil.JWT_ALG,JwtUtil.JWT_RULE);
                String username = JwtUtil.parseJWT(key,authToken).getBody().getSubject();
                logger.info("checking authentication " + username);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 待更改 根据需要确定
                    if (JwtUtil.checkJWT(authToken, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        logger.info("authenticated user " + username + ", setting security context");
                        // 如果身份验证成功，那么最终的认证对象将被放置到当前线程的SecurityContext中
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            catch (ExpiredJwtException e) {
                logger.error("Token已过期: {} " + e);
            } catch (UnsupportedJwtException e) {
                logger.error("Token格式错误: {} " + e);
            } catch (MalformedJwtException e) {
                logger.error("Token没有被正确构造: {} " + e);
            } catch (SignatureException e) {
                logger.error("签名失败: {} " + e);
            } catch (IllegalArgumentException e) {
                logger.error("非法参数异常: {} " + e);
            }
        }
        chain.doFilter(request, response);
    }
}
