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
 * 用户级别Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserLevelServiceImpl implements IUserLevelService {
	@Autowired
	private UserLevelMapper userLevelMapper;

	/**
	 * 查询用户级别
	 *
	 * @param userId 用户级别ID
	 * @return 用户级别
	 */
	@Override
	public UserLevel selectUserLevelById(Long userId) {
		return userLevelMapper.selectUserLevelById(userId);
	}

	/**
	 * 查询用户级别列表
	 *
	 * @param userLevel 用户级别
	 * @return 用户级别
	 */
	@Override
	public List<UserLevel> selectUserLevelList(UserLevel userLevel) {
		return userLevelMapper.selectUserLevelList(userLevel);
	}

	/**
	 * 新增用户级别
	 *
	 * @param userLevel 用户级别
	 * @return 结果
	 */
	@Override
	public int insertUserLevel(UserLevel userLevel) {
		if (userLevel.getCreateTime() == null) {
			userLevel.setCreateTime(DateUtils.getNowDate());
		}
		return userLevelMapper.insertUserLevel(userLevel);
	}

	/**
	 * 修改用户级别
	 *
	 * @param userLevel 用户级别
	 * @return 结果
	 */
	@Override
	public int updateUserLevel(UserLevel userLevel) {
		if (userLevel.getUpdateTime() == null) {
			userLevel.setUpdateTime(DateUtils.getNowDate());
		}
		return userLevelMapper.updateUserLevel(userLevel);
	}

	/**
	 * 删除用户级别对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserLevelByIds(String ids) {
		return userLevelMapper.deleteUserLevelByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户级别信息
	 *
	 * @param userId 用户级别ID
	 * @return 结果
	 */
	@Override
	public int deleteUserLevelById(Long userId) {
		return userLevelMapper.deleteUserLevelById(userId);
	}

	@Override
	public Long selectUserLevelCount(Long levelId) {
		if (levelId == null) {
			return null;
		}
		return userLevelMapper.selectUserLevelCount(levelId);
	}
}
