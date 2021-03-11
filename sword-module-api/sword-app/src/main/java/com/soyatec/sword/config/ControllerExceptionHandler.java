package com.soyatec.sword.config;

import org.apache.shiro.session.InvalidSessionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.exception.user.UserNotLoginException;

@ControllerAdvice // 只负责处理异常
public class ControllerExceptionHandler {

	@ExceptionHandler(value = { UserNotLoginException.class, InvalidSessionException.class })
	@ResponseBody
	@ResponseStatus
	public CommonResult<?> handleUserNotExistException(UserNotLoginException ex) {
		return CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "用户未登录");
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	@ResponseStatus
	public CommonResult<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
		return CommonResult.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), "类型不支持");
	}
}