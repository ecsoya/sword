package com.github.ecsoya.sword.code.sendcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.code.config.CodeProperties;
import com.github.ecsoya.sword.code.handler.SendMailCodeHandler;
import com.github.ecsoya.sword.code.registry.IMailCodeHandlerRegistry;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.google.auto.service.AutoService;
import com.sendcloud.sdk.util.ResponseData;

@Component
@AutoService(IMailCodeHandlerRegistry.class)
public class SendCloudEmailCodeHandler implements SendMailCodeHandler, IMailCodeHandlerRegistry {

	@Autowired
	private SendCloudProperties properties;

	@Autowired
	private SendCloudService sendCloudService;

	@Override
	public SendMailCodeHandler get() {
		return SpringUtils.getBean(SendCloudEmailCodeHandler.class);
	}

	@Override
	public int getPriority() {
		return properties.getPriority();
	}

	@Override
	public CommonResult<?> sendEmail(String email, String subject, String content) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(content)) {
			return CommonResult.fail("参数错误");
		}
		try {
			final ResponseData sent = sendCloudService.sendCommon(email, subject, content);
			if (sent.statusCode == 200) {
				return CommonResult.success();
			}
			return CommonResult.fail(sent.getInfo());
		} catch (final Throwable e) {
			return CommonResult.fail(e.getLocalizedMessage());
		}
	}

	@Override
	public CommonResult<?> sendCode(String email, String code) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("参数错误");
		}
		final String subject = properties.getSubject(LocaleContextHolder.getLocale());
		String template = properties.getTemplate(LocaleContextHolder.getLocale());
		if (StringUtils.isEmpty(template)) {
			template = CodeProperties.DEFAULT_TEMPLATE;
		}
		final String content = StringUtils.formatAll(template, code);
		return sendEmail(email, subject, content);
	}

	@Override
	public CommonResult<?> sendCode(String email, String code, String subject, String template) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("参数错误");
		}
		if (StringUtils.isEmpty(subject)) {
			subject = properties.getSubject(LocaleContextHolder.getLocale());
		}
		if (StringUtils.isEmpty(template)) {
			template = properties.getTemplate(LocaleContextHolder.getLocale());
		}
		if (StringUtils.isEmpty(template)) {
			template = CodeProperties.DEFAULT_TEMPLATE;
		}
		final String content = StringUtils.formatAll(template, code);
		return sendEmail(email, subject, content);
	}

}
