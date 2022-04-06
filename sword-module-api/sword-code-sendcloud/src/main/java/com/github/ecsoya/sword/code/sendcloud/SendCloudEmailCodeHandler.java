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

/**
 * The Class SendCloudEmailCodeHandler.
 */
@Component
@AutoService(IMailCodeHandlerRegistry.class)
public class SendCloudEmailCodeHandler implements SendMailCodeHandler, IMailCodeHandlerRegistry {

	/** The properties. */
	@Autowired
	private SendCloudProperties properties;

	/** The send cloud service. */
	@Autowired
	private SendCloudService sendCloudService;

	/**
	 * Gets the.
	 *
	 * @return the send mail code handler
	 */
	@Override
	public SendMailCodeHandler get() {
		return SpringUtils.getBean(SendCloudEmailCodeHandler.class);
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Override
	public int getPriority() {
		return properties.getPriority();
	}

	/**
	 * Send email.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param content the content
	 * @return the common result
	 */
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

	/**
	 * Send code.
	 *
	 * @param email the email
	 * @param code  the code
	 * @return the common result
	 */
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

	/**
	 * Send code.
	 *
	 * @param email    the email
	 * @param code     the code
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
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
