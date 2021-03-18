package com.soyatec.sword.code.handler;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface SendMailCodeHandler {

	CommonResult<?> sendEmail(String email, String subject, String content);

	CommonResult<?> sendCode(String email, String code);

}