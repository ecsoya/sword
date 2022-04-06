package com.github.ecsoya.sword.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class SysUserRole.
 */
public class SysUserRole {

	/** The user id. */
	private Long userId;

	/** The role id. */
	private Long roleId;

	/**
	 * Gets the 用户ID.
	 *
	 * @return the 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 用户ID.
	 *
	 * @param userId the new 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 角色ID.
	 *
	 * @return the 角色ID
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * Sets the 角色ID.
	 *
	 * @param roleId the new 角色ID
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("roleId", getRoleId()).toString();
	}
}
