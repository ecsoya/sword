package com.github.ecsoya.sword.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysMenu;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

/**
 * The Interface ISysMenuService.
 */
public interface ISysMenuService {

	/**
	 * Select menus by user.
	 *
	 * @param user the user
	 * @return the list
	 */
	public List<SysMenu> selectMenusByUser(SysUser user);

	/**
	 * Select menu list.
	 *
	 * @param menu   the menu
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysMenu> selectMenuList(SysMenu menu, Long userId);

	/**
	 * Select menu all.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysMenu> selectMenuAll(Long userId);

	/**
	 * Select perms by user id.
	 *
	 * @param userId the user id
	 * @return the sets the
	 */
	public Set<String> selectPermsByUserId(Long userId);

	/**
	 * Role menu tree data.
	 *
	 * @param role   the role
	 * @param userId the user id
	 * @return the list
	 */
	public List<Ztree> roleMenuTreeData(SysRole role, Long userId);

	/**
	 * Menu tree data.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<Ztree> menuTreeData(Long userId);

	/**
	 * Select perms all.
	 *
	 * @param userId the user id
	 * @return the map
	 */
	public Map<String, String> selectPermsAll(Long userId);

	/**
	 * Delete menu by id.
	 *
	 * @param menuId the menu id
	 * @return the int
	 */
	public int deleteMenuById(Long menuId);

	/**
	 * Select menu by id.
	 *
	 * @param menuId the menu id
	 * @return the sys menu
	 */
	public SysMenu selectMenuById(Long menuId);

	/**
	 * Select count menu by parent id.
	 *
	 * @param parentId the parent id
	 * @return the int
	 */
	public int selectCountMenuByParentId(Long parentId);

	/**
	 * Select count role menu by menu id.
	 *
	 * @param menuId the menu id
	 * @return the int
	 */
	public int selectCountRoleMenuByMenuId(Long menuId);

	/**
	 * Insert menu.
	 *
	 * @param menu the menu
	 * @return the int
	 */
	public int insertMenu(SysMenu menu);

	/**
	 * Update menu.
	 *
	 * @param menu the menu
	 * @return the int
	 */
	public int updateMenu(SysMenu menu);

	/**
	 * Check menu name unique.
	 *
	 * @param menu the menu
	 * @return the string
	 */
	public String checkMenuNameUnique(SysMenu menu);

}
