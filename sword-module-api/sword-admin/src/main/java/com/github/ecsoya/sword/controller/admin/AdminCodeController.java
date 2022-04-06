package com.github.ecsoya.sword.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.code.service.IMailService;
import com.github.ecsoya.sword.code.service.IMobileService;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Class AdminCodeController.
 */
@Controller
@RequestMapping("/admin/code")
public class AdminCodeController extends BaseController {

	/** The Constant prefix. */
	private static final String prefix = "admin/code";

	/** The mail service. */
	@Autowired
	private IMailService mailService;

	/** The mobile service. */
	@Autowired
	private IMobileService mobileService;

	/**
	 * Code.
	 *
	 * @return the string
	 */
	@GetMapping
	public String code() {
		return prefix + "/code";
	}

	/**
	 * Send email.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param content the content
	 * @return the ajax result
	 */
	@PostMapping("/email")
	@ResponseBody
	public AjaxResult sendEmail(String email, String subject, String content) {
		final CommonResult<?> sent = mailService.sendEmail(email, subject, content);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	/**
	 * Send mobile.
	 *
	 * @param mobile  the mobile
	 * @param message the message
	 * @return the ajax result
	 */
	@PostMapping("/mobile")
	@ResponseBody
	public AjaxResult sendMobile(String mobile, String message) {
		final CommonResult<?> sent = mobileService.sendMessage(mobile, message);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	/**
	 * Send email code.
	 *
	 * @param email the email
	 * @return the ajax result
	 */
	@PostMapping("/email/code")
	@ResponseBody
	public AjaxResult sendEmailCode(String email) {
		final CommonResult<?> sent = mailService.sendCode(email);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	/**
	 * Send mobile code.
	 *
	 * @param mobile the mobile
	 * @return the ajax result
	 */
	@PostMapping("/mobile/code")
	@ResponseBody
	public AjaxResult sendMobileCode(String mobile) {
		final CommonResult<?> sent = mobileService.sendCode(mobile);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}
}
