package com.soyatec.sword.code.tencent;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.soyatec.sword.code.config.CodeProperties;

@ConfigurationProperties(prefix = "sword.code.tencent")
@Configuration
public class TencentCodeProperties extends CodeProperties {

	private String secretId;

	private String secretKey;

	private String from;

	private String fromName;

	private String subject;

	private String region = "ap-hongkong";

	private Long textTemplateId;

	private Long htmlTemplateId;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSecretId() {
		return secretId;
	}

	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Long getTextTemplateId() {
		return textTemplateId;
	}

	public void setTextTemplateId(Long textTemplateId) {
		this.textTemplateId = textTemplateId;
	}

	public Long getHtmlTemplateId() {
		return htmlTemplateId;
	}

	public void setHtmlTemplateId(Long htmlTemplateId) {
		this.htmlTemplateId = htmlTemplateId;
	}
}
