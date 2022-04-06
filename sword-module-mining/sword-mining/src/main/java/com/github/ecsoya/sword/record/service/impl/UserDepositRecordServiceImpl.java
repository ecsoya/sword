package com.github.ecsoya.sword.record.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.record.domain.UserDepositRecord;
import com.github.ecsoya.sword.record.mapper.UserDepositRecordMapper;
import com.github.ecsoya.sword.record.service.IUserDepositRecordService;
import com.github.ecsoya.sword.utils.MathUtils;

/**
 * The Class UserDepositRecordServiceImpl.
 */
@Service
public class UserDepositRecordServiceImpl implements IUserDepositRecordService {

	/** The user deposit record mapper. */
	@Autowired
	private UserDepositRecordMapper userDepositRecordMapper;

	/**
	 * Select user deposit record by id.
	 *
	 * @param id the id
	 * @return the user deposit record
	 */
	@Override
	public UserDepositRecord selectUserDepositRecordById(Long id) {
		return userDepositRecordMapper.selectUserDepositRecordById(id);
	}

	/**
	 * Select user deposit record list.
	 *
	 * @param userDepositRecord the user deposit record
	 * @return the list
	 */
	@Override
	public List<UserDepositRecord> selectUserDepositRecordList(UserDepositRecord userDepositRecord) {
		return userDepositRecordMapper.selectUserDepositRecordList(userDepositRecord);
	}

	/**
	 * Insert user deposit record.
	 *
	 * @param userDepositRecord the user deposit record
	 * @return the int
	 */
	@Override
	public int insertUserDepositRecord(UserDepositRecord userDepositRecord) {
		if (userDepositRecord.getId() == null) {
			userDepositRecord.setId(IdWorker.getId());
		}
		if (userDepositRecord.getCreateTime() == null) {
			userDepositRecord.setCreateTime(DateUtils.getNowDate());
		}
		return userDepositRecordMapper.insertUserDepositRecord(userDepositRecord);
	}

	/**
	 * Update user deposit record.
	 *
	 * @param userDepositRecord the user deposit record
	 * @return the int
	 */
	@Override
	public int updateUserDepositRecord(UserDepositRecord userDepositRecord) {
		userDepositRecord.setUpdateTime(DateUtils.getNowDate());
		return userDepositRecordMapper.updateUserDepositRecord(userDepositRecord);
	}

	/**
	 * Delete user deposit record by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserDepositRecordByIds(String ids) {
		return userDepositRecordMapper.deleteUserDepositRecordByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user deposit record by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserDepositRecordById(Long id) {
		return userDepositRecordMapper.deleteUserDepositRecordById(id);
	}

	/**
	 * Select user deposit record by tx id.
	 *
	 * @param txId the tx id
	 * @return the user deposit record
	 */
	@Override
	public UserDepositRecord selectUserDepositRecordByTxId(String txId) {
		if (StringUtils.isEmpty(txId)) {
			return null;
		}
		return userDepositRecordMapper.selectUserDepositRecordByTxId(txId);
	}

	/**
	 * Select user deposit amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserDepositAmount(String symbol) {
		return MathUtils.nullToZero(userDepositRecordMapper.selectUserDepositAmount(symbol));
	}
}
