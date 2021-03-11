package com.soyatec.sword.config;

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
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.exception.user.UserNotLoginException;

public class ShiroAccessControllerFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			Subject subject = getSubject(request, response);
			// If principal is not null, then the user is known and should be allowed
			// access.
			return subject.getPrincipal() != null;
		}
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

		// 前端http请求中code为403的时候跳转到登陆页，R.fail()为你返回给前端的json对象
		renderJson(httpServletResponse, CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "未登陆"));

		return false;
	}

	public static void renderJson(HttpServletResponse response, Object jsonObject) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(jsonObject));
		} catch (IOException e) {
			throw new UserNotLoginException();
		}
	}

}
