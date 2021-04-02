package com.soyatec.sword.wallet.service;

import java.math.BigDecimal;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.domain.WithdrawalResponse;

/**
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public interface IWalletDelegateService {

	/**
	 * 获取最新交易价格详情
	 */
	CommonResult<Ticker> getTicker(String symbol);

	/**
	 * 创建充值地址
	 */
	CommonResult<Address> getDepositAddress(String symbol, String chain);

	/**
	 * 提币请求
	 */
	CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount);

}
