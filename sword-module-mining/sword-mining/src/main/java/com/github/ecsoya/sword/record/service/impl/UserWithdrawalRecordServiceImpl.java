package com.github.ecsoya.sword.record.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;
import com.github.ecsoya.sword.record.mapper.UserWithdrawalRecordMapper;
import com.github.ecsoya.sword.record.service.IUserWithdrawalRecordService;
import com.github.ecsoya.sword.utils.MathUtils;

/**
 * The Class UserWithdrawalRecordServiceImpl.
 */
@Service
public class UserWithdrawalRecordServiceImpl implements IUserWithdrawalRecordService {

	/** The user withdrawal record mapper. */
	@Autowired
	private UserWithdrawalRecordMapper userWithdrawalRecordMapper;

	/**
	 * Select user withdrawal record by id.
	 *
	 * @param id the id
	 * @return the user withdrawal record
	 */
	@Override
	public UserWithdrawalRecord selectUserWithdrawalRecordById(Long id) {
		return userWithdrawalRecordMapper.selectUserWithdrawalRecordById(id);
	}

	/**
	 * Select user withdrawal record list.
	 *
	 * @param userWithdrawalRecord the user withdrawal record
	 * @return the list
	 */
	@Override
	public List<UserWithdrawalRecord> selectUserWithdrawalRecordList(UserWithdrawalRecord userWithdrawalRecord) {
		return userWithdrawalRecordMapper.selectUserWithdrawalRecordList(userWithdrawalRecord);
	}

	/**
	 * Insert user withdrawal record.
	 *
	 * @param userWithdrawalRecord the user withdrawal record
	 * @return the int
	 */
	@Override
	public int insertUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord) {
		if (userWithdrawalRecord.getId() == null) {
			userWithdrawalRecord.setId(IdWorker.getId());
		}
		if (userWithdrawalRecord.getCreateTime() == null) {
			userWithdrawalRecord.setCreateTime(DateUtils.getNowDate());
		}
		return userWithdrawalRecordMapper.insertUserWithdrawalRecord(userWithdrawalRecord);
	}

	/**
	 * Update user withdrawal record.
	 *
	 * @param userWithdrawalRecord the user withdrawal record
	 * @return the int
	 */
	@Override
	public int updateUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord) {
		userWithdrawalRecord.setUpdateTime(DateUtils.getNowDate());
		return userWithdrawalRecordMapper.updateUserWithdrawalRecord(userWithdrawalRecord);
	}

	/**
	 * Delete user withdrawal record by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserWithdrawalRecordByIds(String ids) {
		return userWithdrawalRecordMapper.deleteUserWithdrawalRecordByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user withdrawal record by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserWithdrawalRecordById(Long id) {
		return userWithdrawalRecordMapper.deleteUserWithdrawalRecordById(id);
	}

	/**
	 * Update user withdrawal record height by tx id.
	 *
	 * @param txId   the tx id
	 * @param height the height
	 * @return the int
	 */
	@Override
	public int updateUserWithdrawalRecordHeightByTxId(String txId, Long height) {
		if (StringUtils.isEmpty(txId) || height == null || height.longValue() < 0) {
			return 0;
		}
		return userWithdrawalRecordMapper.updateUserWithdrawalRecordHeightByTxId(txId, height);
	}

	/**
	 * Select user withdrawal amount by date.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWithdrawalAmountByDate(String symbol, Date start, Date end) {
		return userWithdrawalRecordMapper.selectUserWithdrawalAmountByDate(symbol, start, end);
	}

	/**
	 * Select user withdrawal amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWithdrawalAmount(String symbol) {
		return MathUtils.nullToZero(userWithdrawalRecordMapper.selectUserWithdrawalAmountByDate(symbol, null, null));
	}

	/**
	 * Select user withdrawal fee amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol) {
		return MathUtils.nullToZero(userWithdrawalRecordMapper.selectUserWithdrawalFeeAmount(symbol));
	}
}
