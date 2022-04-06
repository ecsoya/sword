package com.github.ecsoya.sword.framework.shiro.service;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSession;
import com.github.ecsoya.sword.system.domain.SysUserOnline;
import com.github.ecsoya.sword.system.service.ISysUserOnlineService;

/**
 * The Class SysShiroService.
 */
@Component
public class SysShiroService {

	/** The online service. */
	@Autowired
	private ISysUserOnlineService onlineService;

	/**
	 * Delete session.
	 *
	 * @param onlineSession the online session
	 */
	public void deleteSession(OnlineSession onlineSession) {
		onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
	}

	/**
	 * Gets the session.
	 *
	 * @param sessionId the session id
	 * @return the session
	 */
	public Session getSession(Serializable sessionId) {
		final SysUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
		return StringUtils.isNull(userOnline) ? null : createSession(userOnline);
	}

	/**
	 * Creates the session.
	 *
	 * @param userOnline the user online
	 * @return the session
	 */
	public Session createSession(SysUserOnline userOnline) {
		final OnlineSession onlineSession = new OnlineSession();
		if (StringUtils.isNotNull(userOnline)) {
			onlineSession.setId(userOnline.getSessionId());
			onlineSession.setHost(userOnline.getIpaddr());
			onlineSession.setBrowser(userOnline.getBrowser());
			onlineSession.setOs(userOnline.getOs());
			onlineSession.setDeptName(userOnline.getDeptName());
			onlineSession.setLoginName(userOnline.getLoginName());
			onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
			onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
			onlineSession.setTimeout(userOnline.getExpireTime());
		}
		return onlineSession;
	}
}
