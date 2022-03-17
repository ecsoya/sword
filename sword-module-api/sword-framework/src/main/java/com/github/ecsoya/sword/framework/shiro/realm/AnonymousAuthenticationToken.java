package com.github.ecsoya.sword.framework.shiro.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

public class AnonymousAuthenticationToken extends UsernamePasswordToken {

	/**
	 *
	 */
	private static final long serialVersionUID = -3358571335507074241L;

	private final SysUser user;

	private String[] userTypes;

	public AnonymousAuthenticationToken(SysUser user, boolean rememberMe, String... userTypes) {
		super(user.getLoginName(), user.getPassword(), rememberMe);
		this.setUserTypes(userTypes);
		this.user = user;
	}

	public SysUser getUser() {
		return user;
	}

	public String[] getUserTypes() {
		return userTypes;
	}

	public void setUserTypes(String[] userTypes) {
		this.userTypes = userTypes;
	}

}
