package com.github.ecsoya.sword.common.password;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

/**
 * The Interface PasswordEncryptService.
 */
public interface PasswordEncryptService {

	/**
	 * Encrypt password.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return the string
	 */
	String encryptPassword(SysUser user, String password);

	/**
	 * Matches password.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return true, if successful
	 */
	boolean matchesPassword(SysUser user, String password);

}
