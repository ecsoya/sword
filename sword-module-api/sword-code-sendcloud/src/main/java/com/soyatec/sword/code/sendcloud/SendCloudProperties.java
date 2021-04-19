package com.soyatec.sword.code.sendcloud;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.sendcloud.sdk.config.Region;
import com.sendcloud.sdk.config.SendcloudConfig;
import com.soyatec.sword.code.config.CodeProperties;

@ConfigurationProperties(prefix = "sword.code.sendcloud")
@Configuration
public class SendCloudProperties extends CodeProperties {
	private String apiUser;

	private String apiKey;

	private String from;

	private String fromName;

	private Region region = Region.CN;

	public String getApiUser() {
		return apiUser;
	}

	public void setApiUser(String apiUser) {
		this.apiUser = apiUser;
		SendcloudConfig.api_user_cn = apiUser;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
		SendcloudConfig.api_key_cn = apiKey;
	}

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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}
}
