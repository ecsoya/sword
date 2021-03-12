package com.soyatec.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.user.domain.User;
import com.soyatec.sword.user.service.IUserRegisterService;
import com.soyatec.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/open/register")
@Api(tags = { "用户注册" }, description = "注册")
public class RegisterController extends BaseController {

	@Autowired
	private IUserRegisterService userRegisterService;

	@ApiOperation("新用户邮箱注册")
	@PostMapping("/email")
	public CommonResult<?> registerUser(String email, String username, String password, String code,
			String referrerCode, String walletPassword, String mobile) {
		if (StringUtils.isEmpty(email)) {
			return CommonResult.fail("请输入邮箱");
		}
		CommonResult<?> verifyCode = SwordUtils.verifyCode(email, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		User user = new User();
		user.setEmail(email);
		user.setPhonenumber(mobile);
		user.setLoginName(username);
		user.setPassword(password);
		user.setUserType(User.TYPE_USER);
		return userRegisterService.registerUser(user, referrerCode, walletPassword);
	}

	@ApiOperation("新用户手机注册")
	@PostMapping("/mobile")
	public CommonResult<?> registerUserByMobile(String email, String username, String password, String code,
			String referrerCode, String walletPassword, String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("请输入手机号");
		}
		CommonResult<?> verifyCode = SwordUtils.verifyCode(mobile, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		User user = new User();
		user.setEmail(email);
		user.setPhonenumber(mobile);
		user.setLoginName(username);
		user.setPassword(password);
		user.setUserType(User.TYPE_USER);
		return userRegisterService.registerUser(user, referrerCode, walletPassword);
	}

}
