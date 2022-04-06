package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.common.core.domain.entity.SysMenu;

/**
 * The Interface SysMenuMapper.
 */
public interface SysMenuMapper {

	/**
	 * Select menu all.
	 *
	 * @return the list
	 */
	public List<SysMenu> selectMenuAll();

	/**
	 * Select menu all by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysMenu> selectMenuAllByUserId(Long userId);

	/**
	 * Select menu normal all.
	 *
	 * @return the list
	 */
	public List<SysMenu> selectMenuNormalAll();

	/**
	 * Select menus by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysMenu> selectMenusByUserId(Long userId);

	/**
	 * Select perms by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<String> selectPermsByUserId(Long userId);

	/**
	 * Select menu tree.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<String> selectMenuTree(Long roleId);

	/**
	 * Select menu list.
	 *
	 * @param menu the menu
	 * @return the list
	 */
	public List<SysMenu> selectMenuList(SysMenu menu);

	/**
	 * Select menu list by user id.
	 *
	 * @param menu the menu
	 * @return the list
	 */
	public List<SysMenu> selectMenuListByUserId(SysMenu menu);

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
	 * @param menuName the menu name
	 * @param parentId the parent id
	 * @return the sys menu
	 */
	public SysMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);
}
