package com.github.ecsoya.sword.code.tencent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.code.handler.SendMailCodeHandler;
import com.github.ecsoya.sword.code.registry.IMailCodeHandlerRegistry;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.google.auto.service.AutoService;
import com.tencentcloudapi.ses.v20201002.models.SendEmailResponse;

/**
 * The Class TencentMailCodeHandler.
 */
@Component
@AutoService(IMailCodeHandlerRegistry.class)
public class TencentMailCodeHandler implements SendMailCodeHandler, IMailCodeHandlerRegistry {

	/** The config. */
	@Autowired
	private TencentCodeProperties config;

	/** The servie. */
	@Autowired
	private TencentMailCodeService servie;

	/**
	 * Gets the.
	 *
	 * @return the send mail code handler
	 */
	@Override
	public SendMailCodeHandler get() {
		return SpringUtils.getBean(TencentMailCodeHandler.class);
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
		return CommonResult.fail("尚未实现");
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
		final SendEmailResponse sent = servie.sendCode(email, config.getSubject(), code,
				config.getHtmlTemplateId() != null);
		if (sent != null) {
			return CommonResult.success(sent);
		}
		return CommonResult.fail("发送失败");
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
		return sendCode(email, code);
	}

}
