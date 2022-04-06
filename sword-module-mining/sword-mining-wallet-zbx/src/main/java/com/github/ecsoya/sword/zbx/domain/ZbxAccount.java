package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxAccount.
 */
public class ZbxAccount {

	/** The token. */
	private String token;

	/** The user info. */
	private ZbxUserInfo userInfo;

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the user info.
	 *
	 * @return the user info
	 */
	public ZbxUserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * Sets the user info.
	 *
	 * @param userInfo the new user info
	 */
	public void setUserInfo(ZbxUserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
