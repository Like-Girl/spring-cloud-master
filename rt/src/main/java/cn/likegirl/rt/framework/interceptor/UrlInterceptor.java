package cn.likegirl.rt.framework.interceptor;

import cn.likegirl.rt.framework.annotations.ResponseResult;
import cn.likegirl.rt.utils.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


/**
 * ResponseResult 注解处理
 *
 * @author LikeGirl
 */
@Component
public class UrlInterceptor implements HandlerInterceptor {

	private final static Logger LOGGER = LoggerFactory.getLogger(UrlInterceptor.class);

	public static final String RESPONSE_RESULT = "RESPONSE-RESULT";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LOGGER.info("uri[{}] ip[{}] isLocal[{}]",request.getRequestURI(),IpUtil.getClientIpAddr(request),IpUtil.isLocalIp(request));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// nothing to do
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// nothing to do
	}

}
