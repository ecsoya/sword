package com.soyatec.sword.user.mapper;

import java.util.List;

import com.soyatec.sword.user.domain.UserProfile;

public interface UserProfileMapper {
	/**
	 * 查询用户资料
	 */
	UserProfile selectUserProfileById(Long userId);

	/**
	 * 查询用户邮箱
	 */
	String selectUserEmailById(Long userId);

	int deleteUserByIds(Long[] userIds);

	Long selectUserIdByUsername(String username);

	String selectUserEmailByUsername(String username);

	String selectUserLoginNameByUserId(Long userId);

	List<String> selectUserAccountsFromEmail(Long userId);

	String selectUserMobileById(Long userId);

	String selectUserMobileByLoginName(String username);

	List<Long> selectUserIdsByType(String userType);

}
