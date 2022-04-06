package com.github.ecsoya.sword.user.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserProfile;

/**
 * The Interface IUserProfileService.
 */
public interface IUserProfileService {

	/**
	 * Select user profile by id.
	 *
	 * @param userId the user id
	 * @return the user profile
	 */
	UserProfile selectUserProfileById(Long userId);

	/**
	 * Update user profile.
	 *
	 * @param profile the profile
	 * @return the common result
	 */
	CommonResult<?> updateUserProfile(UserProfile profile);

	/**
	 * Select user email by id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	String selectUserEmailById(Long userId);

	/**
	 * Change status.
	 *
	 * @param userId the user id
	 * @param status the status
	 * @return the int
	 */
	int changeStatus(Long userId, Integer status);

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
	 * Change user password.
	 *
	 * @param userId      the user id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the common result
	 */
	CommonResult<?> changeUserPassword(Long userId, String oldPassword, String newPassword);

	/**
	 * Select user login name by user id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	String selectUserLoginNameByUserId(Long userId);

	/**
	 * Reset user password.
	 *
	 * @param userId   the user id
	 * @param password the password
	 * @return the common result
	 */
	CommonResult<?> resetUserPassword(Long userId, String password);

	/**
	 * Select user accounts from email.
	 *
	 * @param userId   the user id
	 * @param userType the user type
	 * @return the common result
	 */
	CommonResult<List<String>> selectUserAccountsFromEmail(Long userId, String userType);

	/**
	 * Update user mobile.
	 *
	 * @param userId the user id
	 * @param mobile the mobile
	 * @return the ajax result
	 */
	AjaxResult updateUserMobile(Long userId, String mobile);

	/**
	 * Update user email.
	 *
	 * @param userId the user id
	 * @param email  the email
	 * @return the ajax result
	 */
	AjaxResult updateUserEmail(Long userId, String email);

	/**
	 * Select user mobile by id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	String selectUserMobileById(Long userId);

	/**
	 * Select user mobile by username.
	 *
	 * @param username the username
	 * @return the string
	 */
	String selectUserMobileByUsername(String username);

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
	List<UserProfile> selectUserProfileList(List<Long> userIds);

	/**
	 * Fuzzy search user list by mobile.
	 *
	 * @param mobile the mobile
	 * @return the list
	 */
	List<UserProfile> fuzzySearchUserListByMobile(String mobile);
}
