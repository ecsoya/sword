package com.soyatec.sword.service;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface IMobileService {

	/**
	 * 发送邮件
	 */
	public CommonResult<?> sendCode(String mobile, String code);

	/**
	 * 发送验证码
	 */
	public CommonResult<?> sendCode(String mobile);

	/**
	 * 验证验证码是否正确
	 */
	public CommonResult<?> verifyCode(String mobile, String code);

	public CommonResult<?> sendCodeByUserId(Long userId);

	public CommonResult<?> verifyCodeByUserId(Long userId, String code);

	public CommonResult<?> verifyCodeByUsername(String username, String code);
}
