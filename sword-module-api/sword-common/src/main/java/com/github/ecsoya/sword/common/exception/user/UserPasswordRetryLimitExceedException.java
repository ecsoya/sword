package com.github.ecsoya.sword.common.exception.user;

/**
 * 用户错误最大次数异常类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class UserPasswordRetryLimitExceedException extends UserException {
	private static final long serialVersionUID = 1L;

	public UserPasswordRetryLimitExceedException(int retryLimitCount) {
		super("user.password.retry.limit.exceed", new Object[] { retryLimitCount });
	}
}