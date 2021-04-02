package com.soyatec.sword.zbx.domain;

import java.math.BigDecimal;

import com.soyatec.sword.wallet.domain.Ticker;

/**
 * 
 * 聚合行情
 * 
 * @author ecsoya
 *
 */
public class ZbxTicker extends Ticker {

	/**
	 * 成交额
	 */
	private BigDecimal moneyVol;

	private BigDecimal monthRate;

	/**
	 * 成交量
	 */
	private BigDecimal coinVol;

	private BigDecimal weekRate;

	public BigDecimal getMoneyVol() {
		return moneyVol;
	}

	public void setMoneyVol(BigDecimal moneyVol) {
		this.moneyVol = moneyVol;
	}

	public BigDecimal getMonthRate() {
		return monthRate;
	}

	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}

	public BigDecimal getCoinVol() {
		return coinVol;
	}

	public void setCoinVol(BigDecimal coinVol) {
		this.coinVol = coinVol;
	}

	public BigDecimal getWeekRate() {
		return weekRate;
	}

	public void setWeekRate(BigDecimal weekRate) {
		this.weekRate = weekRate;
	}

}
