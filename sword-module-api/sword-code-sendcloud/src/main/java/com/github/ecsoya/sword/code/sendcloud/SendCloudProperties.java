package com.github.ecsoya.sword.code.sendcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.code.config.CodeProperties;
import com.sendcloud.sdk.config.Region;
import com.sendcloud.sdk.config.SendcloudConfig;

/**
 * The Class SendCloudProperties.
 */
@ConfigurationProperties(prefix = "sword.code.sendcloud")
@Configuration
public class SendCloudProperties extends CodeProperties {

	/** The api user. */
	private String apiUser;

	/** The api key. */
	private String apiKey;

	/** The from. */
	private String from;

	/** The from name. */
	private String fromName;

	/** The region. */
	private Region region = Region.CN;

	/**
	 * Gets the api user.
	 *
	 * @return the api user
	 */
	public String getApiUser() {
		return apiUser;
	}

	/**
	 * Sets the api user.
	 *
	 * @param apiUser the new api user
	 */
	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
		SendcloudConfig.api_user_cn = apiUser;
	}

	/**
	 * Gets the api key.
	 *
	 * @return the api key
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * Sets the api key.
	 *
	 * @param apiKey the new api key
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
		SendcloudConfig.api_key_cn = apiKey;
	}

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
	public Region getRegion() {
		return region;
	}

	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
}
