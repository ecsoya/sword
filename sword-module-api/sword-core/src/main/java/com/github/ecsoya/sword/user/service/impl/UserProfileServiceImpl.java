package com.github.ecsoya.sword.user.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.CacheUtils;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.service.ISysUserService;
import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.mapper.UserProfileMapper;
import com.github.ecsoya.sword.user.service.IUserProfileService;

/**
 * The Class UserProfileServiceImpl.
 */
@Service
public class UserProfileServiceImpl implements IUserProfileService {

	/** The Constant USER_ID_2_EMAIL_CACHE. */
	private static final String USER_ID_2_EMAIL_CACHE = "user-id-email:"; //$NON-NLS-1$

	/** The Constant USER_LOGIN_NAME_2_EMAIL_CACHE. */
	private static final String USER_LOGIN_NAME_2_EMAIL_CACHE = "user-login-name-email:"; //$NON-NLS-1$

	/** The Constant USER_LOGIN_NAME_2_ID_CACHE. */
	private static final String USER_LOGIN_NAME_2_ID_CACHE = "user-login-name-id:"; //$NON-NLS-1$

	/** The Constant USER_ID_LOGIN_NAME_CACHE. */
	private static final String USER_ID_LOGIN_NAME_CACHE = "user-id-login-name:"; //$NON-NLS-1$

	/** The Constant USER_ID_MOBILE_CACHE. */
	private static final String USER_ID_MOBILE_CACHE = "user-id-mobile:"; //$NON-NLS-1$

	/** The Constant USER_LOGIN_NAME_2_MOBILE_CACHE. */
	private static final String USER_LOGIN_NAME_2_MOBILE_CACHE = "user-login-name-mobile:"; //$NON-NLS-1$

	/** The user profile mapper. */
	@Autowired
	private UserProfileMapper userProfileMapper;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/**
	 * Select user profile by id.
	 *
	 * @param userId the user id
	 * @return the user profile
	 */
	@Override
	public UserProfile selectUserProfileById(Long userId) {
		if (userId == null) {
			return null;
		}
		return userProfileMapper.selectUserProfileById(userId);
	}

	/**
	 * Update user profile.
	 *
	 * @param profile the profile
	 * @return the common result
	 */
	@Override
	public CommonResult<?> updateUserProfile(UserProfile profile) {
		if (profile == null || profile.getUserId() == null) {
			return CommonResult.fail();
		}
		final SysUser user = new SysUser();
		user.setUserId(profile.getUserId());
		user.setAvatar(profile.getAvatar());
		user.setUserName(profile.getUserName());
		final int rows = userService.updateUserInfo(user);
		return rows > 0 ? CommonResult.success(rows) : CommonResult.fail(MessageUtils.message("UserServiceImpl.16")); //$NON-NLS-1$
	}

	/**
	 * Select user email by id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	@Override
	public String selectUserEmailById(Long userId) {
		if (userId == null) {
			return null;
		}
		String email = (String) CacheUtils.get(USER_ID_2_EMAIL_CACHE, userId.toString());
		if (StringUtils.isEmpty(email)) {
			email = userProfileMapper.selectUserEmailById(userId);
			if (email != null) {
				CacheUtils.put(USER_ID_2_EMAIL_CACHE, userId.toString(), email);
			}
		}
		return email;
	}

	/**
	 * Change status.
	 *
	 * @param userId the user id
	 * @param status the status
	 * @return the int
	 */
	@Override
	public int changeStatus(Long userId, Integer status) {
		if (userId == null || status == null) {
			return 0;
		}
		final SysUser user = new SysUser();
		user.setUserId(userId);
		user.setStatus(status.toString());
		return userService.changeStatus(user);
	}

