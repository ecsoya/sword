package com.github.ecsoya.sword.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface IMobileCodeService.
 */
public interface IMobileCodeService {

	/**
	 * Send code.
	 *
	 * @param mobile the mobile
	 * @return the common result
	 */
	public CommonResult<?> sendCode(String mobile);

	/**
	 * Verify code.
	 *
	 * @param mobile the mobile
	 * @param code   the code
	 * @return the common result
	 */
	public CommonResult<?> verifyCode(String mobile, String code);

	/**
	 * Send code by user id.
	 *
	 * @param userId the user id
	 * @return the common result
	 */
	public CommonResult<?> sendCodeByUserId(Long userId);

	/**
	 * Verify code by user id.
	 *
	 * @param userId the user id
	 * @param code   the code
	 * @return the common result
	 */
	public CommonResult<?> verifyCodeByUserId(Long userId, String code);

	/**
	 * Verify code by username.
	 *
	 * @param username the username
	 * @param code     the code
	 * @return the common result
	 */
	public CommonResult<?> verifyCodeByUsername(String username, String code);

	/**
	 * Send code by username.
	 *
	 * @param username the username
	 * @return the common result
	 */
	public CommonResult<?> sendCodeByUsername(String username);
}
