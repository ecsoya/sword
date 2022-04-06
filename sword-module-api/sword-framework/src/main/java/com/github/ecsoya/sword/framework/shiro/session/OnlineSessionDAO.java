package com.github.ecsoya.sword.framework.shiro.session;

import java.io.Serializable;
import java.util.Date;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.ecsoya.sword.common.enums.OnlineStatus;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.manager.factory.AsyncFactory;
import com.github.ecsoya.sword.framework.shiro.service.SysShiroService;

/**
 * The Class OnlineSessionDAO.
 */
public class OnlineSessionDAO extends EnterpriseCacheSessionDAO {

	/** The db sync period. */
	@Value("${shiro.session.dbSyncPeriod}")
	private int dbSyncPeriod;

	/** The Constant LAST_SYNC_DB_TIMESTAMP. */
	private static final String LAST_SYNC_DB_TIMESTAMP = OnlineSessionDAO.class.getName() + "LAST_SYNC_DB_TIMESTAMP";

	/** The sys shiro service. */
	@Autowired
	private SysShiroService sysShiroService;

	/**
	 * Instantiates a new online session DAO.
	 */
	public OnlineSessionDAO() {
		super();
	}

	/**
	 * Instantiates a new online session DAO.
	 *
	 * @param expireTime the expire time
	 */
	public OnlineSessionDAO(long expireTime) {
		super();
	}

	/**
	 * Do read session.
	 *
	 * @param sessionId the session id
	 * @return the session
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		return sysShiroService.getSession(sessionId);
	}

	/**
	 * Update.
	 *
	 * @param session the session
	 * @throws UnknownSessionException the unknown session exception
	 */
	@Override
	public void update(Session session) throws UnknownSessionException {
		super.update(session);
	}

	/**
	 * Sync to db.
	 *
	 * @param onlineSession the online session
	 */
	public void syncToDb(OnlineSession onlineSession) {
		final Date lastSyncTimestamp = (Date) onlineSession.getAttribute(LAST_SYNC_DB_TIMESTAMP);
		if (lastSyncTimestamp != null) {
			boolean needSync = true;
			final long deltaTime = onlineSession.getLastAccessTime().getTime() - lastSyncTimestamp.getTime();
			if (deltaTime < dbSyncPeriod * 60 * 1000) {
				// 时间差不足 无需同步
				needSync = false;
			}
			// isGuest = true 访客
			final boolean isGuest = onlineSession.getUserId() == null || onlineSession.getUserId() == 0L;

			// session 数据变更了 同步
			if (!isGuest && onlineSession.isAttributeChanged()) {
				needSync = true;
			}

			if (!needSync) {
				return;
			}
		}
		// 更新上次同步数据库时间
		onlineSession.setAttribute(LAST_SYNC_DB_TIMESTAMP, onlineSession.getLastAccessTime());
		// 更新完后 重置标识
		if (onlineSession.isAttributeChanged()) {
			onlineSession.resetAttributeChanged();
		}
		AsyncManager.me().execute(AsyncFactory.syncSessionToDb(onlineSession));
	}

	/**
	 * Do delete.
	 *
	 * @param session the session
	 */
	@Override
	protected void doDelete(Session session) {
		final OnlineSession onlineSession = (OnlineSession) session;
		if (null == onlineSession) {
			return;
		}
		onlineSession.setStatus(OnlineStatus.off_line);
		sysShiroService.deleteSession(onlineSession);
	}
}
