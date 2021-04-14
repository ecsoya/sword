package com.soyatec.sword.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMailCodeService;
import com.soyatec.sword.service.IMobileCodeService;
import com.soyatec.sword.system.service.ISysUserService;

@RestController
@RequestMapping("/open/code")
public class OpenCodeController extends BaseController {

	@Autowired(required = false)
	private IMailCodeService mailCodeService;
	@Autowired(required = false)
	private IMobileCodeService mobileCodeService;

	@Autowired
	private ISysUserService userService;

	@PostMapping("/deliveryByUsername")
	@RepeatSubmit
	public AjaxResult deliveryByUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return error("请输入用户名");
		}
		final SysUser admin = userService.selectUserByLoginName(username);
		if (admin == null || UserConstants.REGISTER_USER_TYPE.equals(admin.getUserType())) {
			return error("用户不存在");
		}
		if (GlobalConfig.getEmailCode()) {
			if (mailCodeService == null) {
				return error("不支持邮件");
			}
			return toAjax(mailCodeService.sendCodeByUsername(username));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.sendCodeByUsername(username));
	}

	/**
	 *
	 * 验证邮箱、短信验证码
	 */
	@GetMapping("/verifyByUsername")
	@RepeatSubmit
	public AjaxResult verifyByUsername(String username, String code) {
		if (StringUtils.isEmpty(username)) {
			return error("请输入用户名");
		}
		if (StringUtils.isEmpty(code)) {
			return error("请输入验证码");
		}
		final SysUser admin = userService.selectUserByLoginName(username);
		if (admin == null || UserConstants.REGISTER_USER_TYPE.equals(admin.getUserType())) {
			return error("验证码错误");
		}
		if (GlobalConfig.getEmailCode()) {
			if (mailCodeService == null) {
				return error("不支持邮件");
			}
			return toAjax(mailCodeService.verifyCodeByUsername(username, code));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.verifyCodeByUsername(username, code));
	}

}
