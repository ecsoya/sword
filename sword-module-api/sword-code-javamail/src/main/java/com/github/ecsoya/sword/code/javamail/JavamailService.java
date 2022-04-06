package com.github.ecsoya.sword.code.javamail;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Class JavamailService.
 */
@Component
public class JavamailService {

	/** The config. */
	@Autowired
	private JavamailCodeProperties config;

	/**
	 * Send email.
	 *
	 * @param address the address
	 * @param subject the subject
	 * @param content the content
	 * @return the common result
	 */
	public CommonResult<?> sendEmail(String address, String subject, String content) {
		try {
			final HtmlEmail email = new HtmlEmail();

			email.setHostName(config.getHost());
			email.setSSLOnConnect(config.isSsl());
			email.setCharset(config.getCharset());
			email.addTo(address);
			email.setFrom(config.getFrom(), config.getFromName());
			email.setAuthentication(config.getFrom(), config.getPassword());

			email.setSubject(subject);
			email.setHtmlMsg(content);

			email.send();
		} catch (final EmailException e) {
			return CommonResult.fail(e.getLocalizedMessage());
		}

		return CommonResult.success(address);
	}

}
