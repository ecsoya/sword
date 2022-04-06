package com.github.ecsoya.sword.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.json.JSON;

/**
 * The Class LogUtils.
 */
public class LogUtils {

	/** The Constant ERROR_LOG. */
	public static final Logger ERROR_LOG = LoggerFactory.getLogger("sys-error");

	/** The Constant ACCESS_LOG. */
	public static final Logger ACCESS_LOG = LoggerFactory.getLogger("sys-access");

	/**
	 * Log access.
	 *
	 * @param request the request
	 * @throws Exception the exception
	 */
	public static void logAccess(HttpServletRequest request) throws Exception {
		final String username = getUsername();
		final String jsessionId = request.getRequestedSessionId();
		final String ip = IpUtils.getIpAddr(request);
		final String accept = request.getHeader("accept");
		final String userAgent = request.getHeader("User-Agent");
		final String url = request.getRequestURI();
		final String params = getParams(request);

		final StringBuilder s = new StringBuilder();
		s.append(getBlock(username));
		s.append(getBlock(jsessionId));
		s.append(getBlock(ip));
		s.append(getBlock(accept));
		s.append(getBlock(userAgent));
		s.append(getBlock(url));
		s.append(getBlock(params));
		s.append(getBlock(request.getHeader("Referer")));
		getAccessLog().info(s.toString());
	}

	/**
	 * Log error.
	 *
	 * @param message the message
	 * @param e       the e
	 */
	public static void logError(String message, Throwable e) {
		final String username = getUsername();
		final StringBuilder s = new StringBuilder();
		s.append(getBlock("exception"));
		s.append(getBlock(username));
		s.append(getBlock(message));
		ERROR_LOG.error(s.toString(), e);
	}

	/**
	 * Log page error.
	 *
	 * @param request the request
	 */
	public static void logPageError(HttpServletRequest request) {
		final String username = getUsername();

		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		final String message = (String) request.getAttribute("javax.servlet.error.message");
		final String uri = (String) request.getAttribute("javax.servlet.error.request_uri");
		Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");

		if (statusCode == null) {
			statusCode = 0;
		}

		final StringBuilder s = new StringBuilder();
		s.append(getBlock(t == null ? "page" : "exception"));
		s.append(getBlock(username));
		s.append(getBlock(statusCode));
		s.append(getBlock(message));
		s.append(getBlock(IpUtils.getIpAddr(request)));

		s.append(getBlock(uri));
		s.append(getBlock(request.getHeader("Referer")));
		final StringWriter sw = new StringWriter();

		while (t != null) {
			t.printStackTrace(new PrintWriter(sw));
			t = t.getCause();
		}
		s.append(getBlock(sw.toString()));
		getErrorLog().error(s.toString());

	}

	/**
	 * Gets the block.
	 *
	 * @param msg the msg
	 * @return the block
	 */
	public static String getBlock(Object msg) {
		if (msg == null) {
			msg = "";
		}
		return "[" + msg.toString() + "]";
	}

	/**
	 * Gets the params.
	 *
	 * @param request the request
	 * @return the params
	 * @throws Exception the exception
	 */
	protected static String getParams(HttpServletRequest request) throws Exception {
		final Map<String, String[]> params = request.getParameterMap();
		return JSON.marshal(params);
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	protected static String getUsername() {
		return (String) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * Gets the access log.
	 *
	 * @return the access log
	 */
	public static Logger getAccessLog() {
		return ACCESS_LOG;
	}

	/**
	 * Gets the error log.
	 *
	 * @return the error log
	 */
	public static Logger getErrorLog() {
		return ERROR_LOG;
	}
}
