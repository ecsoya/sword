package com.github.ecsoya.sword.framework.shiro.web.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * The Class AdminUserFilter.
 */
public class AdminUserFilter extends UserFilter {

	/**
	 * Redirect to login.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		WebUtils.issueRedirect(request, response, loginUrl, null, true, false);
	}
}
