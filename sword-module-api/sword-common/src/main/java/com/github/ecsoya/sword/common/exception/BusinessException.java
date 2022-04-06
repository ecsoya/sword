package com.github.ecsoya.sword.common.exception;

/**
 * The Class BusinessException.
 */
public class BusinessException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The message. */
	protected final String message;

	/**
	 * Instantiates a new business exception.
	 *
	 * @param message the message
	 */
	public BusinessException(String message) {
		this.message = message;
	}

	/**
	 * Instantiates a new business exception.
	 *
	 * @param message the message
	 * @param e       the e
	 */
	public BusinessException(String message, Throwable e) {
		super(message, e);
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
