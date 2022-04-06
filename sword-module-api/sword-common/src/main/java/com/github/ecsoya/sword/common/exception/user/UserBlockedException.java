package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserBlockedException.
 */
public class UserBlockedException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user blocked exception.
	 */
	public UserBlockedException() {
		super("user.blocked", null);
	}
}
