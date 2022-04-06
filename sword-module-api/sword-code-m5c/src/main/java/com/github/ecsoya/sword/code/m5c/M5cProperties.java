package com.github.ecsoya.sword.code.m5c;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.code.config.CodeProperties;

/**
 * The Class M5cProperties.
 */
@ConfigurationProperties(prefix = "sword.code.m5c")
@Configuration
public class M5cProperties extends CodeProperties {

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The api key. */
	private String apiKey;

	/** The connection timeout. */
	private long connectionTimeout = 30000;

	/** The read timeout. */
	private long readTimeout = 30000;

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
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
	}

	/**
	 * Gets the connection timeout.
	 *
	 * @return the connection timeout
	 */
	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	/**
	 * Sets the connection timeout.
	 *
	 * @param connectionTimeout the new connection timeout
	 */
	public void setConnectionTimeout(long connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	/**
	 * Gets the read timeout.
	 *
	 * @return the read timeout
	 */
	public long getReadTimeout() {
		return readTimeout;
	}

	/**
	 * Sets the read timeout.
	 *
	 * @param readTimeout the new read timeout
	 */
	public void setReadTimeout(long readTimeout) {
		this.readTimeout = readTimeout;
	}
}
