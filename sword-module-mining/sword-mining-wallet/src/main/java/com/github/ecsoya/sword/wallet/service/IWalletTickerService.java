package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;
import java.util.Date;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.wallet.domain.Ticker;

/**
 * The Interface IWalletTickerService.
 */
public interface IWalletTickerService {

	/**
	 * Gets the ticker.
	 *
	 * @param symbol the symbol
	 * @return the ticker
	 */
	public CommonResult<Ticker> getTicker(String symbol);

	/**
	 * Gets the price.
	 *
	 * @param symbol the symbol
	 * @return the price
	 */
	public BigDecimal getPrice(String symbol);

	/**
	 * Gets the ticker from history.
	 *
	 * @param symbol the symbol
	 * @param time   the time
	 * @return the ticker from history
	 */
	public Ticker getTickerFromHistory(String symbol, Date time);

	/**
	 * Update ticker cache.
	 *
	 * @param symbol the symbol
	 * @return the ticker
	 */
	public Ticker updateTickerCache(String symbol);

	/**
	 * Gets the cny usd exchange rate.
	 *
	 * @return the cny usd exchange rate
	 */
	public BigDecimal getCnyUsdExchangeRate();

	/**
	 * Gets the usdt cny price.
	 *
	 * @return the usdt cny price
	 */
	public BigDecimal getUsdtCnyPrice();
}
