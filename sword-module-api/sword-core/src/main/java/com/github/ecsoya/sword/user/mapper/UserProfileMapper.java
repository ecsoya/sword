package com.github.ecsoya.sword.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.user.domain.UserProfile;

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

	List<String> selectUserAccountsFromEmail(@Param("userId") Long userId, @Param("userType") String userType);

	String selectUserMobileById(Long userId);

	String selectUserMobileByLoginName(String username);

	List<Long> selectUserIdsByType(String userType);

	List<UserProfile> fuzzySearchUserList(String loginName);

	List<UserProfile> selectUserProfileList(@Param("userIds") List<Long> userIds);

	List<UserProfile> fuzzySearchUserListByMobile(String mobile);

}
