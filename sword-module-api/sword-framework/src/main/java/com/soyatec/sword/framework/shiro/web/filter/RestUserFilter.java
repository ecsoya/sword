package com.soyatec.sword.framework.shiro.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.soyatec.sword.common.core.domain.CommonResult;

public class RestUserFilter extends UserFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return true;
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		// 请求被拦截后直接返回json格式的响应数据
		response.getWriter().write(JSON.toJSONString(CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "Not Login")));
		response.getWriter().flush();
		response.getWriter().close();
	}
}
