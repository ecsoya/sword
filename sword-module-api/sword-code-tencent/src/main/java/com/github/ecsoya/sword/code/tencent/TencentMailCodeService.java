package com.github.ecsoya.sword.code.tencent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.tencentcloudapi.ses.v20201002.models.SendEmailRequest;
import com.tencentcloudapi.ses.v20201002.models.SendEmailResponse;
import com.tencentcloudapi.ses.v20201002.models.Template;

/**
 * The Class TencentMailCodeService.
 */
@Component
public class TencentMailCodeService {

	/** The config. */
	@Autowired
	private TencentCodeProperties config;

	/**
	 * Send mail.
	 *
	 * @param email        the email
	 * @param subject      the subject
	 * @param templateId   the template id
	 * @param templateData the template data
	 * @return the send email response
	 */
	public SendEmailResponse sendMail(String email, String subject, Long templateId, String templateData) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		final Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
		final SesClient client = new SesClient(cred, config.getRegion());
		final SendEmailRequest request = new SendEmailRequest();
		request.setFromEmailAddress(config.getFrom());
		request.setDestination(new String[] { email });
		request.setSubject(subject);
		final Template template = new Template();
		template.setTemplateID(templateId);
		template.setTemplateData(templateData);
		request.setTemplate(template);
		try {
			return client.SendEmail(request);
		} catch (final TencentCloudSDKException e) {
			return null;
		}
	}

	/**
	 * Send code.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param code    the code
	 * @param html    the html
	 * @return the send email response
	 */
	public SendEmailResponse sendCode(String email, String subject, String code, boolean html) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		final Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
		final SesClient client = new SesClient(cred, config.getRegion());
		final SendEmailRequest request = new SendEmailRequest();
		request.setFromEmailAddress(config.getFrom());
		request.setDestination(new String[] { email });
		request.setSubject(subject);
		final Template template = new Template();
		if (html) {
			template.setTemplateID(config.getHtmlTemplateId());
		} else {
			template.setTemplateID(config.getTextTemplateId());
		}
		template.setTemplateData("{\"code\": \"" + code + "\"}");
		request.setTemplate(template);
		try {
			return client.SendEmail(request);
		} catch (final TencentCloudSDKException e) {
			return null;
		}
	}
}
