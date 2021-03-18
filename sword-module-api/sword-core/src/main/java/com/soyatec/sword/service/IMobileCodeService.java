package com.soyatec.sword.service;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface IMobileCodeService {

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
