package com.github.ecsoya.sword.business.service;

import java.util.Date;

import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.exceptions.TransactionException;

/**
 * The Interface IProfitService.
 */
public interface IProfitService {

	/**
	 * Dispatch profit.
	 *
	 * @param date the date
	 * @return the int
	 * @throws TransactionException the transaction exception
	 */
	int dispatchProfit(Date date) throws TransactionException;

	/**
	 * Checks if is month end.
	 *
	 * @param date the date
	 * @return true, if is month end
	 */
	default boolean isMonthEnd(Date date) {
		if (date == null) {
			return false;
		}
		Date monthEnd = DateUtils.getMonthEndOf(date);
		return monthEnd.equals(DateUtils.getEndOf(date));
	}

}
