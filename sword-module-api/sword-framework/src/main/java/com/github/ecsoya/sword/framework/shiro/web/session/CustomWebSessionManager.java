package com.github.ecsoya.sword.framework.shiro.web.session;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
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
 * 主要是在此如果会话的属性修改了 就标识下其修改了 然后方便 OnlineSessionDao同步
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class CustomWebSessionManager extends DefaultWebSessionManager {
	private static final Logger log = LoggerFactory.getLogger(CustomWebSessionManager.class);
	private static final String AUTHORIZATION = "Authorization";

	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		final String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
		// 如果请求头中有 Authorization 则其值为sessionId
		if (!StringUtils.isEmpty(id)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return id;
		} else {
			// 否则按默认规则从cookie取sessionId
			return super.getSessionId(request, response);
		}
	}

	@Override
	public void setAttribute(SessionKey sessionKey, Object attributeKey, Object value) throws InvalidSessionException {
		super.setAttribute(sessionKey, attributeKey, value);
		if (value != null && needMarkAttributeChanged(attributeKey)) {
			final OnlineSession session = getOnlineSession(sessionKey);
			session.markAttributeChanged();
		}
	}

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

	@Override
	public Object removeAttribute(SessionKey sessionKey, Object attributeKey) throws InvalidSessionException {
		final Object removed = super.removeAttribute(sessionKey, attributeKey);
		if (removed != null) {
			final OnlineSession s = getOnlineSession(sessionKey);
			s.markAttributeChanged();
		}

		return removed;
	}

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
	 * 验证session是否有效 用于删除过期session
	 */
	@Override
	public void validateSessions() {
		if (log.isInfoEnabled()) {
			log.info("invalidation sessions...");
		}
		super.validateSessions();

		int invalidCount = 0;

		final int timeout = (int) this.getGlobalSessionTimeout();
		if (timeout < 0) {
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

	@Override
	protected Collection<Session> getActiveSessions() {
		return super.getActiveSessions();
//		throw new UnsupportedOperationException("getActiveSessions method not supported");
	}
}