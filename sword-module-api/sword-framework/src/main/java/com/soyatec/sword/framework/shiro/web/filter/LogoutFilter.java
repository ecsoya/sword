package com.soyatec.sword.framework.shiro.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soyatec.sword.common.constant.Constants;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.framework.manager.factory.AsyncFactory;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.system.service.ISysUserOnlineService;

/**
 * 退出过滤器
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
	private static final Logger log = LoggerFactory.getLogger(LogoutFilter.class);

	/**
	 * 退出后重定向的地址
	 */
	private String loginUrl;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

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
			issueRedirect(request, response, redirectUrl);
		} catch (final Exception e) {
			log.error("Encountered session exception during logout.  This can generally safely be ignored.", e);
		}
		return false;
	}

	/**
	 * 退出跳转URL
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
