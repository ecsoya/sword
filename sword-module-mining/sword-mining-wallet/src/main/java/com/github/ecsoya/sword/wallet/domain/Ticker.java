package com.github.ecsoya.sword.wallet.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;

public class Ticker {

	private String symbol;

	private Date date;

	/**
	 * 24H最高价
	 */
	private BigDecimal high;

	/**
	 * 24H涨跌幅
	 */
	private BigDecimal rate;

	/**
	 * 24H最低价
	 */
	private BigDecimal low;

	/**
	 * 价格
	 */
	private BigDecimal price;

	/**
	 * 卖房最低价
	 */
	private BigDecimal ask;

	/**
	 * 买房最高价
	 */
	private BigDecimal bid;

	public BigDecimal getHigh() {
		return high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getLow() {
		return low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public void setAsk(BigDecimal ask) {
		this.ask = ask;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public void setBid(BigDecimal bid) {
		this.bid = bid;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return String.format("Ticker [symbol=%s, date=%s, high=%s, rate=%s, low=%s, price=%s, ask=%s, bid=%s]", symbol,
				DateUtils.toDefault(date), high, rate, low, price, ask, bid);
	}

}
