package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserPasswordRetryLimitExceedException.
 */
public class UserPasswordRetryLimitExceedException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user password retry limit exceed exception.
	 *
	 * @param retryLimitCount the retry limit count
	 */
	public UserPasswordRetryLimitExceedException(int retryLimitCount) {
		super("user.password.retry.limit.exceed", new Object[] { retryLimitCount });
	}
}
