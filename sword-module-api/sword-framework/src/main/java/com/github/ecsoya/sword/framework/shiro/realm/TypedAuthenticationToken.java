package com.github.ecsoya.sword.framework.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

public class TypedAuthenticationToken extends UsernamePasswordToken {

	/**
	 *
	 */
	private static final long serialVersionUID = -3358571335507074241L;

	private String[] userTypes;

	public TypedAuthenticationToken(String username, String password, boolean rememberMe, String... userTypes) {
		super(username, password, rememberMe);
		this.setUserTypes(userTypes);
	}

	public String[] getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(String[] userTypes) {
		this.userTypes = userTypes;
	}

}
