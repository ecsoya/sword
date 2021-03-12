package com.soyatec.sword.handler;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface SendMobileCodeHandler {

	CommonResult<?> send(String mobile, String code);

}
