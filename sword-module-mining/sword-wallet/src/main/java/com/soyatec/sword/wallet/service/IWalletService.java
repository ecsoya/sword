package com.soyatec.sword.wallet.service;

import java.math.BigDecimal;
import java.util.Date;

import com.soyatec.sword.wallet.domain.Ticker;

public interface IWalletService extends IWalletDelegateService {

	/**
	 * 获取最新交易价格
	 */
	public BigDecimal getPrice(String symbol);

	/**
	 * 创建充值地址，返回充值地址
	 */
	public String getDepositAddressValue(String symbol, String chain);

	public Ticker getTickerFromHistory(String symbol, Date time);

	public Ticker updateTickerCache(String symbol);

	public BigDecimal exchangeToUsdt(String symbol, BigDecimal value);

	public BigDecimal exchangeFromUsdt(String symbol, BigDecimal value);

}
