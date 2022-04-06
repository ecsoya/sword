package com.github.ecsoya.sword.code.javamail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.code.config.CodeProperties;

/**
 * The Class JavamailCodeProperties.
 */
@ConfigurationProperties(prefix = "sword.code.javamail")
@Configuration
public class JavamailCodeProperties extends CodeProperties {

	/** The host. */
	private String host;

	/** The from. */
	private String from;

	/** The from name. */
	private String fromName;

	/** The password. */
	private String password;

	/** The ssl. */
	private boolean ssl = true;

	/** The charset. */
	private String charset = "utf-8";

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host the new host
	 */
	public void setHost(String host) {
		this.host = host;
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
	 * Checks if is ssl.
	 *
	 * @return true, if is ssl
	 */
	public boolean isSsl() {
		return ssl;
	}

	/**
	 * Sets the ssl.
	 *
	 * @param ssl the new ssl
	 */
	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	/**
	 * Gets the charset.
	 *
	 * @return the charset
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Sets the charset.
	 *
	 * @param charset the new charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}

}
