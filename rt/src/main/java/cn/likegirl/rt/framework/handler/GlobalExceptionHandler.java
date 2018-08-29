package cn.likegirl.rt.framework.handler;

import cn.likegirl.rt.constant.ResultCode;
import cn.likegirl.rt.framework.exception.BusinessException;
import cn.likegirl.rt.framework.response.DefaultErrorResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * @desc 统一异常处理器
 * 
 * @author LikeGirl
 * @since 8/31/2017 3:00 PM
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends BaseGlobalExceptionHandler {

	/** 处理400类异常 */
	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
		return super.handleConstraintViolationException(e, request);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
		return super.handleConstraintViolationException(e, request);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
		return super.handleBindException(e, request);
	}

	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		return super.handleMethodArgumentNotValidException(e, request);
	}

	/** 处理自定义异常 */
	@Override
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
		return super.handleBusinessException(e, request);
	}

	/**
	 * 500 处理运行时异常
	 *
	 * @param e		RuntimeException
	 * @param request	HttpServletRequest
	 */
	@Override
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public DefaultErrorResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
		//TODO 可通过邮件、微信公众号等方式发送信息至开发人员、记录存档等操作
		return super.handleRuntimeException(e, request);
	}

	/**
	 * 404 异常信息
	 *
	 * @param e		NoHandlerFoundException
	 * @param request	HttpServletRequest
	 */
	@Override
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value= HttpStatus.NOT_FOUND)
	public DefaultErrorResult handleNoHandlerFoundException(NoHandlerFoundException e, HttpServletRequest request) {
		return super.handleNoHandlerFoundException(e,request);
	}

	//=============================================================================================================

}
