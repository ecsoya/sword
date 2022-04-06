package com.github.ecsoya.sword.token.domain;

import java.io.Serializable;

/**
 * The Class TokenRequest.
 */
public class TokenRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3358356083031234008L;

	/** The access key. */
	private String accessKey;

	/** The data. */
	private String data;

	/** The sign. */
	private String sign;

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Sets the access key.
	 *
	 * @param accessKey the new access key
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * Gets the sign.
	 *
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	/**
	 * Sets the sign.
	 *
	 * @param sign the new sign
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return String.format("TokenRequest [accessKey=%s, data=%s, sign=%s]", accessKey, data, sign);
	}

}
