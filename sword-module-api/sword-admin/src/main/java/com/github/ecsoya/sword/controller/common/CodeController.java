package com.github.ecsoya.sword.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ecsoya.sword.common.annotation.RepeatSubmit;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.service.IMailCodeService;
import com.github.ecsoya.sword.service.IMobileCodeService;
import com.github.ecsoya.sword.system.service.ISysUserService;

/**
 * The Class CodeController.
 */
@RestController
@RequestMapping("/open/code")
public class CodeController extends BaseController {

	/** The mail code service. */
	@Autowired(required = false)
	private IMailCodeService mailCodeService;

	/** The mobile code service. */
	@Autowired(required = false)
	private IMobileCodeService mobileCodeService;

	/** The user service. */
	@Autowired
	private ISysUserService userService;

	/**
	 * Delivery by username.
	 *
	 * @param username the username
	 * @param subject  the subject
	 * @param template the template
	 * @return the ajax result
	 */
	@PostMapping("/deliveryByUsername")
	@RepeatSubmit
	public AjaxResult deliveryByUsername(String username, String subject, String template) {
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
			return toAjax(mailCodeService.sendCodeByUsername(username, subject, template));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.sendCodeByUsername(username));
	}

	/**
	 * Verify by username.
	 *
	 * @param username the username
	 * @param code     the code
	 * @return the ajax result
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
