package com.github.ecsoya.sword.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.user.domain.UserLevel;
import com.github.ecsoya.sword.user.mapper.UserLevelMapper;
import com.github.ecsoya.sword.user.service.IUserLevelService;

/**
 * The Class UserLevelServiceImpl.
 */
@Service
public class UserLevelServiceImpl implements IUserLevelService {

	/** The user level mapper. */
	@Autowired
	private UserLevelMapper userLevelMapper;

	/**
	 * Select user level by id.
	 *
	 * @param userId the user id
	 * @return the user level
	 */
	@Override
	public UserLevel selectUserLevelById(Long userId) {
		return userLevelMapper.selectUserLevelById(userId);
	}

	/**
	 * Select user level list.
	 *
	 * @param userLevel the user level
	 * @return the list
	 */
	@Override
	public List<UserLevel> selectUserLevelList(UserLevel userLevel) {
		return userLevelMapper.selectUserLevelList(userLevel);
	}

	/**
	 * Insert user level.
	 *
	 * @param userLevel the user level
	 * @return the int
	 */
	@Override
	public int insertUserLevel(UserLevel userLevel) {
		if (userLevel.getCreateTime() == null) {
			userLevel.setCreateTime(DateUtils.getNowDate());
		}
		return userLevelMapper.insertUserLevel(userLevel);
	}

	/**
	 * Update user level.
	 *
	 * @param userLevel the user level
	 * @return the int
	 */
	@Override
	public int updateUserLevel(UserLevel userLevel) {
		if (userLevel.getUpdateTime() == null) {
			userLevel.setUpdateTime(DateUtils.getNowDate());
		}
		return userLevelMapper.updateUserLevel(userLevel);
	}

	/**
	 * Delete user level by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserLevelByIds(String ids) {
		return userLevelMapper.deleteUserLevelByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user level by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int deleteUserLevelById(Long userId) {
		return userLevelMapper.deleteUserLevelById(userId);
	}

	/**
	 * Select user level count.
	 *
	 * @param levelId the level id
	 * @return the long
	 */
	@Override
	public Long selectUserLevelCount(Long levelId) {
		if (levelId == null) {
			return null;
		}
		return userLevelMapper.selectUserLevelCount(levelId);
	}
}
