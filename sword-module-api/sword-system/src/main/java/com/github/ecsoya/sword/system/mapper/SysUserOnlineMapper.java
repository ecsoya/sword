package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysUserOnline;

/**
 * The Interface SysUserOnlineMapper.
 */
public interface SysUserOnlineMapper {

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
	 * @return the int
	 */
	public int deleteOnlineById(String sessionId);

	/**
	 * Save online.
	 *
	 * @param online the online
	 * @return the int
	 */
	public int saveOnline(SysUserOnline online);

	/**
	 * Select user online list.
	 *
	 * @param userOnline the user online
	 * @return the list
	 */
	public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline);

	/**
	 * Select online by expired.
	 *
	 * @param lastAccessTime the last access time
	 * @return the list
	 */
	public List<SysUserOnline> selectOnlineByExpired(String lastAccessTime);
}
