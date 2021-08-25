package com.soyatec.sword.wallet.service;

import java.math.BigDecimal;
import java.util.Date;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.wallet.domain.Ticker;

public interface IWalletTickerService {

	public CommonResult<Ticker> getTicker(String symbol);

	/**
	 * 获取最新交易价格
	 */
	public BigDecimal getPrice(String symbol);

	public Ticker getTickerFromHistory(String symbol, Date time);

	public Ticker updateTickerCache(String symbol);

	/**
	 * 获取人民币美元汇率
	 */
	public BigDecimal getCnyUsdExchangeRate();

	/**
	 * 获取人民币兑换USDT的价格
	 */
	public BigDecimal getUsdtCnyPrice();
}
