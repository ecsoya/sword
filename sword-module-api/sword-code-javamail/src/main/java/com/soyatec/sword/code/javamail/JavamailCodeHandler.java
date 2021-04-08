package com.soyatec.sword.code.javamail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.code.config.CodeProperties;
import com.soyatec.sword.code.handler.SendMailCodeHandler;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;

@Service
public class JavamailCodeHandler implements SendMailCodeHandler {

	@Autowired
	private JavamailCodeProperties config;

	@Autowired
	private JavamailService service;

	@Override
	public CommonResult<?> sendEmail(String email, String subject, String content) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(content)) {
			return CommonResult.fail("参数错误");
		}
		if (StringUtils.isEmpty(subject)) {
			subject = config.getSubject();
		}
		return service.sendEmail(email, subject, content);
	}

	@Override
	public CommonResult<?> sendCode(String email, String code) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("参数错误");
		}
		String subject = config.getSubject();
		String template = config.getTemplate();
		if (StringUtils.isEmpty(template)) {
			template = CodeProperties.DEFAULT_TEMPLATE;
		}
		String content = StringUtils.format(template, code, code);
		return sendEmail(email, subject, content);
	}

}
