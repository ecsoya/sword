package com.github.ecsoya.sword.report.domain;

import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;

/**
 * The Class DateCount.
 */
public class DateCount {

	/** The date. */
	private Date date;

	/** The count. */
	private Long count;

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
	 * Gets the count.
	 *
	 * @return the count
	 */
	public Long getCount() {
		return count;
	}

	/**
	 * Sets the count.
	 *
	 * @param count the new count
	 */
	public void setCount(Long count) {
		this.count = count;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "DateCount [date=" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date) + ", count=" + count + "]";
	}

}
