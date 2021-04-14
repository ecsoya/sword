package com.soyatec.sword.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * 错误信息处理类。
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class ExceptionUtil {
	/**
	 * 获取exception的详细错误信息。
	 */
	public static String getExceptionMessage(Throwable e) {
		final StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		final String str = sw.toString();
		return str;
	}

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
