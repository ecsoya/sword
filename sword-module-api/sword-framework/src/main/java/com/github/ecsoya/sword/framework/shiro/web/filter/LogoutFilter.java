package com.github.ecsoya.sword.framework.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.framework.manager.factory.AsyncFactory;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysUserOnlineService;

/**
 * The Class LogoutFilter.
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

	/** The login url. */
	private String loginUrl;

	/**
	 * Gets the login url.
	 *
	 * @return the login url
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * Sets the login url.
	 *
	 * @param loginUrl the new login url
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	/**
	 * Pre handle.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		try {
			final Subject subject = getSubject(request, response);
			final String redirectUrl = getRedirectUrl(request, response, subject);
			try {
				final SysUser user = ShiroUtils.getSysUser();
				if (StringUtils.isNotNull(user)) {
					final String loginName = user.getLoginName();
					// 记录用户退出日志
					AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.LOGOUT,
							MessageUtils.message("user.logout.success")));
					// 清理缓存
					SpringUtils.getBean(ISysUserOnlineService.class).removeUserCache(loginName,
							ShiroUtils.getSessionId());
				}
				// 退出登录
				subject.logout();
			} catch (final SessionException ise) {
				log.error("logout fail.", ise);
			}
			WebUtils.issueRedirect(request, response, redirectUrl, null, true, false);
		} catch (final Exception e) {
			log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
		}
		return false;
	}

	/**
	 * Gets the redirect url.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param subject  the subject
	 * @return the redirect url
	 */
	@Override
	protected String getRedirectUrl(ServletRequest request, ServletResponse response, Subject subject) {
		final String url = getLoginUrl();
		if (StringUtils.isNotEmpty(url)) {
			return url;
		}
		return super.getRedirectUrl(request, response, subject);
	}
}
