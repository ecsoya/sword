package com.soyatec.sword.handler;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface SendMailCodeHandler {

	CommonResult<?> send(String email, String subject, String code);

}
