package com.github.ecsoya.sword.framework.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * The Class TypedAuthenticationToken.
 */
public class TypedAuthenticationToken extends UsernamePasswordToken {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3358571335507074241L;

	/** The user types. */
	private String[] userTypes;

	/**
	 * Instantiates a new typed authentication token.
	 *
	 * @param username   the username
	 * @param password   the password
	 * @param rememberMe the remember me
	 * @param userTypes  the user types.
	 */
	public TypedAuthenticationToken(String username, String password, boolean rememberMe, String... userTypes) {
		super(username, password, rememberMe);
		this.setUserTypes(userTypes);
	}

	/**
	 * Gets the user types.
	 *
	 * @return the user types
	 */
	public String[] getUserTypes() {
		return userTypes;
	}

	/**
	 * Sets the user types.
	 *
	 * @param userTypes the new user types
	 */
	public void setUserTypes(String[] userTypes) {
		this.userTypes = userTypes;
	}

}
