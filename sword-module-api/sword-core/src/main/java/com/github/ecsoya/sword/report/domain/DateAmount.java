package com.github.ecsoya.sword.report.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;

/**
 * The Class DateAmount.
 */
public class DateAmount {

	/** The date. */
	private Date date;

	/** The amount. */
	private BigDecimal amount;

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
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "DateAmount [date=" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date) + ", amount=" + amount + "]";
	}

}
