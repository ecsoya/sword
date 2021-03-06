package com.github.ecsoya.sword.framework.shiro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.manager.factory.AsyncFactory;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysUserService;

/**
 * The Class SysRegisterService.
 */
@Component
public class SysRegisterService {

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/** The password service. */
	@Autowired
	private SysPasswordService passwordService;

	/**
	 * Register.
	 *
	 * @param user the user
	 * @return the string
	 */
	public String register(SysUser user) {
		String msg = "";
		final String loginName = user.getLoginName(), password = user.getPassword();

		if (!StringUtils.isEmpty(ServletUtils.getRequest().getAttribute(ShiroConstants.CURRENT_CAPTCHA))) {
			msg = "验证码错误";
		} else if (StringUtils.isEmpty(loginName)) {
			msg = "用户名不能为空";
		} else if (StringUtils.isEmpty(password)) {
			msg = "用户密码不能为空";
		} else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
				|| password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
			msg = "密码长度必须在5到20个字符之间";
		} else if (loginName.length() < UserConstants.USERNAME_MIN_LENGTH
				|| loginName.length() > UserConstants.USERNAME_MAX_LENGTH) {
			msg = "账户长度必须在2到20个字符之间";
		} else if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(loginName))) {
			msg = "保存用户'" + loginName + "'失败，注册账号已存在";
		} else {
			user.setPwdUpdateDate(DateUtils.getNowDate());
			user.setUserName(loginName);
			user.setSalt(ShiroUtils.randomSalt());
			user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
			final boolean regFlag = userService.registerUser(user);
			if (!regFlag) {
				msg = "注册失败,请联系系统管理人员";
			} else {
				AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginName, Constants.REGISTER,
						MessageUtils.message("user.register.success")));
			}
		}
		return msg;
	}
}
