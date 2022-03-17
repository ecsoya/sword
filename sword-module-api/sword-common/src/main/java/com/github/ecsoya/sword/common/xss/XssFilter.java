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
 * 防止XSS攻击的过滤器
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class XssFilter implements Filter {
	/**
	 * 排除链接
	 */
	public List<String> excludes = new ArrayList<>();

	/**
	 * xss过滤开关
	 */
	public boolean enabled = false;

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

	@Override
	public void destroy() {

	}
}