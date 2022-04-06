package com.github.ecsoya.sword.common.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.ecsoya.sword.common.core.text.Convert;

/**
 * The Class ServletUtils.
 */
public class ServletUtils {

	/** The Constant agent. */
	private final static String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };

	/**
	 * Gets the parameter.
	 *
	 * @param name the name
	 * @return the parameter
	 */
	public static String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	/**
	 * Gets the parameter.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the parameter
	 */
	public static String getParameter(String name, String defaultValue) {
		return Convert.toStr(getRequest().getParameter(name), defaultValue);
	}

	/**
	 * Gets the parameter to int.
	 *
	 * @param name the name
	 * @return the parameter to int
	 */
	public static Integer getParameterToInt(String name) {
		return Convert.toInt(getRequest().getParameter(name));
	}

	/**
	 * Gets the parameter to int.
	 *
	 * @param name         the name
	 * @param defaultValue the default value
	 * @return the parameter to int
	 */
	public static Integer getParameterToInt(String name, Integer defaultValue) {
		return Convert.toInt(getRequest().getParameter(name), defaultValue);
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public static HttpServletRequest getRequest() {
		return getRequestAttributes().getRequest();
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public static HttpServletResponse getResponse() {
		return getRequestAttributes().getResponse();
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * Gets the request attributes.
	 *
	 * @return the request attributes
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		final RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		return (ServletRequestAttributes) attributes;
	}

	/**
	 * Render string.
	 *
	 * @param response the response
	 * @param string   the string
	 * @return the string
	 */
	public static String renderString(HttpServletResponse response, String string) {
		try {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks if is ajax request.
	 *
	 * @param request the request
	 * @return true, if is ajax request
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		final String accept = request.getHeader("accept");
		if (accept != null && accept.indexOf("application/json") != -1) {
			return true;
		}

		final String xRequestedWith = request.getHeader("X-Requested-With");
		if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
			return true;
		}

		final String uri = request.getRequestURI();
		if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")) {
			return true;
		}

		final String ajax = request.getParameter("__ajax");
		if (StringUtils.inStringIgnoreCase(ajax, "json", "xml")) {
			return true;
		}
		return false;
	}

	/**
	 * Check agent is mobile.
	 *
	 * @param ua the ua
	 * @return true, if successful
	 */
	public static boolean checkAgentIsMobile(String ua) {
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (final String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
}
