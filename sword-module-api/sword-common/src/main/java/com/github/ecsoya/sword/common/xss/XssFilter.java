package com.github.ecsoya.sword.common.xss;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class XssFilter.
 */
public class XssFilter implements Filter {

	/** The excludes. */
	public List<String> excludes = new ArrayList<>();

	/** The enabled. */
	public boolean enabled = false;

	/**
	 * Inits the.
	 *
	 * @param filterConfig the filter config
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		final String tempExcludes = filterConfig.getInitParameter("excludes");
		final String tempEnabled = filterConfig.getInitParameter("enabled");
		if (StringUtils.isNotEmpty(tempExcludes)) {
			final String[] url = tempExcludes.split(",");
			for (int i = 0; url != null && i < url.length; i++) {
				excludes.add(url[i]);
			}
		}
		if (StringUtils.isNotEmpty(tempEnabled)) {
			enabled = Boolean.valueOf(tempEnabled);
		}
	}

	/**
	 * Do filter.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param chain    the chain
	 * @throws IOException      Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;
		if (handleExcludeURL(req, resp)) {
			chain.doFilter(request, response);
			return;
		}
		final XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(xssRequest, response);
	}

	/**
	 * Handle exclude URL.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 */
	private boolean handleExcludeURL(HttpServletRequest request, HttpServletResponse response) {
		if (!enabled) {
			return true;
		}
		if (excludes == null || excludes.isEmpty()) {
			return false;
		}
		final String url = request.getServletPath();
		for (final String pattern : excludes) {
			final Pattern p = Pattern.compile("^" + pattern);
			final Matcher m = p.matcher(url);
			if (m.find()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Destroy.
	 */
	@Override
	public void destroy() {

	}
}