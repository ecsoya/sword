package com.soyatec.sword.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.service.IMailCodeService;
import com.soyatec.sword.service.IMobileCodeService;

@RestController
@RequestMapping("/code")
public class CodeController extends BaseController {

	@Autowired(required = false)
	private IMailCodeService mailCodeService;
	@Autowired(required = false)
	private IMobileCodeService mobileCodeService;

	@PostMapping("/delivery")
	@RepeatSubmit
	public AjaxResult delivery() {
		final Long userId = ShiroUtils.getUserId();
		if (GlobalConfig.getEmailCode()) {
			if (mailCodeService == null) {
				return error("不支持邮件");
			}
			return toAjax(mailCodeService.sendCodeByUserId(userId));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.sendCodeByUserId(userId));
	}

	/**
	 *
	 * 验证邮箱、短信验证码
	 */
	@PostMapping("/verify")
	@RepeatSubmit
	public AjaxResult verifyCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return error("请输入验证码");
		}
		final Long userId = ShiroUtils.getUserId();
		if (GlobalConfig.getEmailCode()) {
			if (mailCodeService == null) {
				return error("不支持邮件");
			}
			return toAjax(mailCodeService.verifyCodeByUserId(userId, code));
		}
		if (mobileCodeService == null) {
			return error("不支持短信");
		}
		return toAjax(mobileCodeService.verifyCodeByUserId(userId, code));
	}

}
