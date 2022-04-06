package com.github.ecsoya.sword.user.mapper;

import java.util.List;

import com.github.ecsoya.sword.user.domain.UserLevel;

/**
 * The Interface UserLevelMapper.
 */
public interface UserLevelMapper {

	/**
	 * Select user level by id.
	 *
	 * @param userId the user id
	 * @return the user level
	 */
	public UserLevel selectUserLevelById(Long userId);

	/**
	 * Select user level list.
	 *
	 * @param userLevel the user level
	 * @return the list
	 */
	public List<UserLevel> selectUserLevelList(UserLevel userLevel);

	/**
	 * Insert user level.
	 *
	 * @param userLevel the user level
	 * @return the int
	 */
	public int insertUserLevel(UserLevel userLevel);

	/**
	 * Update user level.
	 *
	 * @param userLevel the user level
	 * @return the int
	 */
	public int updateUserLevel(UserLevel userLevel);

	/**
	 * Delete user level by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserLevelById(Long userId);

	/**
	 * Delete user level by ids.
	 *
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteUserLevelByIds(String[] userIds);

	/**
	 * Select user level count.
	 *
	 * @param levelId the level id
	 * @return the long
	 */
	public Long selectUserLevelCount(Long levelId);
}
