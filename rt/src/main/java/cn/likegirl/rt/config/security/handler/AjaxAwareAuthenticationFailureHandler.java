package cn.likegirl.rt.config.security.handler;

import cn.likegirl.rt.constant.Const;
import cn.likegirl.rt.constant.ResultCode;
import cn.likegirl.rt.framework.response.DefaultErrorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxAwareAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Const.CHARSET_UTF8);
        if(exception instanceof UsernameNotFoundException){
            objectMapper.writeValue(response.getWriter(), DefaultErrorResult.failure(ResultCode.USER_NOT_EXIST,exception,HttpStatus.UNAUTHORIZED));
        }else if (exception instanceof BadCredentialsException) {
            objectMapper.writeValue(response.getWriter(), DefaultErrorResult.failure(ResultCode.USER_PASSWORD_ERROR,exception,HttpStatus.UNAUTHORIZED));
        }
        objectMapper.writeValue(response.getWriter(), DefaultErrorResult.failure(ResultCode.USER_LOGIN_ERROR,exception,HttpStatus.UNAUTHORIZED));
    }

}
