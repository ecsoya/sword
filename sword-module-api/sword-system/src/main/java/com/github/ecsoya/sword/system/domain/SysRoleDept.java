package com.github.ecsoya.sword.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class SysRoleDept.
 */
public class SysRoleDept {

	/** The role id. */
	private Long roleId;

	/** The dept id. */
	private Long deptId;

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
	 * Gets the 部门ID.
	 *
	 * @return the 部门ID
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * Sets the 部门ID.
	 *
	 * @param deptId the new 部门ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("roleId", getRoleId())
				.append("deptId", getDeptId()).toString();
	}
}
