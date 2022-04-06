package com.github.ecsoya.sword.framework.web.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.exception.DemoModeException;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.security.PermissionUtils;

/**
 * The Class GlobalExceptionHandler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Handle authorization exception.
	 *
	 * @param request the request
	 * @param e       the e
	 * @return the object
	 */
	@ExceptionHandler(AuthorizationException.class)
	public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
		log.error(e.getMessage(), e);
		if (ServletUtils.isAjaxRequest(request)) {
			return AjaxResult.error(PermissionUtils.getMsg(e.getMessage()));
		} else {
			final ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("error/unauth");
			return modelAndView;
		}
	}

	/**
	 * Handle exception.
	 *
	 * @param e the e
	 * @return the ajax result
	 */
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public AjaxResult handleException(HttpRequestMethodNotSupportedException e) {
		log.error(e.getMessage(), e);
		return AjaxResult.error("不支持' " + e.getMethod() + "'请求");
	}

	/**
	 * Not fount.
	 *
	 * @param e the e
	 * @return the ajax result
	 */
	@ExceptionHandler(RuntimeException.class)
	public AjaxResult notFount(RuntimeException e) {
		log.error("运行时异常:", e);
		return AjaxResult.error("运行时异常:" + e.getMessage());
	}

	/**
	 * Business exception.
	 *
	 * @param request the request
	 * @param e       the e
	 * @return the object
	 */
//	@ExceptionHandler(Exception.class)
//	public AjaxResult handleException(Exception e) {
//		log.error(e.getMessage(), e);
//		return AjaxResult.error("服务器错误，请联系管理员");
//	}

	/**
	 * @param request the request
	 * @param e       exception
	 * @return result
	 */
	@ExceptionHandler(BusinessException.class)
	public Object businessException(HttpServletRequest request, BusinessException e) {
		log.error(e.getMessage(), e);
		if (ServletUtils.isAjaxRequest(request)) {
			return AjaxResult.error(e.getMessage());
		} else {
			final ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("errorMessage", e.getMessage());
			modelAndView.setViewName("error/business");
			return modelAndView;
		}
	}

	/**
	 * Validated bind exception.
	 *
	 * @param e the e
	 * @return the ajax result
	 */
	@ExceptionHandler(BindException.class)
	public AjaxResult validatedBindException(BindException e) {
		log.error(e.getMessage(), e);
		final String message = e.getAllErrors().get(0).getDefaultMessage();
		return AjaxResult.error(message);
	}

	/**
	 * Demo mode exception.
	 *
	 * @param e the e
	 * @return the ajax result
	 */
	@ExceptionHandler(DemoModeException.class)
	public AjaxResult demoModeException(DemoModeException e) {
		return AjaxResult.error("演示模式，不允许操作");
	}
}