	/**
	 * Select user id by username.
	 *
	 * @param username the username
	 * @return the long
	 */
	@Override
	public Long selectUserIdByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		Long userId = (Long) CacheUtils.get(USER_LOGIN_NAME_2_ID_CACHE, username);
		if (userId == null) {
			userId = userProfileMapper.selectUserIdByUsername(username);
			if (userId != null) {
				CacheUtils.put(USER_LOGIN_NAME_2_ID_CACHE, username, userId);
			}
		}
		return userId;
	}

	/**
	 * Select user email by username.
	 *
	 * @param username the username
	 * @return the string
	 */
	@Override
	public String selectUserEmailByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return null;
		}
		String email = (String) CacheUtils.get(USER_LOGIN_NAME_2_EMAIL_CACHE, username);
		if (email == null) {
			email = userProfileMapper.selectUserEmailByUsername(username);
			if (email != null) {
				CacheUtils.put(USER_LOGIN_NAME_2_EMAIL_CACHE, username, email);
			}
		}
		return email;
	}

	/**
	 * Change user password.
	 *
	 * @param userId      the user id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the common result
	 */
	@Override
	public CommonResult<?> changeUserPassword(Long userId, String oldPassword, String newPassword) {
		if (userId == null || StringUtils.isEmpty(oldPassword) || StringUtils.isEmpty(newPassword)) {
			return CommonResult.fail(MessageUtils.message("UserServiceImpl.17")); //$NON-NLS-1$
		}
		if (newPassword.length() < 6) {
			return CommonResult.fail(MessageUtils.message("UserServiceImpl.18")); //$NON-NLS-1$
		}
		final SysUser user = userService.selectUserById(userId);
		if (!org.apache.commons.lang3.StringUtils.equals(user.getPassword(),
				StringUtils.encryptPassword(user.getLoginName(), oldPassword, user.getSalt()))) {
			return CommonResult.fail(MessageUtils.message("UserServiceImpl.19")); //$NON-NLS-1$
		}
		final SysUser resetPwd = new SysUser();
		resetPwd.setUserId(user.getUserId());
		resetPwd.setSalt(StringUtils.randomNum(6));
		resetPwd.setPassword(StringUtils.encryptPassword(user.getLoginName(), newPassword, resetPwd.getSalt()));
		return CommonResult.ajax(userService.resetUserPwd(resetPwd));
	}

	/**
	 * Select user login name by user id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	@Override
	public String selectUserLoginNameByUserId(Long userId) {
		if (userId == null) {
			return null;
		}
		String loginName = (String) CacheUtils.get(USER_ID_LOGIN_NAME_CACHE, userId.toString());
		if (loginName == null) {
			loginName = userProfileMapper.selectUserLoginNameByUserId(userId);
			if (loginName != null) {
				CacheUtils.put(USER_ID_LOGIN_NAME_CACHE, userId.toString(), loginName);
			}
		}
		return loginName;
	}

	/**
	 * Select user mobile by id.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	@Override
	public String selectUserMobileById(Long userId) {
		if (userId == null) {
			return null;
		}
		String mobile = (String) CacheUtils.get(USER_ID_MOBILE_CACHE, userId.toString());
		if (StringUtils.isEmpty(mobile)) {
			mobile = userProfileMapper.selectUserMobileById(userId);
			if (mobile != null) {
				CacheUtils.put(USER_ID_MOBILE_CACHE, userId.toString(), mobile);
			}
		}
		return mobile;
	}

	/**
	 * Select user mobile by username.
	 *
	 * @param username the username
	 * @return the string
	 */
	@Override
	public String selectUserMobileByUsername(String username) {
		if (username == null) {
			return null;
		}
		String mobile = (String) CacheUtils.get(USER_LOGIN_NAME_2_MOBILE_CACHE, username);
		if (StringUtils.isEmpty(mobile)) {
			mobile = userProfileMapper.selectUserMobileByLoginName(username);
			if (mobile != null) {
				CacheUtils.put(USER_LOGIN_NAME_2_MOBILE_CACHE, username, mobile);
			}
		}
		return mobile;
	}

	/**
	 * Reset user password.
	 *
	 * @param userId   the user id
	 * @param password the password
	 * @return the common result
	 */
	@Override
	public CommonResult<?> resetUserPassword(Long userId, String password) {
		final String loginName = selectUserLoginNameByUserId(userId);
		String msg = "";
		if (StringUtils.isEmpty(loginName)) {
			msg = MessageUtils.message("UserServiceImpl.9"); //$NON-NLS-1$
		} else if (StringUtils.isEmpty(password)) {
			msg = MessageUtils.message("UserServiceImpl.10"); //$NON-NLS-1$
		} else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
				|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			msg = MessageUtils.message("UserServiceImpl.11"); //$NON-NLS-1$
		}
		if (!StringUtils.isEmpty(msg)) {
			return CommonResult.fail(msg);
		}
		final SysUser resetPwd = new SysUser();
		resetPwd.setUserId(userId);
		resetPwd.setSalt(StringUtils.randomNum(6));
		resetPwd.setPassword(StringUtils.encryptPassword(loginName, password, resetPwd.getSalt()));
		return CommonResult.ajax(userService.resetUserPwd(resetPwd));
	}

	/**
	 * Select user accounts from email.
	 *
	 * @param userId   the user id
	 * @param userType the user type
	 * @return the common result
	 */
	@Override
	public CommonResult<List<String>> selectUserAccountsFromEmail(Long userId, String userType) {
		if (userId == null) {
			return CommonResult.fail(MessageUtils.message("UserServiceImpl.27"));
		}
		return CommonResult.build(userProfileMapper.selectUserAccountsFromEmail(userId, userType));
	}

	/**
	 * Update user mobile.
	 *
	 * @param userId the user id
	 * @param mobile the mobile
	 * @return the ajax result
	 */
	@Override
	public AjaxResult updateUserMobile(Long userId, String mobile) {
		if (userId == null || StringUtils.isEmpty(mobile)) {
			return AjaxResult.error("参数错误");
		}
		final SysUser user = new SysUser();
		user.setUserId(userId);
		user.setPhonenumber(mobile);
		final int rows = userService.updateUserInfo(user);
		if (rows > 0) {
			return AjaxResult.success("修改成功");
		} else {
			return AjaxResult.error("内部错误");
		}
	}

	/**
	 * Update user email.
	 *
	 * @param userId the user id
	 * @param email  the email
	 * @return the ajax result
	 */
	@Override
	public AjaxResult updateUserEmail(Long userId, String email) {
		if (userId == null || StringUtils.isEmpty(email)) {
			return AjaxResult.error("参数错误");
		}
		final SysUser user = new SysUser();
		user.setUserId(userId);
		user.setEmail(email);
		final int rows = userService.updateUserInfo(user);
		if (rows > 0) {
			CacheUtils.put(USER_ID_2_EMAIL_CACHE, userId.toString(), email);
			CacheUtils.put(USER_LOGIN_NAME_2_EMAIL_CACHE, selectUserLoginNameByUserId(userId), email);
			return AjaxResult.success("修改成功");
		} else {
			return AjaxResult.error("内部错误");
		}
	}

	/**
	 * Select user ids by type.
	 *
	 * @param userType the user type
	 * @return the list
	 */
	@Override
	public List<Long> selectUserIdsByType(String userType) {
		return userProfileMapper.selectUserIdsByType(userType);
	}

	/**
	 * Fuzzy search user list.
	 *
	 * @param loginName the login name
	 * @return the list
	 */
	@Override
	public List<UserProfile> fuzzySearchUserList(String loginName) {
		return userProfileMapper.fuzzySearchUserList(loginName);
	}

	/**
	 * Fuzzy search user list by mobile.
	 *
	 * @param mobile the mobile
	 * @return the list
	 */
	@Override
	public List<UserProfile> fuzzySearchUserListByMobile(String mobile) {
		return userProfileMapper.fuzzySearchUserListByMobile(mobile);
	}

	/**
	 * Select user profile list.
	 *
	 * @param userIds the user ids
	 * @return the list
	 */
	@Override
	public List<UserProfile> selectUserProfileList(List<Long> userIds) {
		if (userIds == null || userIds.isEmpty()) {
			return Collections.emptyList();
		}
		return userProfileMapper.selectUserProfileList(userIds);
	}
}
