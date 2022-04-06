package com.github.ecsoya.sword.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Class CookieUtils.
 */
public class CookieUtils {

	/**
	 * Sets the cookie.
	 *
	 * @param response the response
	 * @param name     the name
	 * @param value    the value
	 */
	public static void setCookie(HttpServletResponse response, String name, String value) {
		setCookie(response, name, value, 60 * 60 * 24);
	}

	/**
	 * Sets the cookie.
	 *
	 * @param response the response
	 * @param name     the name
	 * @param value    the value
	 * @param path     the path
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		setCookie(response, name, value, path, 60 * 60 * 24);
	}

	/**
	 * Sets the cookie.
	 *
	 * @param response the response
	 * @param name     the name
	 * @param value    the value
	 * @param maxAge   the max age
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
		setCookie(response, name, value, "/", maxAge);
	}

	/**
	 * Sets the cookie.
	 *
	 * @param response the response
	 * @param name     the name
	 * @param value    the value
	 * @param path     the path
	 * @param maxAge   the max age
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
		final Cookie cookie = new Cookie(name, null);
		cookie.setPath(path);
		cookie.setMaxAge(maxAge);
		try {
			cookie.setValue(URLEncoder.encode(value, "utf-8"));
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.addCookie(cookie);
	}

	/**
	 * Gets the cookie.
	 *
	 * @param request the request
	 * @param name    the name
	 * @return the cookie
	 */
	public static String getCookie(HttpServletRequest request, String name) {
		return getCookie(request, null, name, false);
	}

	/**
	 * Gets the cookie.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param name     the name
	 * @return the cookie
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		return getCookie(request, response, name, true);
	}

	/**
	 * Gets the cookie.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param name     the name
	 * @param isRemove the is remove
	 * @return the cookie
	 */
	public static String getCookie(HttpServletRequest request, HttpServletResponse response, String name,
			boolean isRemove) {
		String value = null;
		final Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (final Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					try {
						value = URLDecoder.decode(cookie.getValue(), "utf-8");
					} catch (final UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (isRemove) {
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		return value;
	}
}
