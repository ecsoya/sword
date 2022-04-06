package com.github.ecsoya.sword.controller.system;

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

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.service.SysPasswordService;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysPostService;
import com.github.ecsoya.sword.system.service.ISysRoleService;
import com.github.ecsoya.sword.system.service.ISysUserService;
import com.google.common.base.Objects;

/**
 * The Class SysUserController.
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/user";

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/** The role service. */
	@Autowired
	private ISysRoleService roleService;

	/** The post service. */
	@Autowired
	private ISysPostService postService;

	/** The password service. */
	@Autowired
	private SysPasswordService passwordService;

	/**
	 * User.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:user:view")
	@GetMapping()
	public String user() {
		return prefix + "/user";
	}

	/**
	 * List.
	 *
	 * @param user the user
	 * @return the table data info
	 */
	@RequiresPermissions("system:user:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysUser user) {
		if (user.getDeptId() == null) {
			user.setDeptId(101l);
		}
		if (Objects.equal(user.getDeptId(), 101l)) {
			user.setUserType(UserConstants.SYSTEM_USER_TYPE);
		}
		startPage();
		final List<SysUser> list = userService.selectUserList(user);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@Log(title = "用户管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:user:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysUser user) {
		if (user.getDeptId() == null) {
			user.setDeptId(101l);
		}
		if (Objects.equal(user.getDeptId(), 101l)) {
			user.setUserType(UserConstants.SYSTEM_USER_TYPE);
		}
		final List<SysUser> list = userService.selectUserList(user);
		final ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
		return util.exportExcel(list, "用户数据");
	}

	/**
	 * Import data.
	 *
	 * @param file          the file
	 * @param updateSupport the update support
	 * @return the ajax result
	 * @throws Exception the exception
	 */
	@Log(title = "用户管理", businessType = BusinessType.IMPORT)
	@RequiresPermissions("system:user:import")
	@PostMapping("/importData")
	@ResponseBody
	public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
//		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//		List<SysUser> userList = util.importExcel(file.getInputStream());
//		String operName = ShiroUtils.getSysUser().getLoginName();
//		String message = userService.importUser(userList, updateSupport, operName);
//		return AjaxResult.success(message);
		return AjaxResult.error("Unsupported");
	}

	/**
	 * Import template.
	 *
	 * @return the ajax result
	 */
	@RequiresPermissions("system:user:view")
	@GetMapping("/importTemplate")
	@ResponseBody
	public AjaxResult importTemplate() {
//		ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
//		return util.importTemplateExcel("用户数据");
		return AjaxResult.error("Unsupported");
	}

	/**
	 * Adds the.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap) {
		mmap.put("roles", roleService.selectRoleAll().stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		mmap.put("posts", postService.selectPostAll());
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@RequiresPermissions("system:user:add")
	@Log(title = "用户管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysUser user) {
		return AjaxResult.error("Unsupported");
//		if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
//			return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
//		} else if (StringUtils.isNotEmpty(user.getPhonenumber())
//				&& UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
//			return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
//		} else if (StringUtils.isNotEmpty(user.getEmail())
//				&& UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
//			return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
//		}
//		user.setSalt(ShiroUtils.randomSalt());
//		user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
//		user.setCreateBy(ShiroUtils.getLoginName());
//		return toAjax(userService.insertUser(user));
	}

	/**
	 * Edits the.
	 *
	 * @param userId the user id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{userId}")
	public String edit(@PathVariable("userId") Long userId, ModelMap mmap) {
		final List<SysRole> roles = roleService.selectRolesByUserId(userId);
		mmap.put("user", userService.selectUserById(userId));
		mmap.put("roles", SysUser.isAdmin(userId) ? roles
				: roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		mmap.put("posts", postService.selectPostsByUserId(userId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@RequiresPermissions("system:user:edit")
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysUser user) {
		userService.checkUserAllowed(user);
		if (StringUtils.isNotEmpty(user.getPhonenumber())
				&& UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
			return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
		} else if (StringUtils.isNotEmpty(user.getEmail())
				&& UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
			return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
		}
		user.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(userService.updateUser(user));
	}

	/**
	 * Reset pwd.
	 *
	 * @param userId the user id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@RequiresPermissions("system:user:resetPwd")
	@GetMapping("/resetPwd/{userId}")
	public String resetPwd(@PathVariable("userId") Long userId, ModelMap mmap) {
		mmap.put("user", userService.selectUserById(userId));
		return prefix + "/resetPwd";
	}

	/**
	 * Reset pwd save.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@RequiresPermissions("system:user:resetPwd")
	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwdSave(SysUser user) {
		userService.checkUserAllowed(user);
		user.setSalt(ShiroUtils.randomSalt());
		user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
		if (userService.resetUserPwd(user) > 0) {
			if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue()) {
				ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
			}
			return success();
		}
		return error();
	}

	/**
	 * Auth role.
	 *
	 * @param userId the user id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/authRole/{userId}")
	public String authRole(@PathVariable("userId") Long userId, ModelMap mmap) {
		final SysUser user = userService.selectUserById(userId);
		// 获取用户所属的角色列表
		final List<SysRole> roles = roleService.selectRolesByUserId(userId);
		mmap.put("user", user);
		mmap.put("roles", SysUser.isAdmin(userId) ? roles
				: roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
		return prefix + "/authRole";
	}

	/**
	 * Insert auth role.
	 *
	 * @param userId  the user id
	 * @param roleIds the role ids
	 * @return the ajax result
	 */
	@RequiresPermissions("system:user:add")
	@Log(title = "用户管理", businessType = BusinessType.GRANT)
	@PostMapping("/authRole/insertAuthRole")
	@ResponseBody
	public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
		userService.insertUserAuth(userId, roleIds);
		return success();
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("system:user:remove")
	@Log(title = "用户管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return AjaxResult.error("Unsupported");
//		return toAjax(userService.deleteUserByIds(ids));
	}

	/**
	 * Check login name unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	@PostMapping("/checkLoginNameUnique")
	@ResponseBody
	public String checkLoginNameUnique(SysUser user) {
		return userService.checkLoginNameUnique(user.getLoginName());
	}

	/**
	 * Check phone unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	@PostMapping("/checkPhoneUnique")
	@ResponseBody
	public String checkPhoneUnique(SysUser user) {
		return userService.checkPhoneUnique(user);
	}

	/**
	 * Check email unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	@PostMapping("/checkEmailUnique")
	@ResponseBody
	public String checkEmailUnique(SysUser user) {
		return userService.checkEmailUnique(user);
	}

	/**
	 * Change status.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@Log(title = "用户管理", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:user:edit")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysUser user) {
		userService.checkUserAllowed(user);
		return toAjax(userService.changeStatus(user));
	}
}