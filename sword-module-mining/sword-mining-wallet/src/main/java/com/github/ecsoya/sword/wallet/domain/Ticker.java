package com.github.ecsoya.sword.wallet.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;

/**
 * The Class Ticker.
 */
public class Ticker {

	/** The symbol. */
	private String symbol;

	/** The date. */
	private Date date;

	/** The high. */
	private BigDecimal high;

	/** The rate. */
	private BigDecimal rate;

	/** The low. */
	private BigDecimal low;

	/** The price. */
	private BigDecimal price;

	/** The ask. */
	private BigDecimal ask;

	/** The bid. */
	private BigDecimal bid;

	/**
	 * Gets the 24H最高价.
	 *
	 * @return the 24H最高价
	 */
	public BigDecimal getHigh() {
		return high;
	}

	/**
	 * Sets the 24H最高价.
	 *
	 * @param high the new 24H最高价
	 */
	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	/**
	 * Gets the 24H涨跌幅.
	 *
	 * @return the 24H涨跌幅
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * Sets the 24H涨跌幅.
	 *
	 * @param rate the new 24H涨跌幅
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * Gets the 24H最低价.
	 *
	 * @return the 24H最低价
	 */
	public BigDecimal getLow() {
		return low;
	}

	/**
	 * Sets the 24H最低价.
	 *
	 * @param low the new 24H最低价
	 */
	public void setLow(BigDecimal low) {
		this.low = low;
	}

	/**
	 * Gets the 价格.
	 *
	 * @return the 价格
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the 价格.
	 *
	 * @param price the new 价格
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Gets the 卖房最低价.
	 *
	 * @return the 卖房最低价
	 */
	public BigDecimal getAsk() {
		return ask;
	}

	/**
	 * Sets the 卖房最低价.
	 *
	 * @param ask the new 卖房最低价
	 */
	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}

	/**
	 * Gets the 买房最高价.
	 *
	 * @return the 买房最高价
	 */
	public BigDecimal getBid() {
		return bid;
	}

	/**
	 * Sets the 买房最高价.
	 *
	 * @param bid the new 买房最高价
	 */
	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return String.format("Ticker [symbol=%s, date=%s, high=%s, rate=%s, low=%s, price=%s, ask=%s, bid=%s]", symbol,
				DateUtils.toDefault(date), high, rate, low, price, ask, bid);
	}

}
