package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;

/**
 * The Interface IUserWalletRecordService.
 */
public interface IUserWalletRecordService {

	/**
	 * Select user wallet record by id.
	 *
	 * @param id   the id
	 * @param kind the kind
	 * @return the user wallet record
	 */
	public UserWalletRecord selectUserWalletRecordById(Long id, Integer kind);

	/**
	 * Select user wallet record list.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return the list
	 */
	public List<UserWalletRecord> selectUserWalletRecordList(UserWalletRecord userWalletRecord);

	/**
	 * Insert user wallet record.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return the int
	 */
	public int insertUserWalletRecord(UserWalletRecord userWalletRecord);

	/**
	 * Update user wallet record.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return the int
	 */
	public int updateUserWalletRecord(UserWalletRecord userWalletRecord);

	/**
	 * Select user wallet record amount.
	 *
	 * @param record the record
	 * @return the big decimal
	 */
	public BigDecimal selectUserWalletRecordAmount(UserWalletRecord record);

	/**
	 * Select user wallet record one.
	 *
	 * @param query the query
	 * @return the user wallet record
	 */
	public UserWalletRecord selectUserWalletRecordOne(UserWalletRecord query);
}
