package cn.likegirl.rt.config.security.filter;

import cn.likegirl.rt.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader("Authorization");

        if (authToken != null && authToken.startsWith(JwtUtil.JWT_SEPARATOR)) {
            try {
                // The part after "Bearer "
                String username = JwtUtil.parseJWT(JwtUtil.generateKey(JwtUtil.JWT_ALG),authToken).getBody().getSubject();
                logger.info("checking authentication " + username);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    // 待更改
                    if (JwtUtil.checkJWT(authToken, userDetails.getUsername())) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        logger.info("authenticated user " + username + ", setting security context");
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
