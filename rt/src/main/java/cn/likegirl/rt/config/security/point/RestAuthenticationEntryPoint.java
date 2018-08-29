package cn.likegirl.rt.config.security.point;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.likegirl.rt.constant.Const;
import cn.likegirl.rt.constant.ResultCode;
import cn.likegirl.rt.framework.response.DefaultErrorResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(Const.CHARSET_UTF8);
//		response.sendError(HttpStatus.UNAUTHORIZED.value(), "no Unauthorized");
		objectMapper.writeValue(response.getWriter(), DefaultErrorResult.failure(ResultCode.USER_NOT_LOGGED_IN,e,HttpStatus.UNAUTHORIZED));
	}
}