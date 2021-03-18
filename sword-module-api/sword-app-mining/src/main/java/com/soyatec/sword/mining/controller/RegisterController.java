package com.soyatec.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.system.service.ISysUserService;
import com.soyatec.sword.user.domain.User;
import com.soyatec.sword.user.service.IUserProfileService;
import com.soyatec.sword.user.service.IUserRegisterService;
import com.soyatec.sword.utils.SwordUtils;
import com.soyatec.sword.wallet.service.IUserWalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/open")
@Api(tags = { "用户注册" }, description = "注册、设置密码")
public class RegisterController extends BaseController {

	@Autowired
	private IUserRegisterService userRegisterService;

	@Autowired
	private IUserProfileService userProfieService;

	@Autowired
	private ISysUserService userService;

	@Autowired
	private IUserWalletService userWalletService;

	@ApiOperation("新用户邮箱注册")
	@PostMapping("/register/email")
	@RepeatSubmit
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
	@PostMapping("/register/mobile")
	@RepeatSubmit
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

	@ApiOperation("用户通过手机重置登录密码和钱包支付密码")
	@PostMapping("/resetPwd/mobile")
	@RepeatSubmit
	public CommonResult<?> resetPwdByMobile(String mobile, String password, String code, String walletPassword) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(walletPassword) || StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		CommonResult<?> verifyCode = SwordUtils.verifyCode(mobile, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		SysUser user = userService.selectUserByPhoneNumber(mobile);
		if (user == null || !User.TYPE_USER.equals(user.getUserType())) {
			return CommonResult.fail("用户不存在");
		}
		CommonResult<?> resetUserPassword = userProfieService.resetUserPassword(user.getUserId(), password);
		if (!resetUserPassword.isSuccess()) {
			return resetUserPassword;
		}
		return CommonResult.ajax(userWalletService.resetUserWalletPassword(user.getUserId(), walletPassword));
	}

	@ApiOperation("用户通过邮箱重置登录密码和钱包支付密码")
	@PostMapping("/resetPwd/email")
	@RepeatSubmit
	public CommonResult<?> resetPwdByEmail(String email, String password, String code, String walletPassword) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(walletPassword) || StringUtils.isEmpty(password)) {
			return CommonResult.fail("参数错误");
		}
		CommonResult<?> verifyCode = SwordUtils.verifyCode(email, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		SysUser user = userService.selectUserByEmail(email);
		if (user == null || !User.TYPE_USER.equals(user.getUserType())) {
			return CommonResult.fail("用户不存在");
		}
		CommonResult<?> resetUserPassword = userProfieService.resetUserPassword(user.getUserId(), password);
		if (!resetUserPassword.isSuccess()) {
			return resetUserPassword;
		}
		return CommonResult.ajax(userWalletService.resetUserWalletPassword(user.getUserId(), walletPassword));
	}

}
