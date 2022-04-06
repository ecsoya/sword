package com.github.ecsoya.sword.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.code.service.IMobileService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.service.IMobileCodeService;
import com.github.ecsoya.sword.user.service.IUserProfileService;

/**
 * The Class MobileCodeServiceImpl.
 */
@Service
public class MobileCodeServiceImpl implements IMobileCodeService {

	/** The mobile service. */
	@Autowired(required = false)
	private IMobileService mobileService;

	/** The user service. */
	@Autowired
	private IUserProfileService userService;

	/**
	 * Send code.
	 *
	 * @param mobile the mobile
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCode(String mobile) {
		if (mobileService == null) {
			return CommonResult.fail("Not implemented");
		}
		return mobileService.sendCode(mobile);

	}

	/**
	 * Verify code.
	 *
	 * @param mobile the mobile
	 * @param code   the code
	 * @return the common result
	 */
	@Override
	public CommonResult<?> verifyCode(String mobile, String code) {
		if (mobileService == null) {
			return CommonResult.fail("Not implemented");
		}
		return mobileService.verifyCode(mobile, code); // $NON-NLS-1$
	}

	/**
	 * Send code by user id.
	 *
	 * @param userId the user id
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCodeByUserId(Long userId) {
		final String mobile = userService.selectUserMobileById(userId);
		return sendCode(mobile);
	}

	/**
	 * Send code by username.
	 *
	 * @param username the username
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCodeByUsername(String username) {
		final String mobile = userService.selectUserMobileByUsername(username);
		return sendCode(mobile);
	}

	/**
	 * Verify code by user id.
	 *
	 * @param userId the user id
	 * @param code   the code
	 * @return the common result
	 */
	@Override
	public CommonResult<?> verifyCodeByUserId(Long userId, String code) {
		final String mobile = userService.selectUserMobileById(userId);
		return verifyCode(mobile, code);
	}

	/**
	 * Verify code by username.
	 *
	 * @param username the username
	 * @param code     the code
	 * @return the common result
	 */
	@Override
	public CommonResult<?> verifyCodeByUsername(String username, String code) {
		String mobile = userService.selectUserMobileByUsername(username);
		if (org.apache.commons.lang3.StringUtils.isBlank(mobile)) {
			mobile = username;
		}
		return verifyCode(mobile, code);
	}
}
