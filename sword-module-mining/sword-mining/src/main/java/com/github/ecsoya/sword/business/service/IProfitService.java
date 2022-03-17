package com.github.ecsoya.sword.business.service;

import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.exceptions.TransactionException;

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
