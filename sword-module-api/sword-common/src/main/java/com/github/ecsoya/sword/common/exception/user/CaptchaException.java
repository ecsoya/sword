package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class CaptchaException.
 */
public class CaptchaException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new captcha exception.
	 */
	public CaptchaException() {
		super("user.jcaptcha.error", null);
	}
}
