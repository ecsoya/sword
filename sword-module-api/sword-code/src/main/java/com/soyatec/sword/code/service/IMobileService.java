package com.soyatec.sword.code.service;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface IMobileService {

	/**
	 * 发送邮件
	 */
	public CommonResult<?> sendMessage(String mobile, String message);

	/**
	 * 发送验证码
	 */
	public CommonResult<?> sendCode(String mobile);

	/**
	 * 验证验证码是否正确
	 */
	public CommonResult<?> verifyCode(String mobile, String code);

}
