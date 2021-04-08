package com.soyatec.sword.code.tencent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soyatec.sword.common.utils.StringUtils;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.tencentcloudapi.ses.v20201002.models.SendEmailRequest;
import com.tencentcloudapi.ses.v20201002.models.SendEmailResponse;
import com.tencentcloudapi.ses.v20201002.models.Template;

@Component
public class TencentCodeService {

	@Autowired
	private TencentCodeProperties config;

	public SendEmailResponse sendMail(String email, String subject, Long templateId, String templateData) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
		SesClient client = new SesClient(cred, config.getRegion());
		SendEmailRequest request = new SendEmailRequest();
		request.setFromEmailAddress(config.getFrom());
		request.setDestination(new String[] { email });
		request.setSubject(subject);
		Template template = new Template();
		template.setTemplateID(templateId);
		template.setTemplateData(templateData);
		request.setTemplate(template);
		try {
			return client.SendEmail(request);
		} catch (TencentCloudSDKException e) {
			return null;
		}
	}

	public SendEmailResponse sendCode(String email, String subject, String code, boolean html) {
		if (StringUtils.isEmpty(email)) {
			return null;
		}
		Credential cred = new Credential(config.getSecretId(), config.getSecretKey());
		SesClient client = new SesClient(cred, config.getRegion());
		SendEmailRequest request = new SendEmailRequest();
		request.setFromEmailAddress(config.getFrom());
		request.setDestination(new String[] { email });
		request.setSubject(subject);
		Template template = new Template();
		if (html) {
			template.setTemplateID(config.getHtmlTemplateId());
		} else {
			template.setTemplateID(config.getTextTemplateId());
		}
		template.setTemplateData("{\"code\": \"" + code + "\"}");
		request.setTemplate(template);
		try {
			return client.SendEmail(request);
		} catch (TencentCloudSDKException e) {
			return null;
		}
	}
}
