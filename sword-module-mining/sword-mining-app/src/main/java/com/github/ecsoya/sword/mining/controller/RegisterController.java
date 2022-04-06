package com.github.ecsoya.sword.mining.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.system.service.ISysUserService;
import com.github.ecsoya.sword.user.domain.User;
import com.github.ecsoya.sword.user.service.IUserProfileService;
import com.github.ecsoya.sword.user.service.IUserRegisterService;
import com.github.ecsoya.sword.utils.SwordUtils;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * The Class RegisterController.
 */
@RestController
@RequestMapping("/open")
@Api(tags = { "用户注册" }, description = "注册、设置密码")
public class RegisterController extends BaseController {

	/** The user register service. */
	@Autowired
	private IUserRegisterService userRegisterService;

	/** The user profie service. */
	@Autowired
	private IUserProfileService userProfieService;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/** The user wallet service. */
	@Autowired
	private IUserWalletService userWalletService;

	/**
	 * Register user.
	 *
	 * @param email          the email
	 * @param username       the username
	 * @param password       the password
	 * @param code           the code
	 * @param referrerCode   the referrer code
	 * @param walletPassword the wallet password
	 * @param mobile         the mobile
	 * @return the common result
	 */
	@ApiOperation("新用户邮箱注册")
	@PostMapping("/register/email")
	@RepeatSubmit
	public CommonResult<?> registerUser(@ApiParam("邮箱") String email, @ApiParam("用户名") String username,
			@ApiParam("登录密码") String password, @ApiParam("验证码") String code, @ApiParam("推荐码") String referrerCode,
			@ApiParam("支付密码") String walletPassword, @ApiParam("手机号") String mobile) {
		if (StringUtils.isEmpty(email)) {
			return CommonResult.fail(MessageUtils.message("RegisterController.0")); //$NON-NLS-1$
		}
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(email, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		final User user = new User();
		user.setEmail(email);
		user.setPhonenumber(mobile);
		user.setLoginName(username);
		user.setUserName(username);
		user.setPassword(password);
		user.setUserType(User.TYPE_USER);
		return userRegisterService.registerUser(user, referrerCode, walletPassword);
	}

	/**
	 * Register user by mobile.
	 *
	 * @param email          the email
	 * @param username       the username
	 * @param password       the password
	 * @param code           the code
	 * @param referrerCode   the referrer code
	 * @param walletPassword the wallet password
	 * @param mobile         the mobile
	 * @return the common result
	 */
	@ApiOperation("新用户手机注册")
	@PostMapping("/register/mobile")
	@RepeatSubmit
	public CommonResult<?> registerUserByMobile(@ApiParam("邮箱") String email, @ApiParam("用户名") String username,
			@ApiParam("登录密码") String password, @ApiParam("验证码") String code, @ApiParam("推荐码") String referrerCode,
			@ApiParam("支付密码") String walletPassword, @ApiParam("手机号") String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail(MessageUtils.message("RegisterController.1")); //$NON-NLS-1$
		}
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(mobile, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		final User user = new User();
		user.setEmail(email);
		user.setPhonenumber(mobile);
		user.setLoginName(username);
		user.setUserName(username);
		user.setPassword(password);
		user.setUserType(User.TYPE_USER);
		return userRegisterService.registerUser(user, referrerCode, walletPassword);
	}

	/**
	 * Reset pwd by mobile.
	 *
	 * @param mobile         the mobile
	 * @param password       the password
	 * @param code           the code
	 * @param walletPassword the wallet password
	 * @return the common result
	 */
	@ApiOperation("用户通过手机重置登录密码和钱包支付密码")
	@PostMapping("/resetPwd/mobile")
	@RepeatSubmit
	public CommonResult<?> resetPwdByMobile(@ApiParam("手机号") String mobile, @ApiParam("新的登录密码") String password,
			@ApiParam("验证码") String code, @ApiParam("新的支付密码") String walletPassword) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(walletPassword) || StringUtils.isEmpty(password)) {
			return CommonResult.fail(MessageUtils.message("RegisterController.2")); //$NON-NLS-1$
		}
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(mobile, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		final SysUser user = userService.selectUserByPhoneNumber(mobile);
		if (user == null || !User.TYPE_USER.equals(user.getUserType())) {
			return CommonResult.fail(MessageUtils.message("RegisterController.3")); //$NON-NLS-1$
		}
		final CommonResult<?> resetUserPassword = userProfieService.resetUserPassword(user.getUserId(), password);
		if (!resetUserPassword.isSuccess()) {
			return resetUserPassword;
		}
		return CommonResult.ajax(userWalletService.resetUserWalletPassword(user.getUserId(), walletPassword));
	}

	/**
	 * Reset pwd by email.
	 *
	 * @param email          the email
	 * @param password       the password
	 * @param code           the code
	 * @param walletPassword the wallet password
	 * @param mobile         the mobile
	 * @param loginName      the login name
	 * @return the common result
	 */
	@ApiOperation("用户通过邮箱重置登录密码和钱包支付密码")
	@PostMapping("/resetPwd/email")
	@RepeatSubmit
	public CommonResult<?> resetPwdByEmail(@ApiParam("邮箱") String email, @ApiParam("新的登录密码") String password,
			@ApiParam("验证码") String code, @ApiParam("新的支付密码") String walletPassword, @ApiParam("手机") String mobile,
			@ApiParam("用户名") String loginName) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(walletPassword) || StringUtils.isEmpty(password)) {
			return CommonResult.fail(MessageUtils.message("RegisterController.4")); //$NON-NLS-1$
		}
		final CommonResult<?> verifyCode = SwordUtils.verifyCode(email, code);
		if (!verifyCode.isSuccess()) {
			return verifyCode;
		}
		final SysUser user = userService.selectUserByEmail(email);
		if (user == null || !User.TYPE_USER.equals(user.getUserType())) {
			return CommonResult.fail(MessageUtils.message("RegisterController.5")); //$NON-NLS-1$
		}
		if (StringUtils.isNotEmpty(mobile)
				&& !org.apache.commons.lang3.StringUtils.equals(mobile, user.getPhonenumber())) {
			return CommonResult.fail(MessageUtils.message("RegisterController.5") + "【1】");
		}
		if (StringUtils.isNotEmpty(loginName)
				&& !org.apache.commons.lang3.StringUtils.equals(loginName, user.getLoginName())) {
			return CommonResult.fail(MessageUtils.message("RegisterController.5") + "【2】");
		}
		final CommonResult<?> resetUserPassword = userProfieService.resetUserPassword(user.getUserId(), password);
		if (!resetUserPassword.isSuccess()) {
			return resetUserPassword;
		}
		return CommonResult.ajax(userWalletService.resetUserWalletPassword(user.getUserId(), walletPassword));
	}

}
