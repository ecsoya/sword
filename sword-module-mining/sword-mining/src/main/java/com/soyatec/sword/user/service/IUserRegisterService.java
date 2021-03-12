package com.soyatec.sword.user.service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.user.domain.User;

public interface IUserRegisterService {
	/**
	 * 注册用户
	 * 
	 * @param user:         用户
	 * @param referrerCode: 推荐码
	 * @return
	 */
	CommonResult<?> registerUser(User user, String referrerCode, String walletPassword);

	CommonResult<?> registerUser(User user, String referrerCode, String walletPassword, boolean async);

	CommonResult<?> postRegisterUser(Long userId, Long referrerId, String referralCode, String walletPassword);

	CommonResult<?> postRegisterUser(Long userId, Long referrerId, String referralCode, String walletPassword,
			boolean async);
}
