package com.github.ecsoya.sword.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.exception.user.UserNotLoginException;

/**
 * The Class ShiroAccessControllerFilter.
 */
public class ShiroAccessControllerFilter extends AccessControlFilter {

	/**
	 * Checks if is access allowed.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param mappedValue the mapped value
	 * @return true, if is access allowed
	 * @throws Exception the exception
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			final Subject subject = getSubject(request, response);
			// If principal is not null, then the user is known and should be allowed
			// access.
			return subject.getPrincipal() != null;
		}
	}

	/**
	 * On access denied.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		final HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

		// 前端http请求中code为403的时候跳转到登陆页，R.fail()为你返回给前端的json对象
		renderJson(httpServletResponse, CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "未登陆")); //$NON-NLS-1$

		return false;
	}

	/**
	 * Render json.
	 *
	 * @param response   the response
	 * @param jsonObject the json object
	 */
	public static void renderJson(HttpServletResponse response, Object jsonObject) {
		try {
			response.setContentType("application/json"); //$NON-NLS-1$
			response.setCharacterEncoding("UTF-8"); //$NON-NLS-1$
			final PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(jsonObject));
		} catch (final IOException e) {
			throw new UserNotLoginException();
		}
	}

}
