package com.soyatec.sword.common.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.soyatec.sword.common.utils.ServletUtils;

/**
 * 服务相关配置
 *
 * @author Jin Liu (angryred@qq.com)
 *
 */
@Component
public class ServerConfig {
	/**
	 * 获取完整的请求路径，包括：域名，端口，上下文访问路径
	 *
	 * @return 服务地址
	 */
	public String getUrl() {
		final HttpServletRequest request = ServletUtils.getRequest();
		return getDomain(request);
	}

	public String getWsUrl() {
		final HttpServletRequest request = ServletUtils.getRequest();
		return getDomain(request).replaceAll("https", "wss").replaceAll("http", "ws") + "/ws";
	}

	public static String getDomain(HttpServletRequest request) {
		final StringBuffer url = request.getRequestURL();
		final String contextPath = request.getServletContext().getContextPath();
		return url.delete(url.length() - request.getRequestURI().length(), url.length()).append(contextPath).toString();
	}
}
