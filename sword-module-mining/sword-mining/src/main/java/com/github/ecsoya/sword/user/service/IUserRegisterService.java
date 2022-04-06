package com.github.ecsoya.sword.user.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.user.domain.User;

/**
 * The Interface IUserRegisterService.
 */
public interface IUserRegisterService {

	/**
	 * Register user.
	 *
	 * @param user           the user
	 * @param referrerCode   the referrer code
	 * @param walletPassword the wallet password
	 * @return the common result
	 */
	CommonResult<?> registerUser(User user, String referrerCode, String walletPassword);

	/**
	 * Register user.
	 *
	 * @param user           the user
	 * @param referrerCode   the referrer code
	 * @param walletPassword the wallet password
	 * @param async          the async
	 * @return the common result
	 */
	CommonResult<?> registerUser(User user, String referrerCode, String walletPassword, boolean async);

	/**
	 * Post register user.
	 *
	 * @param userId         the user id
	 * @param referrerId     the referrer id
	 * @param referralCode   the referral code
	 * @param walletPassword the wallet password
	 * @return the common result
	 */
	CommonResult<?> postRegisterUser(Long userId, Long referrerId, String referralCode, String walletPassword);

	/**
	 * Post register user.
	 *
	 * @param userId         the user id
	 * @param referrerId     the referrer id
	 * @param referralCode   the referral code
	 * @param walletPassword the wallet password
	 * @param async          the async
	 * @return the common result
	 */
	CommonResult<?> postRegisterUser(Long userId, Long referrerId, String referralCode, String walletPassword,
			boolean async);
}
