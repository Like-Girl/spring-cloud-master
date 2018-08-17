package cn.likegirl.rt.config.security.handler;

import cn.likegirl.rt.constant.ResultCode;
import cn.likegirl.rt.framework.response.DefaultErrorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
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
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (exception instanceof BadCredentialsException) {
            objectMapper.writeValue(response.getWriter(), DefaultErrorResult.failure(ResultCode.SYSTEM_INNER_ERROR,exception,HttpStatus.UNAUTHORIZED));
        }
        objectMapper.writeValue(response.getWriter(), DefaultErrorResult.failure(ResultCode.NO_SUCH_METHOD_EXCEPTION,exception,HttpStatus.UNAUTHORIZED));
    }




}
