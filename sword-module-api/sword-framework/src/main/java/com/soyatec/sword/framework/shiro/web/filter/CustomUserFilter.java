package com.soyatec.sword.framework.shiro.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;

import com.soyatec.sword.common.utils.StringUtils;

public class CustomUserFilter extends UserFilter {

	/**
	 * 本地设置的url
	 */
	@Value("${shiro.user.loginUrl}")
	private String loginUrl;

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (StringUtils.isEmpty(loginUrl)) {
			return true; // 如果登录地址不可以，直接返回
		}
		return super.onAccessDenied(request, response);
	}

	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		// 修复https跳转不到登录页面的BUG
		WebUtils.issueRedirect(request, response, loginUrl, null, true, false);
	}
}
