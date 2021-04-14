package com.soyatec.sword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMobileCodeService;
import com.soyatec.sword.user.service.IUserProfileService;
import com.soyatec.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/open/mobile")
@Api(tags = { "手机验证码" }, description = "获取、验证")
public class MobileController extends BaseController {

	@Autowired
	private IMobileCodeService mobileService;

	@Autowired
	private IUserProfileService userService;

	@ApiOperation("发送手机验证码，无参")
	@PostMapping("/delivery")
	@RepeatSubmit
	public CommonResult<?> delivery() {
		final Long userId = SwordUtils.getUserId();
		return mobileService.sendCodeByUserId(userId);
	}

	@ApiOperation("发送手机验证码，用户名")
	@PostMapping("/deliveryByUsername")
	@RepeatSubmit
	public CommonResult<?> deliveryByUserName(String username) {
		Long userId = null;
		if (StringUtils.isNotEmpty(username)) {
			userId = userService.selectUserIdByUsername(username);
		}
		if (userId == null) {
			return CommonResult.fail("Unknown username");
		}
		return mobileService.sendCodeByUserId(userId);
	}

	@ApiOperation("发送手机验证码，手机")
	@PostMapping("/deliveryByMobile")
	@RepeatSubmit
	public CommonResult<?> deliveryBymobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("Invalid mobile address");
		}
		return mobileService.sendCode(mobile);
	}

	/**
	 *
	 * 验证手机验证码
	 */
	@ApiOperation("验证手机验证码，无参")
	@GetMapping("/verify")
	@RepeatSubmit
	public CommonResult<?> verify(String code) {
		final Long userId = SwordUtils.getUserId();
		return mobileService.verifyCodeByUserId(userId, code);
	}

	/**
	 *
	 * 验证手机验证码
	 */
	@ApiOperation("验证手机验证码，用户名")
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
		return mobileService.verifyCodeByUserId(userId, code);
	}

	/**
	 *
	 * 验证手机验证码
	 */
	@ApiOperation("验证手机验证码，手机")
	@GetMapping("/verifyByMobile")
	@RepeatSubmit
	public CommonResult<?> verifyByMobile(String mobile, String code) {
		return mobileService.verifyCode(mobile, code);
	}

}
