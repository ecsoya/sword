package com.github.ecsoya.sword.code.handler;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

public interface SendMobileCodeHandler extends SendCodeHandler {

	CommonResult<?> sendCode(String mobile, String code);

	CommonResult<?> sendMessage(String mobile, String message);

}
