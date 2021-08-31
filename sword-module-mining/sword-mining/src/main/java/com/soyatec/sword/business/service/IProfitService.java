package com.soyatec.sword.business.service;

import java.util.Date;

import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.exceptions.TransactionException;

public interface IProfitService {

	int dispatchProfit(Date date) throws TransactionException;

	default boolean isMonthEnd(Date date) {
		if (date == null) {
			return false;
		}
		Date monthEnd = DateUtils.getMonthEndOf(date);
		return monthEnd.equals(DateUtils.getEndOf(date));
	}

}
