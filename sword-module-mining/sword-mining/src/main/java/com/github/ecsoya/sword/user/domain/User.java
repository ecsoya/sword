package com.github.ecsoya.sword.user.domain;

import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

/**
 * The Class User.
 */
public class User extends SysUser {

	/** The Constant TYPE_USER. */
	public static final String TYPE_USER = UserConstants.REGISTER_USER_TYPE;

	/** The Constant TYPE_WALLET. */
	public static final String TYPE_WALLET = TYPE_USER;

	/** The Constant TYPE_ANONYMOUS. */
	public static final String TYPE_ANONYMOUS = "03";// 匿名用户

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6149988960819129465L;

	/**
	 * Instantiates a new user.
	 */
	public User() {
		setUserType(TYPE_USER);
	}

}
