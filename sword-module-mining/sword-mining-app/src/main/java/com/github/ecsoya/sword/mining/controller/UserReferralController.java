package com.github.ecsoya.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.UserReferrer;
import com.github.ecsoya.sword.user.service.IUserReferrerService;
import com.github.ecsoya.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户推荐码接口
 *
 * @author ecsoya
 */
@RestController
@RequestMapping("/user/referral")
@Api(tags = { "推荐码" }, description = "查看、刷新")
public class UserReferralController extends BaseController {

	@Autowired
	private IUserReferrerService referrerService;

	/**
	 * 获取推荐码
	 */
	@ApiOperation(value = "获取推荐码", notes = "如果只支持一个推荐码，用left开头的变量即可")
	@GetMapping("/code")
	public CommonResult<UserReferrer> code() {
		final Long userId = SwordUtils.getUserId();
		return CommonResult.build(referrerService.selectUserReferrerById(userId));
	}

	/**
	 * 强制刷新二维码
	 */
	@ApiOperation("强制刷新二维码")
	@PostMapping("/refresh")
	@RepeatSubmit
	public CommonResult<UserReferrer> refresh() {
		final Long userId = SwordUtils.getUserId();
		return CommonResult.build(referrerService.refreshUserReferrerById(userId));
	}

}
