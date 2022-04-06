package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;

/**
 * The Interface IWalletService.
 */
public interface IWalletService extends IWalletBusinessService, IWalletTickerService {

	/**
	 * Gets the deposit address value.
	 *
	 * @param symbol the symbol
	 * @param chain  the chain
	 * @return the deposit address value
	 */
	public String getDepositAddressValue(String symbol, String chain);

	/**
	 * Exchange to usdt.
	 *
	 * @param symbol the symbol
	 * @param value  the value
	 * @return the big decimal
	 */
	public BigDecimal exchangeToUsdt(String symbol, BigDecimal value);

	/**
	 * Exchange from usdt.
	 *
	 * @param symbol the symbol
	 * @param value  the value
	 * @return the big decimal
	 */
	public BigDecimal exchangeFromUsdt(String symbol, BigDecimal value);

	/**
	 * Checks if is local.
	 *
	 * @return true, if is local
	 */
	public boolean isLocal();
}
