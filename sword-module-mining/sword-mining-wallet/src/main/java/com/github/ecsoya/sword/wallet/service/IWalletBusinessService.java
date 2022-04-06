package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.wallet.domain.Address;
import com.github.ecsoya.sword.wallet.domain.WithdrawalResponse;

/**
 * The Interface IWalletBusinessService.
 */
public interface IWalletBusinessService {

	/**
	 * Gets the deposit address.
	 *
	 * @param symbol the symbol
	 * @param chain  the chain
	 * @return the deposit address
	 */
	CommonResult<Address> getDepositAddress(String symbol, String chain);

	/**
	 * Check withdrawal address.
	 *
	 * @param symbol  the symbol
	 * @param chain   the chain
	 * @param address the address
	 * @return the common result
	 */
	CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address);

	/**
	 * Withdrawal.
	 *
	 * @param orderNo the order no
	 * @param symbol  the symbol
	 * @param chain   the chain
	 * @param address the address
	 * @param memo    the memo
	 * @param amount  the amount
	 * @param auto    the auto
	 * @return the common result
	 */
	CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount, boolean auto);

}
