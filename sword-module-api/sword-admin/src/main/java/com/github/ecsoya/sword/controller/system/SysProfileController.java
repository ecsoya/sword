package com.github.ecsoya.sword.controller.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.service.SysPasswordService;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysUserService;
import com.github.ecsoya.sword.upload.utils.FileUploadUtils;

/**
 * The Class SysProfileController.
 */
@Controller
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SysProfileController.class);

	/** The prefix. */
	private final String prefix = "system/user/profile";

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/** The password service. */
	@Autowired
	private SysPasswordService passwordService;

	/**
	 * Profile.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping()
	public String profile(ModelMap mmap) {
		final SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", user);
		mmap.put("roleGroup", userService.selectUserRoleGroup(user.getUserId()));
		mmap.put("postGroup", userService.selectUserPostGroup(user.getUserId()));
		return prefix + "/profile";
	}

	/**
	 * Check password.
	 *
	 * @param password the password
	 * @return true, if successful
	 */
	@GetMapping("/checkPassword")
	@ResponseBody
	public boolean checkPassword(String password) {
		final SysUser user = ShiroUtils.getSysUser();
		if (passwordService.matches(user, password)) {
			return true;
		}
		return false;
	}

	/**
	 * Reset pwd.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/resetPwd")
	public String resetPwd(ModelMap mmap) {
		final SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/resetPwd";
	}

	/**
	 * Reset pwd.
	 *
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the ajax result
	 */
	@Log(title = "重置密码", businessType = BusinessType.UPDATE)
	@PostMapping("/resetPwd")
	@ResponseBody
	public AjaxResult resetPwd(String oldPassword, String newPassword) {
		final SysUser user = ShiroUtils.getSysUser();
		if (!passwordService.matches(user, oldPassword)) {
			return error("修改密码失败，旧密码错误");
		}
		if (passwordService.matches(user, newPassword)) {
			return error("新密码不能与旧密码相同");
		}
		user.setSalt(ShiroUtils.randomSalt());
		user.setPassword(StringUtils.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
		user.setPwdUpdateDate(DateUtils.getNowDate());
		if (userService.resetUserPwd(user) > 0) {
			ShiroUtils.setSysUser(userService.selectUserById(user.getUserId()));
			return success();
		}
		return error("修改密码异常，请联系管理员");
	}

	/**
	 * Edits the.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/edit")
	public String edit(ModelMap mmap) {
		final SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/edit";
	}

	/**
	 * Avatar.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/avatar")
	public String avatar(ModelMap mmap) {
		final SysUser user = ShiroUtils.getSysUser();
		mmap.put("user", userService.selectUserById(user.getUserId()));
		return prefix + "/avatar";
	}

	/**
	 * Update.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/update")
	@ResponseBody
	public AjaxResult update(SysUser user) {
		final SysUser currentUser = ShiroUtils.getSysUser();
		currentUser.setUserName(user.getUserName());
		currentUser.setEmail(user.getEmail());
		currentUser.setPhonenumber(user.getPhonenumber());
		currentUser.setSex(user.getSex());
		if (userService.updateUserInfo(currentUser) > 0) {
			ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
			return success();
		}
		return error();
	}

	/**
	 * Update avatar.
	 *
	 * @param file the file
	 * @return the ajax result
	 */
	@Log(title = "个人信息", businessType = BusinessType.UPDATE)
	@PostMapping("/updateAvatar")
	@ResponseBody
	public AjaxResult updateAvatar(@RequestParam("avatarfile") MultipartFile file) {
		final SysUser currentUser = ShiroUtils.getSysUser();
		try {
			if (!file.isEmpty()) {
				final String avatar = FileUploadUtils.upload(file);
				currentUser.setAvatar(avatar);
				if (userService.updateUserInfo(currentUser) > 0) {
					ShiroUtils.setSysUser(userService.selectUserById(currentUser.getUserId()));
					return success();
				}
			}
			return error();
		} catch (final Exception e) {
			log.error("修改头像失败！", e);
			return error(e.getMessage());
		}
	}
}
