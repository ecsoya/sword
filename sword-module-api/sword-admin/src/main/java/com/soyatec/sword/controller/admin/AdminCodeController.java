package com.soyatec.sword.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soyatec.sword.code.service.IMailService;
import com.soyatec.sword.code.service.IMobileService;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.CommonResult;

@Controller
@RequestMapping("/admin/code")
public class AdminCodeController extends BaseController {
	private static final String prefix = "admin/code";

	@Autowired
	private IMailService mailService;

	@Autowired
	private IMobileService mobileService;

	@GetMapping
	public String code() {
		return prefix + "/code";
	}

	@PostMapping("/email")
	@ResponseBody
	public AjaxResult sendEmail(String email, String subject, String content) {
		CommonResult<?> sent = mailService.sendEmail(email, subject, content);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	@PostMapping("/mobile")
	@ResponseBody
	public AjaxResult sendMobile(String mobile, String message) {
		CommonResult<?> sent = mobileService.sendMessage(mobile, message);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	@PostMapping("/email/code")
	@ResponseBody
	public AjaxResult sendEmailCode(String email) {
		CommonResult<?> sent = mailService.sendCode(email);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	@PostMapping("/mobile/code")
	@ResponseBody
	public AjaxResult sendMobileCode(String mobile) {
		CommonResult<?> sent = mobileService.sendCode(mobile);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}
}
