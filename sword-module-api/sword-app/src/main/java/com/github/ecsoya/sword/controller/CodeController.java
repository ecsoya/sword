package com.github.ecsoya.sword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.service.IMailCodeService;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/open/code")
@Api(tags = { "邮箱验证码" }, description = "获取、验证")
public class CodeController extends BaseController {

	@Autowired
	private IMailCodeService mailService;

	@Autowired
	private IUserProfileService userService;

	@ApiOperation("发送邮箱验证码，无参")
	@PostMapping("/delivery")
	@RepeatSubmit
	public CommonResult<?> delivery(String subject, String template) {
		final Long userId = SwordUtils.getUserId();
		return mailService.sendCodeByUserId(userId, subject, template);
	}

	@ApiOperation("发送邮箱验证码，用户名")
	@PostMapping("/deliveryByUsername")
	@RepeatSubmit
	public CommonResult<?> deliveryByUserName(String username, String subject, String template) {
		Long userId = null;
		if (StringUtils.isNotEmpty(username)) {
			userId = userService.selectUserIdByUsername(username);
		}
		if (userId == null) {
			return CommonResult.fail("Unknown username");
		}
		return mailService.sendCodeByUserId(userId, subject, template);
	}

	@ApiOperation("发送邮箱验证码，邮箱")
	@PostMapping("/deliveryByEmail")
	@RepeatSubmit
	public CommonResult<?> deliveryByEmail(String email) {
		if (!StringUtils.isValidEmail(email)) {
			return CommonResult.fail("Invalid email address");
		}
		return mailService.sendCode(email);
	}

	/**
	 *
	 * 验证邮箱验证码
	 */
	@ApiOperation("验证邮箱验证码，无参")
	@GetMapping("/verify")
	@RepeatSubmit
	public CommonResult<?> verify(String code) {
		final Long userId = SwordUtils.getUserId();
		return mailService.verifyCodeByUserId(userId, code);
	}

	/**
	 *
	 * 验证邮箱验证码
	 */
	@ApiOperation("验证邮箱验证码，用户名")
	@GetMapping("/verifyByUsername")
	@RepeatSubmit
	public CommonResult<?> verifyByUsername(String username, String code) {
		Long userId = null;
		if (StringUtils.isNotEmpty(username)) {
			userId = userService.selectUserIdByUsername(username);
		}
		if (userId == null) {
			return CommonResult.fail("Unknown username");
		}
		return mailService.verifyCodeByUserId(userId, code);
	}

	/**
	 *
	 * 验证邮箱验证码
	 */
	@ApiOperation("验证邮箱验证码，邮箱")
	@GetMapping("/verifyByEmail")
	@RepeatSubmit
	public CommonResult<?> verifyByEmail(String email, String code) {
		return mailService.verifyCode(email, code);
	}

}