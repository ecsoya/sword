package com.github.ecsoya.sword.report.domain;

import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;

public class DateCount {

	private Date date;

	private Long count;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "DateCount [date=" + DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, date) + ", count=" + count + "]";
	}

}
