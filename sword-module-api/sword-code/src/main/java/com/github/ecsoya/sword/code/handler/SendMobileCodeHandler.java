package com.github.ecsoya.sword.code.handler;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface SendMobileCodeHandler.
 */
public interface SendMobileCodeHandler extends SendCodeHandler {

	/**
	 * Send code.
	 *
	 * @param mobile the mobile
	 * @param code   the code
	 * @return the common result
	 */
	CommonResult<?> sendCode(String mobile, String code);

	/**
	 * Send message.
	 *
	 * @param mobile  the mobile
	 * @param message the message
	 * @return the common result
	 */
	CommonResult<?> sendMessage(String mobile, String message);

}
