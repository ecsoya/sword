package com.github.ecsoya.sword.user.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserProfile;

public interface IUserProfileService {

	/**
	 * 查询用户资料
	 */
	UserProfile selectUserProfileById(Long userId);

	/**
	 * 更新用户资料
	 */
	CommonResult<?> updateUserProfile(UserProfile profile);

	String selectUserEmailById(Long userId);

	/**
	 *
	 * 修改用户状态
	 */
	int changeStatus(Long userId, Integer status);

	Long selectUserIdByUsername(String username);

	String selectUserEmailByUsername(String username);

	/**
	 * 修改用户登录密码
	 */
	CommonResult<?> changeUserPassword(Long userId, String oldPassword, String newPassword);

	String selectUserLoginNameByUserId(Long userId);

	CommonResult<?> resetUserPassword(Long userId, String password);

	/**
	 * 查询邮箱关联账号
	 * @param userType TODO
	 */
	CommonResult<List<String>> selectUserAccountsFromEmail(Long userId, String userType);

	AjaxResult updateUserMobile(Long userId, String mobile);

	AjaxResult updateUserEmail(Long userId, String email);

	String selectUserMobileById(Long userId);

	String selectUserMobileByUsername(String username);

	List<Long> selectUserIdsByType(String userType);

	List<UserProfile> fuzzySearchUserList(String loginName);

	List<UserProfile> selectUserProfileList(List<Long> userIds);

	List<UserProfile> fuzzySearchUserListByMobile(String mobile);
}
