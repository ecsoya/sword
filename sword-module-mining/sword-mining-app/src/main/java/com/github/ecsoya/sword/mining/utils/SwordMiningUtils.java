package com.github.ecsoya.sword.mining.utils;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.utils.SwordUtils;
import com.github.ecsoya.sword.wallet.service.IUserWalletService;

/**
 * The Class SwordMiningUtils.
 */
public class SwordMiningUtils extends SwordUtils {

	/**
	 * Verify wallet password.
	 *
	 * @param password the password
	 * @return the common result
	 */
	public static CommonResult<?> verifyWalletPassword(String password) {
		if (StringUtils.isEmpty(password)) {
			return CommonResult.fail("密码错误");
		}
		final IUserWalletService walletService = SpringUtils.getBean(IUserWalletService.class);
		return walletService.verifyUserWalletPassword(getUserId(), password);
	}

}
