package com.soyatec.sword.framework.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.json.JSON;
import com.soyatec.sword.common.utils.ServletUtils;

/**
 * 防止重复提交拦截器
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Component
public abstract class RepeatSubmitInterceptor implements AsyncHandlerInterceptor {
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
	 * 验证是否重复提交由子类实现具体的防重复提交的规则
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;
}
