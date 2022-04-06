package com.github.ecsoya.sword.record.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;

/**
 * The Interface UserWithdrawalRecordMapper.
 */
public interface UserWithdrawalRecordMapper {

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
	 * Delete user withdrawal record by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWithdrawalRecordById(Long id);

	/**
	 * Delete user withdrawal record by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWithdrawalRecordByIds(String[] ids);

	/**
	 * Update user withdrawal record height by tx id.
	 *
	 * @param txId   the tx id
	 * @param height the height
	 * @return the int
	 */
	public int updateUserWithdrawalRecordHeightByTxId(@Param("txId") String txId, @Param("height") Long height);

	/**
	 * Select user withdrawal amount by date.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmountByDate(@Param("symbol") String symbol, @Param("start") Date start,
			@Param("end") Date end);

	/**
	 * Select user withdrawal fee amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);
}
