package com.github.ecsoya.sword.framework.shiro.web.filter.online;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.alibaba.fastjson.JSON;
import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.enums.OnlineStatus;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSession;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSessionDAO;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;

/**
 * The Class OnlineSessionFilter.
 */
public class OnlineSessionFilter extends AccessControlFilter {

	/** The online session DAO. */
	private OnlineSessionDAO onlineSessionDAO;

	/**
	 * Checks if is access allowed.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param mappedValue the mapped value
	 * @return true, if is access allowed
	 * @throws Exception the exception
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		final Subject subject = getSubject(request, response);
		if (subject == null || subject.getSession() == null) {
			return true;
		}
		final Session session = onlineSessionDAO.readSession(subject.getSession().getId());
		if (session != null && session instanceof OnlineSession) {
			final OnlineSession onlineSession = (OnlineSession) session;
			request.setAttribute(ShiroConstants.ONLINE_SESSION, onlineSession);
			// 把user对象设置进去
			final boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;
			if (isGuest == true) {
				final SysUser user = ShiroUtils.getSysUser();
				if (user != null) {
					onlineSession.setUserId(user.getUserId());
					onlineSession.setLoginName(user.getLoginName());
					onlineSession.setAvatar(user.getAvatar());
					onlineSession.setDeptName(user.getDept().getDeptName());
					onlineSession.markAttributeChanged();
				}
			}

			if (onlineSession.getStatus() == OnlineStatus.off_line) {
				return false;
			}
		}
		return true;
	}

	/**
	 * On access denied.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		final Subject subject = getSubject(request, response);
		if (subject != null) {
			subject.logout();
		}
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

	/**
	 * Redirect to login.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// 跳转到登录页
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		String loginUrl = getLoginUrl();
		if (StringUtils.isEmpty(getLoginUrl())) {
			// 请求被拦截后直接返回json格式的响应数据
			response.getWriter()
					.write(JSON.toJSONString(CommonResult.fail(HttpStatus.UNAUTHORIZED.value(), "Not Login")));
			response.getWriter().flush();
			response.getWriter().close();
		} else {
			WebUtils.issueRedirect(request, response, loginUrl, null, true, false);
		}
	}

	/**
	 * Sets the online session DAO.
	 *
	 * @param onlineSessionDAO the new online session DAO
	 */
	public void setOnlineSessionDAO(OnlineSessionDAO onlineSessionDAO) {
		this.onlineSessionDAO = onlineSessionDAO;
	}
}
