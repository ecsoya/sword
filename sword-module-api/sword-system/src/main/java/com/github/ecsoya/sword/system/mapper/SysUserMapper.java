package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

/**
 * The Interface SysUserMapper.
 */
public interface SysUserMapper {

	/**
	 * Select user list.
	 *
	 * @param sysUser the sys user
	 * @return the list
	 */
	public List<SysUser> selectUserList(SysUser sysUser);

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
	public int deleteUserByIds(Long[] ids);

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int updateUser(SysUser user);

	/**
	 * Insert user.
	 *
	 * @param user the user
	 * @return the int
	 */
	public int insertUser(SysUser user);

	/**
	 * Check login name unique.
	 *
	 * @param loginName the login name
	 * @return the int
	 */
	public int checkLoginNameUnique(String loginName);

	/**
	 * Check phone unique.
	 *
	 * @param phonenumber the phonenumber
	 * @param userType    the user type
	 * @return the sys user
	 */
	public SysUser checkPhoneUnique(@Param("phonenumber") String phonenumber, @Param("userType") String userType);

	/**
	 * Check email unique.
	 *
	 * @param email    the email
	 * @param userType the user type
	 * @return the sys user
	 */
	public SysUser checkEmailUnique(@Param("email") String email, @Param("userType") String userType);
}
