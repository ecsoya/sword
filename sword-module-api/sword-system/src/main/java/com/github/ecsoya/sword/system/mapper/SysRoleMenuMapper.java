package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysRoleMenu;

/**
 * The Interface SysRoleMenuMapper.
 */
public interface SysRoleMenuMapper {

	/**
	 * Delete role menu by role id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	public int deleteRoleMenuByRoleId(Long roleId);

	/**
	 * Delete role menu.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteRoleMenu(Long[] ids);

	/**
	 * Select count role menu by menu id.
	 *
	 * @param menuId the menu id
	 * @return the int
	 */
	public int selectCountRoleMenuByMenuId(Long menuId);

	/**
	 * Batch role menu.
	 *
	 * @param roleMenuList the role menu list
	 * @return the int
	 */
	public int batchRoleMenu(List<SysRoleMenu> roleMenuList);
}
