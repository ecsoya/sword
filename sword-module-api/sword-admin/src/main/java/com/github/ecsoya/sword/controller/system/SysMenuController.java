package com.github.ecsoya.sword.controller.system;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysMenu;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.framework.shiro.util.AuthorizationUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysMenuService;

/**
 * The Class SysMenuController.
 */
@Controller
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/menu";

	/** The menu service. */
	@Autowired
	private ISysMenuService menuService;

	/**
	 * Menu.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:menu:view")
	@GetMapping()
	public String menu() {
		return prefix + "/menu";
	}

	/**
	 * List.
	 *
	 * @param menu the menu
	 * @return the list
	 */
	@RequiresPermissions("system:menu:list")
	@PostMapping("/list")
	@ResponseBody
	public List<SysMenu> list(SysMenu menu) {
		final Long userId = ShiroUtils.getUserId();
		final List<SysMenu> menuList = menuService.selectMenuList(menu, userId);
		return menuList;
	}

	/**
	 * Removes the.
	 *
	 * @param menuId the menu id
	 * @return the ajax result
	 */
	@Log(title = "菜单管理", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:menu:remove")
	@GetMapping("/remove/{menuId}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("menuId") Long menuId) {
		if (menuService.selectCountMenuByParentId(menuId) > 0) {
			return AjaxResult.warn("存在子菜单,不允许删除");
		}
		if (menuService.selectCountRoleMenuByMenuId(menuId) > 0) {
			return AjaxResult.warn("菜单已分配,不允许删除");
		}
		AuthorizationUtils.clearAllCachedAuthorizationInfo();
		return toAjax(menuService.deleteMenuById(menuId));
	}

	/**
	 * Adds the.
	 *
	 * @param parentId the parent id
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/add/{parentId}")
	public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
		SysMenu menu = null;
		if (0L != parentId) {
			menu = menuService.selectMenuById(parentId);
		} else {
			menu = new SysMenu();
			menu.setMenuId(0L);
			menu.setMenuName("主目录");
		}
		mmap.put("menu", menu);
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param menu the menu
	 * @return the ajax result
	 */
	@Log(title = "菜单管理", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:menu:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysMenu menu) {
		if (UserConstants.MENU_NAME_NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		}
		menu.setCreateBy(ShiroUtils.getLoginName());
		AuthorizationUtils.clearAllCachedAuthorizationInfo();
		return toAjax(menuService.insertMenu(menu));
	}

	/**
	 * Edits the.
	 *
	 * @param menuId the menu id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{menuId}")
	public String edit(@PathVariable("menuId") Long menuId, ModelMap mmap) {
		mmap.put("menu", menuService.selectMenuById(menuId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param menu the menu
	 * @return the ajax result
	 */
	@Log(title = "菜单管理", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:menu:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysMenu menu) {
		if (UserConstants.MENU_NAME_NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
			return error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
		}
		menu.setUpdateBy(ShiroUtils.getLoginName());
		AuthorizationUtils.clearAllCachedAuthorizationInfo();
		return toAjax(menuService.updateMenu(menu));
	}

	/**
	 * Icon.
	 *
	 * @return the string
	 */
	@GetMapping("/icon")
	public String icon() {
		return prefix + "/icon";
	}

	/**
	 * Check menu name unique.
	 *
	 * @param menu the menu
	 * @return the string
	 */
	@PostMapping("/checkMenuNameUnique")
	@ResponseBody
	public String checkMenuNameUnique(SysMenu menu) {
		return menuService.checkMenuNameUnique(menu);
	}

	/**
	 * Role menu tree data.
	 *
	 * @param role the role
	 * @return the list
	 */
	@GetMapping("/roleMenuTreeData")
	@ResponseBody
	public List<Ztree> roleMenuTreeData(SysRole role) {
		final Long userId = ShiroUtils.getUserId();
		final List<Ztree> ztrees = menuService.roleMenuTreeData(role, userId);
		return ztrees;
	}

	/**
	 * Menu tree data.
	 *
	 * @return the list
	 */
	@GetMapping("/menuTreeData")
	@ResponseBody
	public List<Ztree> menuTreeData() {
		final Long userId = ShiroUtils.getUserId();
		final List<Ztree> ztrees = menuService.menuTreeData(userId);
		return ztrees;
	}

	/**
	 * Select menu tree.
	 *
	 * @param menuId the menu id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/selectMenuTree/{menuId}")
	public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap mmap) {
		mmap.put("menu", menuService.selectMenuById(menuId));
		return prefix + "/tree";
	}

}