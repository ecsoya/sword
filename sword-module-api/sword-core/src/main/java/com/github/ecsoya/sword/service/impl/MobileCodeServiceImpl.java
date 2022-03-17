package com.github.ecsoya.sword.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.code.service.IMobileService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.service.IMobileCodeService;
import com.github.ecsoya.sword.user.service.IUserProfileService;

@Service
public class MobileCodeServiceImpl implements IMobileCodeService {

	@Autowired(required = false)
	private IMobileService mobileService;

	@Autowired
	private IUserProfileService userService;

	@Override
	public CommonResult<?> sendCode(String mobile) {
		if (mobileService == null) {
			return CommonResult.fail("Not implemented");
		}
		return mobileService.sendCode(mobile);

	}

	@Override
	public CommonResult<?> verifyCode(String mobile, String code) {
		if (mobileService == null) {
			return CommonResult.fail("Not implemented");
		}
		return mobileService.verifyCode(mobile, code); // $NON-NLS-1$
	}

	@Override
	public CommonResult<?> sendCodeByUserId(Long userId) {
		final String mobile = userService.selectUserMobileById(userId);
		return sendCode(mobile);
	}

	@Override
	public CommonResult<?> sendCodeByUsername(String username) {
		final String mobile = userService.selectUserMobileByUsername(username);
		return sendCode(mobile);
	}

	@Override
	public CommonResult<?> verifyCodeByUserId(Long userId, String code) {
		final String mobile = userService.selectUserMobileById(userId);
		return verifyCode(mobile, code);
	}

	@Override
	public CommonResult<?> verifyCodeByUsername(String username, String code) {
		String mobile = userService.selectUserMobileByUsername(username);
		if (org.apache.commons.lang3.StringUtils.isBlank(mobile)) {
			mobile = username;
		}
		return verifyCode(mobile, code);
	}
}
