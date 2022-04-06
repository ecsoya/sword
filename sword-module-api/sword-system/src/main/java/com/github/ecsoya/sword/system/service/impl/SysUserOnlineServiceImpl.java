package com.github.ecsoya.sword.system.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.List;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.domain.SysUserOnline;
import com.github.ecsoya.sword.system.mapper.SysUserOnlineMapper;
import com.github.ecsoya.sword.system.service.ISysUserOnlineService;

/**
 * The Class SysUserOnlineServiceImpl.
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {

	/** The user online dao. */
	@Autowired
	private SysUserOnlineMapper userOnlineDao;

	/** The cache manager. */
	@Autowired
	private CacheManager cacheManager;

	/**
	 * Select online by id.
	 *
	 * @param sessionId the session id
	 * @return the sys user online
	 */
	@Override
	public SysUserOnline selectOnlineById(String sessionId) {
		return userOnlineDao.selectOnlineById(sessionId);
	}

	/**
	 * Delete online by id.
	 *
	 * @param sessionId the session id
	 */
	@Override
	public void deleteOnlineById(String sessionId) {
		final SysUserOnline userOnline = selectOnlineById(sessionId);
		if (StringUtils.isNotNull(userOnline)) {
			userOnlineDao.deleteOnlineById(sessionId);
		}
	}

	/**
	 * Batch delete online.
	 *
	 * @param sessions the sessions
	 */
	@Override
	public void batchDeleteOnline(List<String> sessions) {
		for (final String sessionId : sessions) {
			final SysUserOnline userOnline = selectOnlineById(sessionId);
			if (StringUtils.isNotNull(userOnline)) {
				userOnlineDao.deleteOnlineById(sessionId);
			}
		}
	}

	/**
	 * Save online.
	 *
	 * @param online the online
	 */
	@Override
	public void saveOnline(SysUserOnline online) {
		userOnlineDao.saveOnline(online);
	}

	/**
	 * Select user online list.
	 *
	 * @param userOnline the user online
	 * @return the list
	 */
	@Override
	public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline) {
		return userOnlineDao.selectUserOnlineList(userOnline);
	}

	/**
	 * Force logout.
	 *
	 * @param sessionId the session id
	 */
	@Override
	public void forceLogout(String sessionId) {
		userOnlineDao.deleteOnlineById(sessionId);
	}

	/**
	 * Removes the user cache.
	 *
	 * @param loginName the login name
	 * @param sessionId the session id
	 */
	@Override
	public void removeUserCache(String loginName, String sessionId) {
		final Cache<String, Deque<Serializable>> cache = cacheManager.getCache(ShiroConstants.SYS_USERCACHE);
		final Deque<Serializable> deque = cache.get(loginName);
		if (StringUtils.isEmpty(deque) || deque.size() == 0) {
			return;
		}
		deque.remove(sessionId);
	}

	/**
	 * Select online by expired.
	 *
	 * @param expiredDate the expired date
	 * @return the list
	 */
	@Override
	public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
		final String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
		return userOnlineDao.selectOnlineByExpired(lastAccessTime);
	}
}
