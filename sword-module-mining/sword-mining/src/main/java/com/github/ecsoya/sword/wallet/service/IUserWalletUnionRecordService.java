package com.github.ecsoya.sword.wallet.service;

import java.util.Date;
import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWalletUnionRecord;

/**
 * The Interface IUserWalletUnionRecordService.
 */
public interface IUserWalletUnionRecordService {

	/**
	 * Select wallet record list by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @param kind   the kind
	 * @return the list
	 */
	public List<UserWalletUnionRecord> selectWalletRecordListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind);

	/**
	 * Select wallet order list by user id.
	 *
	 * @param userId the user id
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @param kind   the kind
	 * @return the list
	 */
	public List<UserWalletUnionRecord> selectWalletOrderListByUserId(Long userId, String symbol, Date start, Date end,
			Integer kind);
}
