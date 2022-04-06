package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserNotExistsException.
 */
public class UserNotExistsException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user not exists exception.
	 */
	public UserNotExistsException() {
		super("user.not.exists", null);
	}
}
