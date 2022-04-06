package com.github.ecsoya.sword.framework.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

/**
 * The Class AnonymousAuthenticationToken.
 */
public class AnonymousAuthenticationToken extends UsernamePasswordToken {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -3358571335507074241L;

	/** The user. */
	private final SysUser user;

	/** The user types. */
	private String[] userTypes;

	/**
	 * Instantiates a new anonymous authentication token.
	 *
	 * @param user       the user.
	 * @param rememberMe the remember me
	 * @param userTypes  the user types.
	 */
	public AnonymousAuthenticationToken(SysUser user, boolean rememberMe, String... userTypes) {
		super(user.getLoginName(), user.getPassword(), rememberMe);
		this.setUserTypes(userTypes);
		this.user = user;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public SysUser getUser() {
		return user;
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
