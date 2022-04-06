package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserDeleteException.
 */
public class UserDeleteException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user delete exception.
	 */
	public UserDeleteException() {
		super("user.password.delete", null);
	}
}
