package com.github.ecsoya.sword.common.exception.user;

/**
 * 用户账号已被删除
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class UserDeleteException extends UserException {
	private static final long serialVersionUID = 1L;

	public UserDeleteException() {
		super("user.password.delete", null);
	}
}