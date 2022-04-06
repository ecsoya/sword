package com.github.ecsoya.sword.code.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface IMobileService.
 */
public interface IMobileService {

	/**
	 * Send message.
	 *
	 * @param mobile  the mobile
	 * @param message the message
	 * @return the common result
	 */
	public CommonResult<?> sendMessage(String mobile, String message);

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

}
