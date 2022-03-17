package com.github.ecsoya.sword.code.tencent;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.code.config.CodeProperties;

@ConfigurationProperties(prefix = "sword.code.tencent")
@Configuration
public class TencentCodeProperties extends CodeProperties {

	private String secretId;

	private String secretKey;

	private String from;

	private String fromName;

	private String region = "ap-hongkong";

	private Long textTemplateId;

	private Long htmlTemplateId;

	/**
	 * 短信
	 */
	private Long templateId;

	/**
	 * 短信
	 */
	private String sdkAppId;

	/**
	 * 短信签名
	 */
	private String signName;

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

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public String getSdkAppId() {
		return sdkAppId;
	}

	public void setSdkAppId(String sdkAppId) {
		this.sdkAppId = sdkAppId;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}
}
