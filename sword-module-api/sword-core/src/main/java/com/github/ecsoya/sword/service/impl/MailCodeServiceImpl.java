package com.github.ecsoya.sword.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.code.service.IMailService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.service.IMailCodeService;
import com.github.ecsoya.sword.user.service.IUserProfileService;

/**
 * The Class MailCodeServiceImpl.
 */
@Service
public class MailCodeServiceImpl implements IMailCodeService {

	/** The user service. */
	@Autowired
	private IUserProfileService userService;

	/** The mail service. */
	@Autowired(required = false)
	private IMailService mailService;

	/**
	 * Send code.
	 *
	 * @param email the email
	 * @return the common result
	 */
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

	/**
	 * Send code.
	 *
	 * @param email    the email
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCode(String email, String subject, String template) {
		if (mailService == null) {
			return CommonResult.fail("Not implemented");
		}
		if (StringUtils.isEmpty(email)) {
			return CommonResult.fail("Email not exist");
		}
		return mailService.sendCode(email, subject, template);
	}

	/**
	 * Verify code.
	 *
	 * @param email the email
	 * @param code  the code
	 * @return the common result
	 */
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

	/**
	 * Send code by user id.
	 *
	 * @param userId the user id
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCodeByUserId(Long userId) {
		final String email = userService.selectUserEmailById(userId);
		return sendCode(email);
	}

	/**
	 * Send code by user id.
	 *
	 * @param userId   the user id
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCodeByUserId(Long userId, String subject, String template) {
		final String email = userService.selectUserEmailById(userId);
		return sendCode(email, subject, template);
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
		final String email = userService.selectUserEmailById(userId);
		return verifyCode(email, code);
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
		String email = userService.selectUserEmailByUsername(username);
		if (org.apache.commons.lang3.StringUtils.isBlank(email)) {
			email = username;
		}
		return verifyCode(email, code);
	}

	/**
	 * Send code by username.
	 *
	 * @param username the username
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCodeByUsername(String username) {
		final String email = userService.selectUserEmailByUsername(username);
		return sendCode(email);
	}

	/**
	 * Send code by username.
	 *
	 * @param username the username
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCodeByUsername(String username, String subject, String template) {
		final String email = userService.selectUserEmailByUsername(username);
		return sendCode(email, subject, template);
	}
}
