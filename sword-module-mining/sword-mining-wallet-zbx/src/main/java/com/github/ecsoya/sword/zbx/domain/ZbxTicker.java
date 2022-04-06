package com.github.ecsoya.sword.zbx.domain;

import java.math.BigDecimal;

import com.github.ecsoya.sword.wallet.domain.Ticker;

/**
 * The Class ZbxTicker.
 */
public class ZbxTicker extends Ticker {

	/** The money vol. */
	private BigDecimal moneyVol;

	/** The month rate. */
	private BigDecimal monthRate;

	/** The coin vol. */
	private BigDecimal coinVol;

	/** The week rate. */
	private BigDecimal weekRate;

	/**
	 * Gets the 成交额.
	 *
	 * @return the 成交额
	 */
	public BigDecimal getMoneyVol() {
		return moneyVol;
	}

	/**
	 * Sets the 成交额.
	 *
	 * @param moneyVol the new 成交额
	 */
	public void setMoneyVol(BigDecimal moneyVol) {
		this.moneyVol = moneyVol;
	}

	/**
	 * Gets the month rate.
	 *
	 * @return the month rate
	 */
	public BigDecimal getMonthRate() {
		return monthRate;
	}

	/**
	 * Sets the month rate.
	 *
	 * @param monthRate the new month rate
	 */
	public void setMonthRate(BigDecimal monthRate) {
		this.monthRate = monthRate;
	}

	/**
	 * Gets the 成交量.
	 *
	 * @return the 成交量
	 */
	public BigDecimal getCoinVol() {
		return coinVol;
	}

	/**
	 * Sets the 成交量.
	 *
	 * @param coinVol the new 成交量
	 */
	public void setCoinVol(BigDecimal coinVol) {
		this.coinVol = coinVol;
	}

	/**
	 * Gets the week rate.
	 *
	 * @return the week rate
	 */
	public BigDecimal getWeekRate() {
		return weekRate;
	}

	/**
	 * Sets the week rate.
	 *
	 * @param weekRate the new week rate
	 */
	public void setWeekRate(BigDecimal weekRate) {
		this.weekRate = weekRate;
	}

}
