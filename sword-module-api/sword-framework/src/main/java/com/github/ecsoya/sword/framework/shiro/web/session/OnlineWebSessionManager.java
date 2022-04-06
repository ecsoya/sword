package com.github.ecsoya.sword.framework.shiro.web.session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.framework.shiro.session.OnlineSession;
import com.github.ecsoya.sword.system.domain.SysUserOnline;
import com.github.ecsoya.sword.system.service.ISysUserOnlineService;

/**
 * The Class OnlineWebSessionManager.
 */
public class OnlineWebSessionManager extends DefaultWebSessionManager {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(OnlineWebSessionManager.class);

	/**
	 * Sets the attribute.
	 *
	 * @param sessionKey   the session key
	 * @param attributeKey the attribute key
	 * @param value        the value
	 * @throws InvalidSessionException the invalid session exception
	 */
	@Override
	public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
		super.setAttribute(sessionKey, attributeKey, value);
		if (value != null && needMarkAttributeChanged(attributeKey)) {
			final OnlineSession session = getOnlineSession(sessionKey);
			session.markAttributeChanged();
		}
	}

	/**
	 * Need mark attribute changed.
	 *
	 * @param attributeKey the attribute key
	 * @return true, if successful
	 */
	private boolean needMarkAttributeChanged(Object attributeKey) {
		if (attributeKey == null) {
			return false;
		}
		final String attributeKeyStr = attributeKey.toString();
		// 优化 flash属性没必要持久化
		if (attributeKeyStr.startsWith("org.springframework")) {
			return false;
		}
		if (attributeKeyStr.startsWith("javax.servlet")) {
			return false;
		}
		if (attributeKeyStr.equals(ShiroConstants.CURRENT_USERNAME)) {
			return false;
		}
		return true;
	}

	/**
	 * Removes the attribute.
	 *
	 * @param sessionKey   the session key
	 * @param attributeKey the attribute key
	 * @return the object
	 * @throws InvalidSessionException the invalid session exception
	 */
	@Override
	public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
		final Object removed = super.removeAttribute(sessionKey, attributeKey);
		if (removed != null) {
			final OnlineSession s = getOnlineSession(sessionKey);
			s.markAttributeChanged();
		}

		return removed;
	}

	/**
	 * Gets the online session.
	 *
	 * @param sessionKey the session key
	 * @return the online session
	 */
	public OnlineSession getOnlineSession(SessionKey sessionKey) {
		OnlineSession session = null;
		final Object obj = doGetSession(sessionKey);
		if (StringUtils.isNotNull(obj)) {
			session = new OnlineSession();
			BeanUtils.copyBeanProp(session, obj);
		}
		return session;
	}

	/**
	 * Validate sessions.
	 */
	@Override
	public void validateSessions() {
		if (log.isInfoEnabled()) {
			log.info("invalidation sessions...");
		}

		int invalidCount = 0;

		final int timeout = (int) this.getGlobalSessionTimeout();
		if (timeout < 0) {
			// 永不过期不进行处理
			return;
		}
		final Date expiredDate = DateUtils.addMilliseconds(new Date(), 0 - timeout);
		final ISysUserOnlineService userOnlineService = SpringUtils.getBean(ISysUserOnlineService.class);
		final List<SysUserOnline> userOnlineList = userOnlineService.selectOnlineByExpired(expiredDate);
		// 批量过期删除
		final List<String> needOfflineIdList = new ArrayList<String>();
		for (final SysUserOnline userOnline : userOnlineList) {
			try {
				final SessionKey key = new DefaultSessionKey(userOnline.getSessionId());
				final Session session = retrieveSession(key);
				if (session != null) {
					throw new InvalidSessionException();
				}
			} catch (final InvalidSessionException e) {
				if (log.isDebugEnabled()) {
					final boolean expired = (e instanceof ExpiredSessionException);
					final String msg = "Invalidated session with id [" + userOnline.getSessionId() + "]"
							+ (expired ? " (expired)" : " (stopped)");
					log.debug(msg);
				}
				invalidCount++;
				needOfflineIdList.add(userOnline.getSessionId());
				userOnlineService.removeUserCache(userOnline.getLoginName(), userOnline.getSessionId());
			}

		}
		if (needOfflineIdList.size() > 0) {
			try {
				userOnlineService.batchDeleteOnline(needOfflineIdList);
			} catch (final Exception e) {
				log.error("batch delete db session error.", e);
			}
		}

		if (log.isInfoEnabled()) {
			String msg = "Finished invalidation session.";
			if (invalidCount > 0) {
				msg += " [" + invalidCount + "] sessions were stopped.";
			} else {
				msg += " No sessions were stopped.";
			}
			log.info(msg);
		}

	}

	/**
	 * Gets the active sessions.
	 *
	 * @return the active sessions
	 */
	@Override
	protected Collection<Session> getActiveSessions() {
		throw new UnsupportedOperationException("getActiveSessions method not supported");
	}
}
