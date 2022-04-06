package com.github.ecsoya.sword.common.exception.user;

import com.github.ecsoya.sword.common.exception.base.BaseException;

/**
 * The Class UserException.
 */
public class UserException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new user exception.
	 *
	 * @param code the code
	 * @param args the args
	 */
	public UserException(String code, Object[] args) {
		super("user", code, args, null);
	}
}
