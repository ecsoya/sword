package com.soyatec.sword.user.mapper;

import java.util.List;

import com.soyatec.sword.user.domain.UserLevel;

/**
 * 用户级别Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserLevelMapper {
	/**
	 * 查询用户级别
	 *
	 * @param userId 用户级别ID
	 * @return 用户级别
	 */
	public UserLevel selectUserLevelById(Long userId);

	/**
	 * 查询用户级别列表
	 *
	 * @param userLevel 用户级别
	 * @return 用户级别集合
	 */
	public List<UserLevel> selectUserLevelList(UserLevel userLevel);

	/**
	 * 新增用户级别
	 *
	 * @param userLevel 用户级别
	 * @return 结果
	 */
	public int insertUserLevel(UserLevel userLevel);

	/**
	 * 修改用户级别
	 *
	 * @param userLevel 用户级别
	 * @return 结果
	 */
	public int updateUserLevel(UserLevel userLevel);

	/**
	 * 删除用户级别
	 *
	 * @param userId 用户级别ID
	 * @return 结果
	 */
	public int deleteUserLevelById(Long userId);

	/**
	 * 批量删除用户级别
	 *
	 * @param userIds 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserLevelByIds(String[] userIds);

	public Long selectUserLevelCount(Long levelId);
}
