package com.github.ecsoya.sword.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * The Class ExceptionUtil.
 */
public class ExceptionUtil {

	/**
	 * Gets the exception message.
	 *
	 * @param e the e
	 * @return the exception message
	 */
	public static String getExceptionMessage(Throwable e) {
		final StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		final String str = sw.toString();
		return str;
	}

	/**
	 * Gets the root error mseeage.
	 *
	 * @param e the e
	 * @return the root error mseeage
	 */
	public static String getRootErrorMseeage(Exception e) {
		Throwable root = ExceptionUtils.getRootCause(e);
		root = (root == null ? e : root);
		if (root == null) {
			return "";
		}
		final String msg = root.getMessage();
		if (msg == null) {
			return "null";
		}
		return org.apache.commons.lang3.StringUtils.defaultString(msg);
	}
}
