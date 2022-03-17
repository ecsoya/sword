package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;

public interface IWalletService extends IWalletBusinessService, IWalletTickerService {

	/**
	 * 创建充值地址，返回充值地址
	 */
	public String getDepositAddressValue(String symbol, String chain);

	public BigDecimal exchangeToUsdt(String symbol, BigDecimal value);

	public BigDecimal exchangeFromUsdt(String symbol, BigDecimal value);

	public boolean isLocal();
}
