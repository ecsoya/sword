package com.soyatec.sword.mining.utils;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.utils.SwordUtils;
import com.soyatec.sword.wallet.service.IUserWalletService;

public class SwordMiningUtils extends SwordUtils {

	public static CommonResult<?> verifyWalletPassword(String password) {
		if (StringUtils.isEmpty(password)) {
			return CommonResult.fail("密码错误");
		}
		IUserWalletService walletService = SpringUtils.getBean(IUserWalletService.class);
		return walletService.verifyUserWalletPassword(getUserId(), password);
	}

}
