package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserNotLoginException.
 */
public class UserNotLoginException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3876665978896522335L;

	/**
	 * Instantiates a new user not login exception.
	 */
	public UserNotLoginException() {
	}

	/**
	 * Instantiates a new user not login exception.
	 *
	 * @param message the message
	 */
	public UserNotLoginException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new user not login exception.
	 *
	 * @param cause the cause
	 */
	public UserNotLoginException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new user not login exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public UserNotLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new user not login exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public UserNotLoginException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
