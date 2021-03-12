package com.soyatec.sword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMobileService;
import com.soyatec.sword.user.service.IUserProfileService;
import com.soyatec.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/open/mobile")
@Api(tags = { "手机验证码" }, description = "获取、验证")
public class MobileController extends BaseController {

	@Autowired
	private IMobileService mobileService;

	@Autowired
	private IUserProfileService userService;

	@ApiOperation("发送手机验证码，无参")
	@PostMapping("/delivery")
	public CommonResult<?> delivery() {
		Long userId = SwordUtils.getUserId();
		return mobileService.sendCodeByUserId(userId);
	}

	@ApiOperation("发送手机验证码，用户名")
	@PostMapping("/deliveryByUsername")
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
	public CommonResult<?> deliveryByEmail(String email) {
		if (!StringUtils.isValidEmail(email)) {
			return CommonResult.fail("Invalid email address");
		}
		return mobileService.sendCode(email);
	}

	/**
	 * 
	 * 验证手机验证码
	 */
	@ApiOperation("验证手机验证码，无参")
	@GetMapping("/verify")
	public CommonResult<?> verify(String code) {
		Long userId = SwordUtils.getUserId();
		return mobileService.verifyCodeByUserId(userId, code);
	}

	/**
	 * 
	 * 验证手机验证码
	 */
	@ApiOperation("验证手机验证码，用户名")
	@GetMapping("/verifyByUsername")
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
	public CommonResult<?> verifyByEmail(String mobile, String code) {
		return mobileService.verifyCode(mobile, code);
	}

}
