package com.soyatec.sword.user.service;

import java.util.List;

import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.user.domain.UserProfile;

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
	 */
	CommonResult<List<String>> selectUserAccountsFromEmail(Long userId);

	AjaxResult updateUserMobile(Long userId, String mobile);

	AjaxResult updateUserEmail(Long userId, String email);

	String selectUserMobileById(Long userId);

	String selectUserMobileByUsername(String username);

	List<Long> selectUserIdsByType(String userType);

	List<UserProfile> fuzzySearchUserList(String loginName);
}
