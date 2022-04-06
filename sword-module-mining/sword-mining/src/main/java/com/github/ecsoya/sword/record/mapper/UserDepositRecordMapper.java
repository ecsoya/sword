package com.github.ecsoya.sword.record.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.record.domain.UserDepositRecord;

/**
 * The Interface UserDepositRecordMapper.
 */
public interface UserDepositRecordMapper {

	/**
	 * Select user deposit record by id.
	 *
	 * @param id the id
	 * @return the user deposit record
	 */
	public UserDepositRecord selectUserDepositRecordById(Long id);

	/**
	 * Select user deposit record list.
	 *
	 * @param userDepositRecord the user deposit record
	 * @return the list
	 */
	public List<UserDepositRecord> selectUserDepositRecordList(UserDepositRecord userDepositRecord);

	/**
	 * Insert user deposit record.
	 *
	 * @param userDepositRecord the user deposit record
	 * @return the int
	 */
	public int insertUserDepositRecord(UserDepositRecord userDepositRecord);

	/**
	 * Update user deposit record.
	 *
	 * @param userDepositRecord the user deposit record
	 * @return the int
	 */
	public int updateUserDepositRecord(UserDepositRecord userDepositRecord);

	/**
	 * Delete user deposit record by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserDepositRecordById(Long id);

	/**
	 * Delete user deposit record by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserDepositRecordByIds(String[] ids);

	/**
	 * Select user deposit record by tx id.
	 *
	 * @param txId the tx id
	 * @return the user deposit record
	 */
	public UserDepositRecord selectUserDepositRecordByTxId(String txId);

	/**
	 * Select user deposit amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserDepositAmount(String symbol);
}
