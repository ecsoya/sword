package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserPasswordNotMatchException.
 */
public class UserPasswordNotMatchException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user password not match exception.
	 */
	public UserPasswordNotMatchException() {
		super("user.password.not.match", null);
	}
}
