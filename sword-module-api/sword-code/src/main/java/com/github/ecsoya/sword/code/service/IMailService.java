package com.github.ecsoya.sword.code.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface IMailService.
 */
public interface IMailService {

	/**
	 * Send email.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param content the content
	 * @return the common result
	 */
	public CommonResult<?> sendEmail(String email, String subject, String content);

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
	 * Send code.
	 *
	 * @param email the email
	 * @return the common result
	 */
	public CommonResult<?> sendCode(String email);

	/**
	 * Verify code.
	 *
	 * @param email the email
	 * @param code  the code
	 * @return the common result
	 */
	public CommonResult<?> verifyCode(String email, String code);

}
