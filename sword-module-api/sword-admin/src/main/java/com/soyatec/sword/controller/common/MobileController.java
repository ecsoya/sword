package com.soyatec.sword.controller.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soyatec.sword.common.annotation.RepeatSubmit;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMobileCodeService;

@RestController
@RequestMapping("/open/mobile")
public class MobileController extends BaseController {

	@Autowired
	private IMobileCodeService mobileService;

	@PostMapping("/delivery")
	@RepeatSubmit
	public CommonResult<?> deliveryBymobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("Invalid mobile address");
		}
		return mobileService.sendCode(mobile);
	}

	@GetMapping("/verify")
	@RepeatSubmit
	public CommonResult<?> verifyByMobile(String mobile, String code) {
		return mobileService.verifyCode(mobile, code);
	}

}
