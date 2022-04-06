package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.system.domain.SysUserRole;

/**
 * The Interface ISysUserService.
 */
public interface ISysUserService {

	/**
	 * Select user list.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<SysUser> selectUserList(SysUser user);

	/**
	 * Select allocated list.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<SysUser> selectAllocatedList(SysUser user);

	/**
	 * Select unallocated list.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<SysUser> selectUnallocatedList(SysUser user);

	/**
	 * Select user by login name.
	 *
	 * @param userName the user name
	 * @return the sys user
	 */
	public SysUser selectUserByLoginName(String userName);

	/**
	 * Select user by phone number.
	 *
	 * @param phoneNumber the phone number
	 * @return the sys user
	 */
	public SysUser selectUserByPhoneNumber(String phoneNumber);

	/**
	 * Select user by email.
	 *
	 * @param email the email
	 * @return the sys user
	 */
	public SysUser selectUserByEmail(String email);

	/**
	 * Select user by id.
	 *
	 * @param userId the user id
	 * @return the sys user
	 */
	public SysUser selectUserById(Long userId);

	/**
	 * Select user role by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysUserRole> selectUserRoleByUserId(Long userId);

	/**
	 * Delete user by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserById(Long userId);

	/**
	 * Delete user by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserByIds(String ids);

	/**
	 * Insert user.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int insertUser(SysUser user);

	/**
	 * Register user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean registerUser(SysUser user);

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int updateUser(SysUser user);

	/**
	 * Update user info.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int updateUserInfo(SysUser user);

	/**
	 * Insert user auth.
	 *
	 * @param userId  the user id
	 * @param roleIds the role ids
	 */
	public void insertUserAuth(Long userId, Long[] roleIds);

	/**
	 * Reset user pwd.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int resetUserPwd(SysUser user);

	/**
	 * Check login name unique.
	 *
	 * @param loginName the login name
	 * @return the string
	 */
	public String checkLoginNameUnique(String loginName);

	/**
	 * Check phone unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	public String checkPhoneUnique(SysUser user);

	/**
	 * Check email unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	public String checkEmailUnique(SysUser user);

	/**
	 * Check user allowed.
	 *
	 * @param user the user
	 */
	public void checkUserAllowed(SysUser user);

	/**
	 * Select user role group.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	public String selectUserRoleGroup(Long userId);

	/**
	 * Select user post group.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	public String selectUserPostGroup(Long userId);

	/**
	 * Import user.
	 *
	 * @param userList        the user list
	 * @param isUpdateSupport the is update support
	 * @param operName        the oper name
	 * @return the string
	 */
	public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName);

	/**
	 * Change status.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int changeStatus(SysUser user);
}
