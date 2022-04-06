package com.github.ecsoya.sword.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface IMailCodeService.
 */
public interface IMailCodeService {

	/**
	 * Send code.
	 *
	 * @param email the email
	 * @return the common result
	 */
	public CommonResult<?> sendCode(String email);

	/**
	 * Send code.
	 *
	 * @param email    the email
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	public CommonResult<?> sendCode(String email, String subject, String template);

	/**
	 * Verify code.
	 *
	 * @param email the email
	 * @param code  the code
	 * @return the common result
	 */
	public CommonResult<?> verifyCode(String email, String code);

	/**
	 * Send code by user id.
	 *
	 * @param userId the user id
	 * @return the common result
	 */
	public CommonResult<?> sendCodeByUserId(Long userId);

	/**
	 * Send code by user id.
	 *
	 * @param userId   the user id
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	public CommonResult<?> sendCodeByUserId(Long userId, String subject, String template);

	/**
	 * Send code by username.
	 *
	 * @param username the username
	 * @return the common result
	 */
	public CommonResult<?> sendCodeByUsername(String username);

	/**
	 * Send code by username.
	 *
	 * @param username the username
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	public CommonResult<?> sendCodeByUsername(String username, String subject, String template);

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
}
