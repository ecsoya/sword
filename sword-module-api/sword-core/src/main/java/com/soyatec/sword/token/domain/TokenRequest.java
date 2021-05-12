package com.soyatec.sword.token.domain;

import java.io.Serializable;

public class TokenRequest implements Serializable {

	private static final long serialVersionUID = 3358356083031234008L;

	private String accessKey;

	private String data;

	private String sign;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return String.format("TokenRequest [accessKey=%s, data=%s, sign=%s]", accessKey, data, sign);
	}

}
