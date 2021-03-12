package com.soyatec.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.user.domain.UserReferrer;
import com.soyatec.sword.user.service.IUserReferrerService;
import com.soyatec.sword.utils.SwordUtils;

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
	@ApiOperation("获取推荐码")
	@GetMapping("/code")
	public CommonResult<UserReferrer> code() {
		Long userId = SwordUtils.getUserId();
		return CommonResult.build(referrerService.selectUserReferrerById(userId));
	}

	/**
	 * 强制刷新二维码
	 */
	@ApiOperation("强制刷新二维码")
	@PostMapping("/refresh")
	public CommonResult<UserReferrer> refresh() {
		Long userId = SwordUtils.getUserId();
		return CommonResult.build(referrerService.refreshUserReferrerById(userId));
	}

}
