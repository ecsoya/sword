package com.soyatec.sword.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.framework.shiro.realm.AnonymousAuthenticationToken;
import com.soyatec.sword.framework.shiro.realm.TypedAuthenticationToken;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.system.service.ISysUserService;
import com.soyatec.sword.user.service.IUserProfileService;
import com.soyatec.sword.utils.SwordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/open")
@Api(tags = { "登录" }, description = "登录、退出、账号切换、忘记密码")
public class LoginController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private ISysUserService sysUserService;

	@Autowired
	private IUserProfileService userService;

	/**
	 * 用户登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation("用户名+密码+验证码登录")
	@PostMapping("/login")
	@RepeatSubmit
	public CommonResult<?> login(@ApiParam("用户名") String username, @ApiParam("密码") String password,
			@ApiParam("验证码") String code, Long version) {
		log.info("username={}, version={}", username, version);
		final CommonResult<?> checkVersion = SwordUtils.checkVersion(version);
		if (!checkVersion.isSuccess()) {
			return checkVersion;
		}
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return CommonResult.fail(MessageUtils.message("LoginController.0")); //$NON-NLS-1$
		}
		final CommonResult<?> verifiedCode = SwordUtils.verifyCodeByUsername(username, code);
		if (!verifiedCode.isSuccess()) {
			return verifiedCode;
		}

		final TypedAuthenticationToken token = new TypedAuthenticationToken(username, password, true,
				UserConstants.REGISTER_USER_TYPE);
		final Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			final Session session = subject.getSession();
			return CommonResult.success(session.getId());
		} catch (final AuthenticationException e) {
			String msg = MessageUtils.message("LoginController.3"); //$NON-NLS-1$
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return CommonResult.fail(msg);
		}
	}

	/**
	 * 用户登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "手机号+验证码登录", notes = "使用手机号+短信验证码快捷登录，此API使用于能发手机验证码，且手机号码唯一的系统")
	@PostMapping("/loginByMobile")
	@RepeatSubmit
	public CommonResult<?> loginByMobile(@ApiParam("用户名") String mobile, @ApiParam("验证码") String code, Long version) {
		log.info("mobile={}, version={}", mobile, version);
		final CommonResult<?> checkVersion = SwordUtils.checkVersion(version);
		if (!checkVersion.isSuccess()) {
			return checkVersion;
		}
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail(MessageUtils.message("LoginController.0")); //$NON-NLS-1$
		}
		final CommonResult<?> verifiedCode = SwordUtils.verifyCode(mobile, code);
		if (!verifiedCode.isSuccess()) {
			return verifiedCode;
		}
		final SysUser user = sysUserService.selectUserByPhoneNumber(mobile);
		if (user == null) {
			return CommonResult.fail("此号码尚未注册，请注册后使用");
		}

		final AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(user, true,
				UserConstants.REGISTER_USER_TYPE);
		final Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			final Session session = subject.getSession();
			return CommonResult.success(session.getId());
		} catch (final AuthenticationException e) {
			String msg = MessageUtils.message("LoginController.3"); //$NON-NLS-1$
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return CommonResult.fail(msg);
		}
	}

	/**
	 * 用户登录
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@ApiOperation(value = "邮箱+验证码登录", notes = "使用邮箱+验证码快捷登录，此API适用于能发送邮箱验证码，且邮箱唯一的系统")
	@PostMapping("/loginByEmail")
	@RepeatSubmit
	public CommonResult<?> loginByEmail(@ApiParam("用户名") String email, @ApiParam("验证码") String code, Long version) {
		log.info("email={}, version={}", email, version);
		final CommonResult<?> checkVersion = SwordUtils.checkVersion(version);
		if (!checkVersion.isSuccess()) {
			return checkVersion;
		}
		if (StringUtils.isEmpty(email)) {
			return CommonResult.fail(MessageUtils.message("LoginController.0")); //$NON-NLS-1$
		}
		final CommonResult<?> verifiedCode = SwordUtils.verifyCode(email, code);
		if (!verifiedCode.isSuccess()) {
			return verifiedCode;
		}
		final SysUser user = sysUserService.selectUserByEmail(email);
		if (user == null) {
			return CommonResult.fail("此邮箱尚未注册，请注册后使用");
		}

		final AnonymousAuthenticationToken token = new AnonymousAuthenticationToken(user, true,
				UserConstants.REGISTER_USER_TYPE);
		final Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			final Session session = subject.getSession();
			return CommonResult.success(session.getId());
		} catch (final AuthenticationException e) {
			String msg = MessageUtils.message("LoginController.3"); //$NON-NLS-1$
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return CommonResult.fail(msg);
		}
	}

	@ApiOperation("重置密码")
	@PostMapping("/resetPwd")
	@RepeatSubmit
	public CommonResult<?> resetPwd(String username, String code, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || password.length() < 6) {
			return CommonResult.fail(MessageUtils.message("LoginController.4")); //$NON-NLS-1$
		}
//		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
//			return CommonResult.fail(MessageUtils.message("LoginController.5")); //$NON-NLS-1$
//		}
		final CommonResult<?> verifiedCode = SwordUtils.verifyCodeByUsername(username, code);
		if (!verifiedCode.isSuccess()) {
			return CommonResult.fail(MessageUtils.message("LoginController.6")); //$NON-NLS-1$
		}
		final SysUser user = sysUserService.selectUserByLoginName(username);
		if (user == null) {
			return CommonResult.fail(MessageUtils.message("LoginController.7")); //$NON-NLS-1$
		}
		final SysUser resetPwd = new SysUser();
		resetPwd.setUserId(user.getUserId());
		resetPwd.setSalt(ShiroUtils.randomSalt());
		resetPwd.setPassword(StringUtils.encryptPassword(username, password, resetPwd.getSalt()));
		return CommonResult.ajax(sysUserService.resetUserPwd(resetPwd));
	}

	@ApiOperation("加载同邮箱账号")
	@GetMapping("/accounts")
	public CommonResult<List<String>> accounts() {
		return userService.selectUserAccountsFromEmail(SwordUtils.getUserId());
	}

	@ApiOperation("切换账号")
	@PostMapping("/switchAccount")
	@RepeatSubmit
	public CommonResult<?> switchAccount(String account) {
		if (StringUtils.isEmpty(account)) {
			return CommonResult.fail(MessageUtils.message("LoginController.8"));// 参数错误 //$NON-NLS-1$
		}
		final Long userId = SwordUtils.getUserId();
		final String oldEmail = userService.selectUserEmailById(userId);
		final String newEmail = userService.selectUserEmailByUsername(account);
		if (!org.apache.commons.lang3.StringUtils.equals(oldEmail, newEmail)) {
			return CommonResult.fail(MessageUtils.message("LoginController.9"));// 非法请求 //$NON-NLS-1$
		}
		final Subject subject = SecurityUtils.getSubject();
		final SysUser user = sysUserService.selectUserByLoginName(account);
		if (user == null || userId.equals(user.getUserId())) {
			return CommonResult.success(subject.getSession().getId());
		}

		final PrincipalCollection principalCollection = subject.getPrincipals();
		final String realmName = principalCollection.getRealmNames().iterator().next();
		final PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
		subject.runAs(newPrincipalCollection);
		return CommonResult.success(subject.getSession().getId());
//		ShiroUtils.logout();
//		FreeAuthenticationToken token = new FreeAuthenticationToken(account, true, User.TYPE_USER);
//		Subject subject = subject;
//		try {
//			subject.login(token);
//			Session session = subject.getSession();
//			return CommonResult.success(session.getId());
//		} catch (AuthenticationException e) {
//			String msg = MessageUtils.message("LoginController.10"); //$NON-NLS-1$
//			if (StringUtils.isNotEmpty(e.getMessage())) {
//				msg = e.getMessage();
//			}
//			return CommonResult.fail(msg);
//		}
	}

	/**
	 * 用户退出
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	@PostMapping("/exit")
	@RepeatSubmit
	public CommonResult<?> logout() {
		ShiroUtils.logout();
		return CommonResult.success();
	}
}
