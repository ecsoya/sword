package com.github.ecsoya.sword.code.handler;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

public interface SendMailCodeHandler extends SendCodeHandler {

	CommonResult<?> sendEmail(String email, String subject, String content);

	CommonResult<?> sendCode(String email, String code);

	CommonResult<?> sendCode(String email, String code, String subject, String template);

}
