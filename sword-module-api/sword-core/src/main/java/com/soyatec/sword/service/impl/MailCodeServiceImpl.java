package com.soyatec.sword.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.code.service.IMailService;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMailCodeService;
import com.soyatec.sword.user.service.IUserProfileService;

@Service
public class MailCodeServiceImpl implements IMailCodeService {

	@Autowired
	private IUserProfileService userService;

	@Autowired(required = false)
	private IMailService mailService;

	@Override
	public CommonResult<?> sendCode(String email) {
		if (mailService == null) {
			return CommonResult.fail("Not implemented");
		}
		if (StringUtils.isEmpty(email)) {
			return CommonResult.fail("Email not exist");
		}
		return mailService.sendCode(email);

	}

	@Override
	public CommonResult<?> verifyCode(String email, String code) {
		if (StringUtils.isEmpty(email)) {
			return CommonResult.fail("Email not exist");
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail("Code is empty");
		}
		if (mailService == null) {
			return CommonResult.fail("Not implemented");
		}
		return mailService.verifyCode(email, code); // $NON-NLS-1$
	}

	@Override
	public CommonResult<?> sendCodeByUserId(Long userId) {
		final String email = userService.selectUserEmailById(userId);
		return sendCode(email);
	}

	@Override
	public CommonResult<?> verifyCodeByUserId(Long userId, String code) {
		final String email = userService.selectUserEmailById(userId);
		return verifyCode(email, code);
	}

	@Override
	public CommonResult<?> verifyCodeByUsername(String username, String code) {
		String email = userService.selectUserEmailByUsername(username);
		if (org.apache.commons.lang3.StringUtils.isBlank(email)) {
			email = username;
		}
		return verifyCode(email, code);
	}

	@Override
	public CommonResult<?> sendCodeByUsername(String username) {
		final String email = userService.selectUserEmailByUsername(username);
		return sendCode(email);
	}

}
