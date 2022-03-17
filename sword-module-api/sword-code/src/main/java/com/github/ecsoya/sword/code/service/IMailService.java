package com.github.ecsoya.sword.code.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

public interface IMailService {

	/**
	 * 发送邮件
	 */
	public CommonResult<?> sendEmail(String email, String subject, String content);

	/**
	 * 发送验证码
	 */
	public CommonResult<?> sendCode(String email, String subject, String template);

	/**
	 * 发送验证码
	 */
	public CommonResult<?> sendCode(String email);

	/**
	 * 验证验证码是否正确
	 */
	public CommonResult<?> verifyCode(String email, String code);

}
