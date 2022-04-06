package com.github.ecsoya.sword.record.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;

/**
 * The Interface IUserWithdrawalRecordService.
 */
public interface IUserWithdrawalRecordService {

	/**
	 * Select user withdrawal record by id.
	 *
	 * @param id the id
	 * @return the user withdrawal record
	 */
	public UserWithdrawalRecord selectUserWithdrawalRecordById(Long id);

	/**
	 * Select user withdrawal record list.
	 *
	 * @param userWithdrawalRecord the user withdrawal record
	 * @return the list
	 */
	public List<UserWithdrawalRecord> selectUserWithdrawalRecordList(UserWithdrawalRecord userWithdrawalRecord);

	/**
	 * Insert user withdrawal record.
	 *
	 * @param userWithdrawalRecord the user withdrawal record
	 * @return the int
	 */
	public int insertUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord);

	/**
	 * Update user withdrawal record.
	 *
	 * @param userWithdrawalRecord the user withdrawal record
	 * @return the int
	 */
	public int updateUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord);

	/**
	 * Delete user withdrawal record by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWithdrawalRecordByIds(String ids);

	/**
	 * Delete user withdrawal record by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWithdrawalRecordById(Long id);

	/**
	 * Update user withdrawal record height by tx id.
	 *
	 * @param txId   the tx id
	 * @param height the height
	 * @return the int
	 */
	public int updateUserWithdrawalRecordHeightByTxId(String txId, Long height);

	/**
	 * Select user withdrawal amount by date.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmountByDate(String symbol, Date start, Date end);

	/**
	 * Select user withdrawal amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmount(String symbol);

	/**
	 * Select user withdrawal fee amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);

}
