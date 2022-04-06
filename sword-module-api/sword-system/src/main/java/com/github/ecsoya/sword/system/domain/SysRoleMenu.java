package com.github.ecsoya.sword.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class SysRoleMenu.
 */
public class SysRoleMenu {

	/** The role id. */
	private Long roleId;

	/** The menu id. */
	private Long menuId;

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
	 * Gets the 菜单ID.
	 *
	 * @return the 菜单ID
	 */
	public Long getMenuId() {
		return menuId;
	}

	/**
	 * Sets the 菜单ID.
	 *
	 * @param menuId the new 菜单ID
	 */
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("roleId", getRoleId())
				.append("menuId", getMenuId()).toString();
	}
}
