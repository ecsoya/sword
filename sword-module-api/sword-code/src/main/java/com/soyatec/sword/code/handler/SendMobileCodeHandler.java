package com.soyatec.sword.code.handler;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface SendMobileCodeHandler extends SendCodeHandler {

	CommonResult<?> sendCode(String mobile, String code);

	CommonResult<?> sendMessage(String mobile, String message);

}
