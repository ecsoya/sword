package com.github.ecsoya.sword.code.tencent;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.code.config.CodeProperties;

/**
 * The Class TencentCodeProperties.
 */
@ConfigurationProperties(prefix = "sword.code.tencent")
@Configuration
public class TencentCodeProperties extends CodeProperties {

	/** The secret id. */
	private String secretId;

	/** The secret key. */
	private String secretKey;

	/** The from. */
	private String from;

	/** The from name. */
	private String fromName;

	/** The region. */
	private String region = "ap-hongkong";

	/** The text template id. */
	private Long textTemplateId;

	/** The html template id. */
	private Long htmlTemplateId;

	/** The template id. */
	private Long templateId;

	/** The sdk app id. */
	private String sdkAppId;

	/** The sign name. */
	private String signName;

	/**
	 * Gets the from.
	 *
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * Sets the from.
	 *
	 * @param from the new from
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * Gets the from name.
	 *
	 * @return the from name
	 */
	public String getFromName() {
		return fromName;
	}

	/**
	 * Sets the from name.
	 *
	 * @param fromName the new from name
	 */
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Gets the secret id.
	 *
	 * @return the secret id
	 */
	public String getSecretId() {
		return secretId;
	}

	/**
	 * Sets the secret id.
	 *
	 * @param secretId the new secret id
	 */
	public void setSecretId(String secretId) {
		this.secretId = secretId;
	}

	/**
	 * Gets the secret key.
	 *
	 * @return the secret key
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * Sets the secret key.
	 *
	 * @param secretKey the new secret key
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Gets the text template id.
	 *
	 * @return the text template id
	 */
	public Long getTextTemplateId() {
		return textTemplateId;
	}

	/**
	 * Sets the text template id.
	 *
	 * @param textTemplateId the new text template id
	 */
	public void setTextTemplateId(Long textTemplateId) {
		this.textTemplateId = textTemplateId;
	}

	/**
	 * Gets the html template id.
	 *
	 * @return the html template id
	 */
	public Long getHtmlTemplateId() {
		return htmlTemplateId;
	}

	/**
	 * Sets the html template id.
	 *
	 * @param htmlTemplateId the new html template id
	 */
	public void setHtmlTemplateId(Long htmlTemplateId) {
		this.htmlTemplateId = htmlTemplateId;
	}

	/**
	 * Gets the 短信.
	 *
	 * @return the 短信
	 */
	public Long getTemplateId() {
		return templateId;
	}

	/**
	 * Sets the 短信.
	 *
	 * @param templateId the new 短信
	 */
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	/**
	 * Gets the 短信.
	 *
	 * @return the 短信
	 */
	public String getSdkAppId() {
		return sdkAppId;
	}

	/**
	 * Sets the 短信.
	 *
	 * @param sdkAppId the new 短信
	 */
	public void setSdkAppId(String sdkAppId) {
		this.sdkAppId = sdkAppId;
	}

	/**
	 * Gets the 短信签名.
	 *
	 * @return the 短信签名
	 */
	public String getSignName() {
		return signName;
	}

	/**
	 * Sets the 短信签名.
	 *
	 * @param signName the new 短信签名
	 */
	public void setSignName(String signName) {
		this.signName = signName;
	}
}
