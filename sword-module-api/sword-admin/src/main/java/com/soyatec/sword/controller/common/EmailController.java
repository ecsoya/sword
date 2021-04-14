package com.soyatec.sword.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMailCodeService;
import com.soyatec.sword.system.service.ISysUserService;

@RestController
@RequestMapping("/open/email")
public class EmailController extends BaseController {

	@Autowired
	private IMailCodeService mailService;

	@Autowired
	private ISysUserService userService;

	@PostMapping("/delivery")
	@RepeatSubmit
	public AjaxResult delivery(String email) {
		if (!StringUtils.isValidEmail(email)) {
			return error("邮箱格式错误");
		}
		return toAjax(mailService.sendCode(email));
	}

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
		return toAjax(mailService.sendCodeByUsername(username));
	}

	/**
	 *
	 * 验证邮箱验证码
	 */
	@GetMapping("/verify")
	@RepeatSubmit
	public AjaxResult verify(String email, String code) {
		return toAjax(mailService.verifyCode(email, code));
	}

	/**
	 *
	 * 验证邮箱验证码
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
		return toAjax(mailService.verifyCodeByUsername(username, code));
	}

}
