package com.soyatec.sword.controller.admin;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.multipart.MultipartFile;

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.entity.SysRole;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.poi.ExcelUtil;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.system.service.ISysRoleService;
import com.soyatec.sword.system.service.ISysUserService;

/**
 * 用户信息
 *
 * @author King Crab
 */
@Controller
@RequestMapping("/admin/manager")
public class AdminManagerController extends BaseController {
	private final String prefix = "admin/manager";

	private static final Long MANAGER_DEPT_ID = 101l;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private ISysRoleService roleService;

	@RequiresPermissions("admin:manager:view")
	@GetMapping()
	public String manager() {
		return prefix + "/manager";
	}

	@RequiresPermissions("admin:manager:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysUser manager) {
		manager.setUserType(UserConstants.SYSTEM_USER_TYPE);
		manager.setDeptId(MANAGER_DEPT_ID);
		startPage();
		final List<SysUser> list = userService.selectUserList(manager).stream()
				.filter(a -> !org.apache.commons.lang3.StringUtils.equals(a.getLoginName(), "admin"))
				.collect(Collectors.toList());
		return getDataTable(list);
	}

	@Log(title = "导出管理员", businessType = BusinessType.EXPORT)
	@RequiresPermissions("admin:manager:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysUser manager) {
		manager.setUserType(UserConstants.SYSTEM_USER_TYPE);
		manager.setDeptId(MANAGER_DEPT_ID);
		final List<SysUser> list = userService.selectUserList(manager);
		final ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.exportExcel(list, "用户数据");
	}

	@Log(title = "导入管理员", businessType = BusinessType.IMPORT)
	@RequiresPermissions("admin:manager:import")
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
		final ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		final List<SysUser> userList = util.importExcel(file.getInputStream());
		final String operName = ShiroUtils.getSysUser().getLoginName();
		final String message = userService.importUser(userList, updateSupport, operName);
		return AjaxResult.success(message);
	}

	@RequiresPermissions("admin:manager:view")
	@GetMapping("/importTemplate")
	@ResponseBody
	public AjaxResult importTemplate() {
		final ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.importTemplateExcel("用户数据");
	}

	/**
	 * 新增用户
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap) {
		mmap.put("roles", roleService.selectRoleAll().stream().filter(r -> "5".equals(r.getDataScope()))
				.collect(Collectors.toList()));
		return prefix + "/add";
	}

	/**
	 * 新增保存用户
	 */
	@RequiresPermissions("admin:manager:add")
	@Log(title = "添加管理员", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysUser manager) {
		manager.setUserType(UserConstants.SYSTEM_USER_TYPE);
		if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(manager.getLoginName()))) {
			return error("新增用户'" + manager.getLoginName() + "'失败，登录账号已存在");
		} else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(manager))) {
			return error("新增用户'" + manager.getLoginName() + "'失败，手机号码已存在");
		} else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(manager))) {
			return error("新增用户'" + manager.getLoginName() + "'失败，邮箱账号已存在");
		}
		manager.setDeptId(MANAGER_DEPT_ID);
		manager.setSalt(ShiroUtils.randomSalt());
		manager.setPassword(
				StringUtils.encryptPassword(manager.getLoginName(), manager.getPassword(), manager.getSalt()));
		manager.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(userService.insertUser(manager));
	}

	/**
	 * 修改用户
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
		mmap.put("user", userService.selectUserById(userId));
		mmap.put("roles", roleService.selectRoleAll().stream().filter(r -> "5".equals(r.getDataScope()))
				.collect(Collectors.toList()));
		return prefix + "/edit";
	}

	/**
	 * 修改保存用户
	 */
	@RequiresPermissions("admin:manager:edit")
	@Log(title = "更新管理员", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysUser manager) {
		userService.checkUserAllowed(manager);
		manager.setUserType(UserConstants.SYSTEM_USER_TYPE);
		if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(manager))) {
			return error("修改用户'" + manager.getLoginName() + "'失败，手机号码已存在");
		} else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(manager))) {
			return error("修改用户'" + manager.getLoginName() + "'失败，邮箱账号已存在");
		}
		manager.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(userService.updateUser(manager));
	}

	@RequiresPermissions("admin:manager:resetPwd")
	@GetMapping("/resetPwd/{userId}")
	public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
		mmap.put("user", userService.selectUserById(userId));
		return prefix + "/resetPwd";
	}

	@RequiresPermissions("admin:manager:resetPwd")
	@Log(title = "重置管理员密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwdSave(SysUser manager) {
		userService.checkUserAllowed(manager);
		manager.setSalt(ShiroUtils.randomSalt());
		manager.setPassword(
				StringUtils.encryptPassword(manager.getLoginName(), manager.getPassword(), manager.getSalt()));
		if (userService.resetUserPwd(manager) > 0) {
			if (ShiroUtils.getUserId().longValue() == manager.getUserId().longValue()) {
				ShiroUtils.setSysUser(userService.selectUserById(manager.getUserId()));
			}
			return success();
		}
		return error();
	}

	/**
	 * 进入授权角色页
	 */
	@GetMapping("/authRole/{userId}")
	public String authRole(@PathVariable("userId") Long userId, ModelMap mmap) {
		final SysUser manager = userService.selectUserById(userId);
		// 获取用户所属的角色列表
		final List<SysRole> roles = roleService.selectRolesByUserId(userId);
		mmap.put("manager", manager);
		mmap.put("roles", SysUser.isAdmin(userId) ? roles
				: roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		return prefix + "/authRole";
	}

	/**
	 * 用户授权角色
	 */
	@RequiresPermissions("admin:manager:add")
	@Log(title = "用户管理", businessType = BusinessType.GRANT)
	@PostMapping("/authRole/insertAuthRole")
	@ResponseBody
	public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
		userService.insertUserAuth(userId, roleIds);
		return success();
	}

	@RequiresPermissions("admin:manager:remove")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		try {
			return toAjax(userService.deleteUserByIds(ids));
		} catch (final Exception e) {
			return error(e.getMessage());
		}
	}

	/**
	 * 校验用户名
	 */
	@PostMapping("/checkLoginNameUnique")
	@ResponseBody
	public String checkLoginNameUnique(SysUser manager) {
		return userService.checkLoginNameUnique(manager.getLoginName());
	}

	/**
	 * 校验手机号码
	 */
	@PostMapping("/checkPhoneUnique")
	@ResponseBody
	public String checkPhoneUnique(SysUser manager) {
		return userService.checkPhoneUnique(manager);
	}

	/**
	 * 校验email邮箱
	 */
	@PostMapping("/checkEmailUnique")
	@ResponseBody
	public String checkEmailUnique(SysUser manager) {
		return userService.checkEmailUnique(manager);
	}

	/**
	 * 用户状态修改
	 */
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@RequiresPermissions("admin:manager:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysUser manager) {
		userService.checkUserAllowed(manager);
		return toAjax(userService.changeStatus(manager));
	}
}