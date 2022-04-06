package com.github.ecsoya.sword.upload.core;

/**
 * The Class FileUploadException.
 */
public class FileUploadException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new file upload exception.
	 */
	public FileUploadException() {
		super();
	}

	/**
	 * Instantiates a new file upload exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public FileUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Instantiates a new file upload exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public FileUploadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new file upload exception.
	 *
	 * @param message the message
	 */
	public FileUploadException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new file upload exception.
	 *
	 * @param cause the cause
	 */
	public FileUploadException(Throwable cause) {
		super(cause);
	}

}
