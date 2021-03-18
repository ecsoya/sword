package com.soyatec.sword.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.service.IMailService;
import com.soyatec.sword.service.IMobileService;

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
	public AjaxResult sendEmailCode(String email, String subject, String content) {
		CommonResult<?> sent = mailService.sendCode(email, subject, content);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}

	@PostMapping("/mobile")
	@ResponseBody
	public AjaxResult sendMobile(String mobile, String code) {
		CommonResult<?> sent = mobileService.sendCode(mobile, code);
		if (sent.isSuccess()) {
			return success("发送成功");
		}
		return error(sent.getInfo());
	}
}
