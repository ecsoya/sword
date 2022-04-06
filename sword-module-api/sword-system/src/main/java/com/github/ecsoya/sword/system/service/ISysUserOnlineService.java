package com.github.ecsoya.sword.system.service;

import java.util.Date;
import java.util.List;

import com.github.ecsoya.sword.system.domain.SysUserOnline;

/**
 * The Interface ISysUserOnlineService.
 */
public interface ISysUserOnlineService {

	/**
	 * Select online by id.
	 *
	 * @param sessionId the session id
	 * @return the sys user online
	 */
	public SysUserOnline selectOnlineById(String sessionId);

	/**
	 * Delete online by id.
	 *
	 * @param sessionId the session id
	 */
	public void deleteOnlineById(String sessionId);

	/**
	 * Batch delete online.
	 *
	 * @param sessions the sessions
	 */
	public void batchDeleteOnline(List<String> sessions);

	/**
	 * Save online.
	 *
	 * @param online the online
	 */
	public void saveOnline(SysUserOnline online);

	/**
	 * Select user online list.
	 *
	 * @param userOnline the user online
	 * @return the list
	 */
	public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

	/**
	 * Force logout.
	 *
	 * @param sessionId the session id
	 */
	public void forceLogout(String sessionId);

	/**
	 * Removes the user cache.
	 *
	 * @param loginName the login name
	 * @param sessionId the session id
	 */
	public void removeUserCache(String loginName, String sessionId);

	/**
	 * Select online by expired.
	 *
	 * @param expiredDate the expired date
	 * @return the list
	 */
	public List<SysUserOnline> selectOnlineByExpired(Date expiredDate);
}
