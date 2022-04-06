package com.github.ecsoya.sword.common.xss;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.github.ecsoya.sword.common.utils.html.EscapeUtil;

/**
 * The Class XssHttpServletRequestWrapper.
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * Instantiates a new xss http servlet request wrapper.
	 *
	 * @param request the request
	 */
	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * Gets the parameter values.
	 *
	 * @param name the name
	 * @return the parameter values
	 */
	@Override
	public String[] getParameterValues(String name) {
		final String[] values = super.getParameterValues(name);
		if (values != null) {
			final int length = values.length;
			final String[] escapseValues = new String[length];
			for (int i = 0; i < length; i++) {
				// 防xss攻击和过滤前后空格
				escapseValues[i] = EscapeUtil.clean(values[i]).trim();
			}
			return escapseValues;
		}
		return super.getParameterValues(name);
	}
}