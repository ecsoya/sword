package com.github.ecsoya.sword.common.exception;

/**
 * The Class UtilException.
 */
public class UtilException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8247610319171014183L;

	/**
	 * Instantiates a new util exception.
	 *
	 * @param e the e
	 */
	public UtilException(Throwable e) {
		super(e.getMessage(), e);
	}

	/**
	 * Instantiates a new util exception.
	 *
	 * @param message the message
	 */
	public UtilException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new util exception.
	 *
	 * @param message   the message
	 * @param throwable the throwable
	 */
	public UtilException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
