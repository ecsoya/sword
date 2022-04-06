package com.github.ecsoya.sword.framework.shiro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.enums.UserStatus;
import com.github.ecsoya.sword.common.exception.user.CaptchaException;
import com.github.ecsoya.sword.common.exception.user.UserBlockedException;
import com.github.ecsoya.sword.common.exception.user.UserDeleteException;
import com.github.ecsoya.sword.common.exception.user.UserNotExistsException;
import com.github.ecsoya.sword.common.exception.user.UserPasswordNotMatchException;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.manager.factory.AsyncFactory;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysUserService;

/**
 * The Class SysLoginService.
 */
@Component
public class SysLoginService {

	/** The password service. */
	@Autowired
	private SysPasswordService passwordService;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/**
	 * Login.
	 *
	 * @param username        the username
	 * @param password        the password
	 * @param userTypes       the user types
	 * @param withoutPassword the without password
	 * @return the sys user
	 */
	public SysUser login(String username, String password, List<String> userTypes, boolean withoutPassword) {
		// 验证码校验
		if (ShiroConstants.CAPTCHA_ERROR
				.equals(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.jcaptcha.error")));
			throw new CaptchaException();
		}
		// 用户名或密码为空 错误
		if (StringUtils.isEmpty(username)) {
			AsyncManager.me().execute(
					AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("not.null")));
			throw new UserNotExistsException();
		}
		if (!withoutPassword) {
			// 密码如果不在指定范围内 错误
			if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
					|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
						MessageUtils.message("user.password.not.match")));
				throw new UserPasswordNotMatchException();
			}
		}

		// 用户名不在指定范围内 错误
		if (username.length() < UserConstants.USERNAME_MIN_LENGTH
				|| username.length() > UserConstants.USERNAME_MAX_LENGTH) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.not.match")));
			throw new UserPasswordNotMatchException();
		}

		// 查询用户信息
		final SysUser user = userService.selectUserByLoginName(username);

		/**
		 * if (user == null && maybeMobilePhoneNumber(username)) { user =
		 * userService.selectUserByPhoneNumber(username); }
		 *
		 * if (user == null && maybeEmail(username)) { user =
		 * userService.selectUserByEmail(username); }
		 */

		if (user == null) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.not.exists")));
			throw new UserNotExistsException();
		} // 判断类型是否匹配
		else if (userTypes != null && !userTypes.isEmpty() && !"develop".equals(username)
				&& !userTypes.contains(user.getUserType())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.not.exists")));
			throw new UserNotExistsException();
		}

		if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.password.delete")));
			throw new UserDeleteException();
		}

		if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
			AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL,
					MessageUtils.message("user.blocked", user.getRemark())));
			throw new UserBlockedException();
		}
		if (!withoutPassword) {
			passwordService.validate(user, password);
		}

		AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS,
				MessageUtils.message("user.login.success")));
		recordLoginInfo(user);
		return user;
	}

	/**
	 * Record login info. 记录登录信息
	 *
	 * @param user the user
	 */
	public void recordLoginInfo(SysUser user) {
		user.setLoginIp(ShiroUtils.getIp());
		user.setLoginDate(DateUtils.getNowDate());
		userService.updateUserInfo(user);
	}
}
