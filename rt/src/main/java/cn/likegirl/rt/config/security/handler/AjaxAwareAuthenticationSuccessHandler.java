package cn.likegirl.rt.config.security.handler;

import cn.likegirl.rt.config.security.model.AuthUser;
import cn.likegirl.rt.constant.Const;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthUser user = (AuthUser) authentication.getPrincipal();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Const.CHARSET_UTF8);
        String token = JwtUtil.buildJWT(user.getUsername());
        Map<String,Object> info = new HashMap<>();
        info.put("token",token);
        objectMapper.writeValue(response.getWriter(), PlatformResult.success(info));
    }
}
