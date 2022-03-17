package com.github.ecsoya.sword.report.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;

public class DateAmount {

	private Date date;

	private BigDecimal amount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "DateAmount [date=" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date) + ", amount=" + amount + "]";
	}

}
