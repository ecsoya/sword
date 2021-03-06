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
import com.github.ecsoya.sword.service.IMobileCodeService;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * The Class MobileController.
 */
@RestController
@RequestMapping("/open/mobile")
@Api(tags = { "手机验证码" }, description = "获取、验证")
public class MobileController extends BaseController {

	/** The mobile service. */
	@Autowired
	private IMobileCodeService mobileService;

	/** The user service. */
	@Autowired
	private IUserProfileService userService;

	/**
	 * Delivery.
	 *
	 * @return the common result
	 */
	@ApiOperation("发送手机验证码，无参")
	@PostMapping("/delivery")
	@RepeatSubmit
	public CommonResult<?> delivery() {
		final Long userId = SwordUtils.getUserId();
		return mobileService.sendCodeByUserId(userId);
	}

	/**
	 * Delivery by user name.
	 *
	 * @param username the username
	 * @return the common result
	 */
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

	/**
	 * Delivery bymobile.
	 *
	 * @param mobile the mobile
	 * @return the common result
	 */
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
	 * Verify.
	 *
	 * @param code the code
	 * @return the common result
	 */
	@ApiOperation("验证手机验证码，无参")
	@GetMapping("/verify")
	@RepeatSubmit
	public CommonResult<?> verify(String code) {
		final Long userId = SwordUtils.getUserId();
		return mobileService.verifyCodeByUserId(userId, code);
	}

	/**
	 * Verify by username.
	 *
	 * @param username the username
	 * @param code     the code
	 * @return the common result
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
	 * Verify by mobile.
	 *
	 * @param mobile the mobile
	 * @param code   the code
	 * @return the common result
	 */
	@ApiOperation("验证手机验证码，手机")
	@GetMapping("/verifyByMobile")
	@RepeatSubmit
	public CommonResult<?> verifyByMobile(String mobile, String code) {
		return mobileService.verifyCode(mobile, code);
	}

}
