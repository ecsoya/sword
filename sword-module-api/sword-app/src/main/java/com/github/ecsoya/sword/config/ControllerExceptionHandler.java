package com.github.ecsoya.sword.config;

import org.apache.shiro.session.InvalidSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.exception.user.UserNotLoginException;

/**
 * The Class ControllerExceptionHandler.
 */
@ControllerAdvice // 只负责处理异常
public class ControllerExceptionHandler {

	/**
	 * Handle user not exist exception.
	 *
	 * @param ex the ex
	 * @return the common result
	 */
	@ExceptionHandler(value = { UserNotLoginException.class, InvalidSessionException.class })
	@ResponseBody
	@ResponseStatus
	public CommonResult<?> handleUserNotExistException(UserNotLoginException ex) {
		return CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "用户未登录");
	}

	/**
	 * Handle http request method not supported exception.
	 *
	 * @param ex the ex
	 * @return the common result
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	@ResponseStatus
	public CommonResult<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		return CommonResult.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "类型不支持");
	}
}