package cn.likegirl.rt.framework.handler;

import cn.likegirl.rt.framework.annotations.ResponseResult;
import cn.likegirl.rt.framework.interceptor.ResponseResultInterceptor;
import cn.likegirl.rt.framework.response.DefaultErrorResult;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import cn.likegirl.rt.utils.FastJsonConvertUtil;
import cn.likegirl.rt.utils.RequestContextUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LikeGirl
 */
@ControllerAdvice
public class LogResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        ResponseResult responseResultAnn = (ResponseResult) RequestContextUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        return responseResultAnn != null;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (LOGGER.isDebugEnabled()) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
            String requestUriWithoutContextPath = servletRequest.getRequestURI().substring(servletRequest.getContextPath().length());
            LOGGER.debug("uri={} | responseBody={}", requestUriWithoutContextPath, FastJsonConvertUtil.convertObjectToJSON(body));
        }
        ResponseResult responseResultAnn = (ResponseResult) RequestContextUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);

        Class<? extends Result> resultClazz = responseResultAnn.value();

        if (resultClazz.isAssignableFrom(PlatformResult.class)) {
            if (body instanceof DefaultErrorResult) {
                DefaultErrorResult defaultErrorResult = (DefaultErrorResult) body;
                PlatformResult platformResult = new PlatformResult();
                BeanUtils.copyProperties(defaultErrorResult,platformResult);
                return platformResult;
            } else if (body instanceof String) {
                return JSONObject.toJSONString(PlatformResult.success(body));
            }
            return PlatformResult.success(body);
        }
        return body;
    }
}
