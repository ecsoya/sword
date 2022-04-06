package com.github.ecsoya.sword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserProfile;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class ProfileController.
 */
@RestController
@RequestMapping("/profile")
@Api(tags = { "用户资料" }, description = "查询、修改、更新登录密码")
public class ProfileController extends BaseController {

	/** The user service. */
	@Autowired
	private IUserProfileService userService;

	/**
	 * Profile.
	 *
	 * @return the common result
	 */
	@ApiOperation("查询用户资料")
	@GetMapping("/get")
	public CommonResult<UserProfile> profile() {
		final UserProfile profile = userService.selectUserProfileById(SwordUtils.getUserId());
		return CommonResult.build(profile);
	}

	/**
	 * Edits the.
	 *
	 * @param avatar   the avatar
	 * @param userName the user name
	 * @return the common result
	 */
	@ApiOperation("更新用户资料（昵称和头像）")
	@PostMapping("/edit")
	@RepeatSubmit
	public CommonResult<?> edit(String avatar, String userName) {
		final UserProfile profile = new UserProfile();
		profile.setUserId(SwordUtils.getUserId());
		profile.setAvatar(avatar);
		profile.setUserName(userName);
		return userService.updateUserProfile(profile);
	}

	/**
	 * Change pwd.
	 *
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @param code        the code
	 * @return the common result
	 */
	@ApiOperation("修改用户密码")
	@PostMapping("/changePwd")
	@RepeatSubmit
	public CommonResult<?> changePwd(String oldPassword, String newPassword, String code) {
		final Long userId = SwordUtils.getUserId();
		final CommonResult<?> verified = SwordUtils.verifyCode(code);
		if (!verified.isSuccess()) {
			return verified;
		}
		return userService.changeUserPassword(userId, oldPassword, newPassword);
	}

	/**
	 * Reset pwd.
	 *
	 * @param password the password
	 * @param code     the code
	 * @return the common result
	 */
	@ApiOperation("重置用户密码")
	@PostMapping("/resetPwd")
	@RepeatSubmit
	public CommonResult<?> resetPwd(String password, String code) {
		final Long userId = SwordUtils.getUserId();
		final CommonResult<?> verified = SwordUtils.verifyCode(code);
		if (!verified.isSuccess()) {
			return verified;
		}
		return userService.resetUserPassword(userId, password);
	}
}
