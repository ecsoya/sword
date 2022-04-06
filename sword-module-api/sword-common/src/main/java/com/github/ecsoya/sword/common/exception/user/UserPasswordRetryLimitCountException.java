package com.github.ecsoya.sword.common.exception.user;

/**
 * The Class UserPasswordRetryLimitCountException.
 */
public class UserPasswordRetryLimitCountException extends UserException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user password retry limit count exception.
	 *
	 * @param retryLimitCount the retry limit count
	 */
	public UserPasswordRetryLimitCountException(int retryLimitCount) {
		super("user.password.retry.limit.count", new Object[] { retryLimitCount });
	}
}
