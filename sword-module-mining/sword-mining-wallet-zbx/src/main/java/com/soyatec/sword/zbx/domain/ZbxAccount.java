package com.soyatec.sword.zbx.domain;

public class ZbxAccount {

	private String token;

	private ZbxUserInfo userInfo;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public ZbxUserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(ZbxUserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
