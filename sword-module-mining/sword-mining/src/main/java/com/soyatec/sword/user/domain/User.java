package com.soyatec.sword.user.domain;

import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.domain.entity.SysUser;

public class User extends SysUser {

	public static final String TYPE_USER = UserConstants.REGISTER_USER_TYPE;
	public static final String TYPE_WALLET = TYPE_USER;
	public static final String TYPE_ANONYMOUS = "03";// 匿名用户
	/**
	 *
	 */
	private static final long serialVersionUID = 6149988960819129465L;

	public User() {
		setUserType(TYPE_USER);
	}

}
