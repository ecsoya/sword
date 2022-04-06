package com.github.ecsoya.sword.framework.shiro.web.filter.sync;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;

import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSession;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSessionDAO;

/**
 * The Class SyncOnlineSessionFilter.
 */
public class SyncOnlineSessionFilter extends PathMatchingFilter {

	/** The online session DAO. */
	private OnlineSessionDAO onlineSessionDAO;

	/**
	 * On pre handle.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param mappedValue the mapped value
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		final OnlineSession session = (OnlineSession) request.getAttribute(ShiroConstants.ONLINE_SESSION);
		// 如果session stop了 也不同步
		// session停止时间，如果stopTimestamp不为null，则代表已停止
		if (session != null && session.getUserId() != null && session.getStopTimestamp() == null) {
			onlineSessionDAO.syncToDb(session);
		}
		return true;
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
