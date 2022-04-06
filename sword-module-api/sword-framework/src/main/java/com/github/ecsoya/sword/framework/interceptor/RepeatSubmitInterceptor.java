package com.github.ecsoya.sword.framework.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.json.JSON;
import com.github.ecsoya.sword.common.utils.ServletUtils;

/**
 * The Class RepeatSubmitInterceptor.
 */
@Component
public abstract class RepeatSubmitInterceptor implements AsyncHandlerInterceptor {

	/**
	 * Pre handle.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param handler  the handler
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			final HandlerMethod handlerMethod = (HandlerMethod) handler;
			final Method method = handlerMethod.getMethod();
			final RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
			if (annotation != null && this.isRepeatSubmit(request)) {
//					AjaxResult ajaxResult = AjaxResult.error("不允许重复提交，请稍后再试");
				final CommonResult<?> result = CommonResult.fail("重复提交，请稍后再试");
				ServletUtils.renderString(response, JSON.marshal(result));
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if is repeat submit.
	 *
	 * @param request the request
	 * @return true, if is repeat submit
	 * @throws Exception the exception
	 */
	public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;
}
