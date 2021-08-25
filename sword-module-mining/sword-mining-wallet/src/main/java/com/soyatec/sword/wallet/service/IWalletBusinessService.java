package com.soyatec.sword.wallet.service;

import java.math.BigDecimal;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.WithdrawalResponse;

/**
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public interface IWalletBusinessService {

	/**
	 * 创建充值地址
	 */
	CommonResult<Address> getDepositAddress(String symbol, String chain);

	/**
	 * 检测提币地址是否合法
	 * 
	 */
	CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address);

	/**
	 * 提币请求
	 */
	CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount);

}
