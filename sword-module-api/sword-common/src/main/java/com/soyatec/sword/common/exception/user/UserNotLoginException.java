package com.soyatec.sword.common.exception.user;

public class UserNotLoginException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -3876665978896522335L;

	public UserNotLoginException() {
	}

	public UserNotLoginException(String message) {
		super(message);
	}

	public UserNotLoginException(Throwable cause) {
		super(cause);
	}

	public UserNotLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotLoginException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
