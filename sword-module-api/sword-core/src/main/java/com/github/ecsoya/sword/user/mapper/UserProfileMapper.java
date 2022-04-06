package com.github.ecsoya.sword.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.user.domain.UserProfile;

/**
 * The Interface UserProfileMapper.
 */
public interface UserProfileMapper {

	/**
	 * Select user profile by id.
	 *
	 * @param userId the user id
	 * @return the user profile
	 */
	UserProfile selectUserProfileById(Long userId);

	/**
	 * Select user email by id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	String selectUserEmailById(Long userId);

	/**
	 * Delete user by ids.
	 *
	 * @param userIds the user ids
	 * @return the int
	 */
	int deleteUserByIds(Long[] userIds);

	/**
	 * Select user id by username.
	 *
	 * @param username the username
	 * @return the long
	 */
	Long selectUserIdByUsername(String username);

	/**
	 * Select user email by username.
	 *
	 * @param username the username
	 * @return the string
	 */
	String selectUserEmailByUsername(String username);

	/**
	 * Select user login name by user id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	String selectUserLoginNameByUserId(Long userId);

	/**
	 * Select user accounts from email.
	 *
	 * @param userId   the user id
	 * @param userType the user type
	 * @return the list
	 */
	List<String> selectUserAccountsFromEmail(@Param("userId") Long userId, @Param("userType") String userType);

	/**
	 * Select user mobile by id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	String selectUserMobileById(Long userId);

	/**
	 * Select user mobile by login name.
	 *
	 * @param username the username
	 * @return the string
	 */
	String selectUserMobileByLoginName(String username);

	/**
	 * Select user ids by type.
	 *
	 * @param userType the user type
	 * @return the list
	 */
	List<Long> selectUserIdsByType(String userType);

	/**
	 * Fuzzy search user list.
	 *
	 * @param loginName the login name
	 * @return the list
	 */
	List<UserProfile> fuzzySearchUserList(String loginName);

	/**
	 * Select user profile list.
	 *
	 * @param userIds the user ids
	 * @return the list
	 */
	List<UserProfile> selectUserProfileList(@Param("userIds") List<Long> userIds);

	/**
	 * Fuzzy search user list by mobile.
	 *
	 * @param mobile the mobile
	 * @return the list
	 */
	List<UserProfile> fuzzySearchUserListByMobile(String mobile);

}
