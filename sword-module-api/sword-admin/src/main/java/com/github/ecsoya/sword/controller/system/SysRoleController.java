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
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.util.AuthorizationUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.domain.SysUserRole;
import com.github.ecsoya.sword.system.service.ISysRoleService;
import com.github.ecsoya.sword.system.service.ISysUserService;

/**
 * The Class SysRoleController.
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/role";

	/** The role service. */
	@Autowired
	private ISysRoleService roleService;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/**
	 * Role.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:role:view")
	@GetMapping()
	public String role() {
		return prefix + "/role";
	}

	/**
	 * List.
	 *
	 * @param role the role
	 * @return the table data info
	 */
	@RequiresPermissions("system:role:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysRole role) {
		startPage();
		final List<SysRole> list = roleService.selectRoleList(role);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param role the role
	 * @return the ajax result
	 */
	@Log(title = "????????????", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:role:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysRole role) {
		final List<SysRole> list = roleService.selectRoleList(role);
		final ExcelUtil<SysRole> util = new ExcelUtil<SysRole>(SysRole.class);
		return util.exportExcel(list, "????????????");
	}

	/**
	 * Adds the.
	 *
	 * @return the string
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param role the role
	 * @return the ajax result
	 */
	@RequiresPermissions("system:role:add")
	@Log(title = "????????????", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysRole role) {
		if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return error("????????????'" + role.getRoleName() + "'??????????????????????????????");
		} else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return error("????????????'" + role.getRoleName() + "'??????????????????????????????");
		}
		role.setCreateBy(ShiroUtils.getLoginName());
		AuthorizationUtils.clearAllCachedAuthorizationInfo();
		return toAjax(roleService.insertRole(role));

	}

	/**
	 * Edits the.
	 *
	 * @param roleId the role id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{roleId}")
	public String edit(@PathVariable("roleId") Long roleId, ModelMap mmap) {
		mmap.put("role", roleService.selectRoleById(roleId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param role the role
	 * @return the ajax result
	 */
	@RequiresPermissions("system:role:edit")
	@Log(title = "????????????", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysRole role) {
		roleService.checkRoleAllowed(role);
		if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
			return error("????????????'" + role.getRoleName() + "'??????????????????????????????");
		} else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
			return error("????????????'" + role.getRoleName() + "'??????????????????????????????");
		}
		role.setUpdateBy(ShiroUtils.getLoginName());
		AuthorizationUtils.clearAllCachedAuthorizationInfo();
		return toAjax(roleService.updateRole(role));
	}

	/**
	 * Auth data scope.
	 *
	 * @param roleId the role id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/authDataScope/{roleId}")
	public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap mmap) {
		mmap.put("role", roleService.selectRoleById(roleId));
		return prefix + "/dataScope";
	}

	/**
	 * Auth data scope save.
	 *
	 * @param role the role
	 * @return the ajax result
	 */
	@RequiresPermissions("system:role:edit")
	@Log(title = "????????????", businessType = BusinessType.UPDATE)
	@PostMapping("/authDataScope")
	@ResponseBody
	public AjaxResult authDataScopeSave(SysRole role) {
		roleService.checkRoleAllowed(role);
		role.setUpdateBy(ShiroUtils.getLoginName());
		if (roleService.authDataScope(role) > 0) {
			ShiroUtils.setSysUser(userService.selectUserById(ShiroUtils.getSysUser().getUserId()));
			return success();
		}
		return error();
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("system:role:remove")
	@Log(title = "????????????", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(roleService.deleteRoleByIds(ids));
	}

	/**
	 * Check role name unique.
	 *
	 * @param role the role
	 * @return the string
	 */
	@PostMapping("/checkRoleNameUnique")
	@ResponseBody
	public String checkRoleNameUnique(SysRole role) {
		return roleService.checkRoleNameUnique(role);
	}

	/**
	 * Check role key unique.
	 *
	 * @param role the role
	 * @return the string
	 */
	@PostMapping("/checkRoleKeyUnique")
	@ResponseBody
	public String checkRoleKeyUnique(SysRole role) {
		return roleService.checkRoleKeyUnique(role);
	}

	/**
	 * Select menu tree.
	 *
	 * @return the string
	 */
	@GetMapping("/selectMenuTree")
	public String selectMenuTree() {
		return prefix + "/tree";
	}

	/**
	 * Change status.
	 *
	 * @param role the role
	 * @return the ajax result
	 */
	@Log(title = "????????????", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:role:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysRole role) {
		roleService.checkRoleAllowed(role);
		return toAjax(roleService.changeStatus(role));
	}

	/**
	 * Auth user.
	 *
	 * @param roleId the role id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@RequiresPermissions("system:role:edit")
	@GetMapping("/authUser/{roleId}")
	public String authUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
		mmap.put("role", roleService.selectRoleById(roleId));
		return prefix + "/authUser";
	}

	/**
	 * Allocated list.
	 *
	 * @param user the user
	 * @return the table data info
	 */
	@RequiresPermissions("system:role:list")
	@PostMapping("/authUser/allocatedList")
	@ResponseBody
	public TableDataInfo allocatedList(SysUser user) {
		startPage();
		final List<SysUser> list = userService.selectAllocatedList(user);
		return getDataTable(list);
	}

	/**
	 * Cancel auth user.
	 *
	 * @param userRole the user role
	 * @return the ajax result
	 */
	@Log(title = "????????????", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/cancel")
	@ResponseBody
	public AjaxResult cancelAuthUser(SysUserRole userRole) {
		return toAjax(roleService.deleteAuthUser(userRole));
	}

	/**
	 * Cancel auth user all.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the ajax result
	 */
	@Log(title = "????????????", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/cancelAll")
	@ResponseBody
	public AjaxResult cancelAuthUserAll(Long roleId, String userIds) {
		return toAjax(roleService.deleteAuthUsers(roleId, userIds));
	}

	/**
	 * Select user.
	 *
	 * @param roleId the role id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/authUser/selectUser/{roleId}")
	public String selectUser(@PathVariable("roleId") Long roleId, ModelMap mmap) {
		mmap.put("role", roleService.selectRoleById(roleId));
		return prefix + "/selectUser";
	}

	/**
	 * Unallocated list.
	 *
	 * @param user the user
	 * @return the table data info
	 */
	@RequiresPermissions("system:role:list")
	@PostMapping("/authUser/unallocatedList")
	@ResponseBody
	public TableDataInfo unallocatedList(SysUser user) {
		startPage();
		final List<SysUser> list = userService.selectUnallocatedList(user);
		return getDataTable(list);
	}

	/**
	 * Select auth user all.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the ajax result
	 */
	@Log(title = "????????????", businessType = BusinessType.GRANT)
	@PostMapping("/authUser/selectAll")
	@ResponseBody
	public AjaxResult selectAuthUserAll(Long roleId, String userIds) {
		return toAjax(roleService.insertAuthUsers(roleId, userIds));
	}
}