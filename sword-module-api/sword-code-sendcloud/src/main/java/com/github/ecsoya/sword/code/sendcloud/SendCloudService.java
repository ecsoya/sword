package com.github.ecsoya.sword.code.sendcloud;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sendcloud.sdk.builder.SendCloudBuilder;
import com.sendcloud.sdk.config.Credential;
import com.sendcloud.sdk.core.SendCloud;
import com.sendcloud.sdk.model.MailAddressReceiver;
import com.sendcloud.sdk.model.MailBody;
import com.sendcloud.sdk.model.SendCloudMail;
import com.sendcloud.sdk.model.TemplateContent;
import com.sendcloud.sdk.model.TextContent;
import com.sendcloud.sdk.model.TextContent.ScContentType;
import com.sendcloud.sdk.util.ResponseData;

/**
 * The Class SendCloudService.
 */
@Component
public class SendCloudService {

	/** The config. */
	@Autowired
	private SendCloudProperties config;

	/**
	 * Send common.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param html    the html
	 * @return the response data
	 * @throws Throwable the throwable
	 */
	public ResponseData sendCommon(String email, String subject, String html) throws Throwable {
		final MailAddressReceiver receiver = new MailAddressReceiver();
		// 添加收件人
		receiver.addTo(email);

		final MailBody body = new MailBody();
		// 设置 From
		body.setFrom(config.getFrom());
		// 设置 FromName
		body.setFromName(config.getFromName());
		// 设置标题
		body.setSubject(subject);

		final TextContent content = new TextContent();
		content.setContent_type(ScContentType.html);
		content.setText(html);

		final SendCloudMail mail = new SendCloudMail();
		mail.setTo(receiver);
		mail.setBody(body);
		mail.setContent(content);

		final SendCloud sc = SendCloudBuilder.build();
		final Credential credential = new Credential(config.getApiUser(), config.getApiKey());
		return sc.sendMail(mail, credential, config.getRegion());
	}

	/**
	 * Send template.
	 *
	 * @param email    the email
	 * @param subject  the subject
	 * @param template the template
	 * @return the response data
	 * @throws Throwable the throwable
	 */
	public ResponseData sendTemplate(String email, String subject, String template) throws Throwable {
		final MailBody body = new MailBody();
		// 设置 From
		body.setFrom(config.getFrom());
		// 设置 FromName
		body.setFromName(config.getFromName());
		body.setSubject(subject);
		body.addXsmtpapi("to", Collections.singletonList(email));
		// 使用邮件模板
		final TemplateContent content = new TemplateContent();
		content.setTemplateInvokeName(template);

		final SendCloudMail mail = new SendCloudMail();
		// 模板发送时, 必须使用 Xsmtpapi 来指明收件人; mail.setTo();
		mail.setBody(body);
		mail.setContent(content);

		final SendCloud sc = SendCloudBuilder.build();
		final Credential credential = new Credential(config.getApiUser(), config.getApiKey());
		return sc.sendMail(mail, credential, config.getRegion());
	}

}
