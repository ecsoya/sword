package com.github.ecsoya.sword.code.handler;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface SendMailCodeHandler.
 */
public interface SendMailCodeHandler extends SendCodeHandler {

	/**
	 * Send email.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param content the content
	 * @return the common result
	 */
	CommonResult<?> sendEmail(String email, String subject, String content);

	/**
	 * Send code.
	 *
	 * @param email the email
	 * @param code  the code
	 * @return the common result
	 */
	CommonResult<?> sendCode(String email, String code);

	/**
	 * Send code.
	 *
	 * @param email    the email
	 * @param code     the code
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	CommonResult<?> sendCode(String email, String code, String subject, String template);

}
