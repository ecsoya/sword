package com.github.ecsoya.sword.framework.shiro.session;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.IpUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;

import eu.bitwalker.useragentutils.UserAgent;

/**
 * A factory for creating OnlineSession objects.
 */
@Component
public class OnlineSessionFactory implements SessionFactory {

	/**
	 * Creates a new OnlineSession object.
	 *
	 * @param initData the init data
	 * @return the session
	 */
	@Override
	public Session createSession(SessionContext initData) {
		final OnlineSession session = new OnlineSession();
		if (initData != null && initData instanceof WebSessionContext) {
			final WebSessionContext sessionContext = (WebSessionContext) initData;
			final HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
			if (request != null) {
				final UserAgent userAgent = UserAgent
						.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
				// 获取客户端操作系统
				final String os = userAgent.getOperatingSystem().getName();
				// 获取客户端浏览器
				final String browser = userAgent.getBrowser().getName();
				session.setHost(IpUtils.getIpAddr(request));
				session.setBrowser(browser);
				session.setOs(os);
			}
		}
		return session;
	}
}
