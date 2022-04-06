package com.github.ecsoya.sword.code.javamail;

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

/**
 * The Class JavamailCodeHandler.
 */
@Component
@AutoService(IMailCodeHandlerRegistry.class)
public class JavamailCodeHandler implements SendMailCodeHandler, IMailCodeHandlerRegistry {

	/** The config. */
	@Autowired
	private JavamailCodeProperties config;

	/** The service. */
	@Autowired
	private JavamailService service;

	/**
	 * Gets the.
	 *
	 * @return the send mail code handler
	 */
	@Override
	public SendMailCodeHandler get() {
		return SpringUtils.getBean(JavamailCodeHandler.class);
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Override
	public int getPriority() {
		return config.getPriority();
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
		if (StringUtils.isEmpty(subject)) {
			subject = config.getSubject(LocaleContextHolder.getLocale());
		}
		return service.sendEmail(email, subject, content);
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
		final String subject = config.getSubject(LocaleContextHolder.getLocale());
		String template = config.getTemplate(LocaleContextHolder.getLocale());
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
			subject = config.getSubject(LocaleContextHolder.getLocale());
		}
		if (StringUtils.isEmpty(template)) {
			template = config.getTemplate(LocaleContextHolder.getLocale());
		}
		if (StringUtils.isEmpty(template)) {
			template = CodeProperties.DEFAULT_TEMPLATE;
		}
		final String content = StringUtils.formatAll(template, code);
		return sendEmail(email, subject, content);
	}

}
