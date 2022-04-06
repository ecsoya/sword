package com.github.ecsoya.sword.common.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.ServletUtils;

/**
 * The Class ServerConfig.
 */
@Component
public class ServerConfig {

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		final HttpServletRequest request = ServletUtils.getRequest();
		return getDomain(request);
	}

	/**
	 * Gets the context path.
	 *
	 * @return the context path
	 */
	public String getContextPath() {
		final HttpServletRequest request = ServletUtils.getRequest();
		return request.getServletContext().getContextPath();
	}

	/**
	 * Gets the ws url.
	 *
	 * @return the ws url
	 */
	public String getWsUrl() {
		final HttpServletRequest request = ServletUtils.getRequest();
		return getDomain(request).replaceAll("https", "wss").replaceAll("http", "ws") + "/ws";
	}

	/**
	 * Gets the domain.
	 *
	 * @param request the request
	 * @return the domain
	 */
	public static String getDomain(HttpServletRequest request) {
		final StringBuffer url = request.getRequestURL();
		final String contextPath = request.getServletContext().getContextPath();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
	}
}
