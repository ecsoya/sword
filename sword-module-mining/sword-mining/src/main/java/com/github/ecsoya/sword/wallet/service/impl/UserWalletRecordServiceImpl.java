package com.github.ecsoya.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.utils.MathUtils;
import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;
import com.github.ecsoya.sword.wallet.mapper.UserWalletRecord0Mapper;
import com.github.ecsoya.sword.wallet.mapper.UserWalletRecord1Mapper;
import com.github.ecsoya.sword.wallet.mapper.UserWalletRecord2Mapper;
import com.github.ecsoya.sword.wallet.mapper.UserWalletRecordMapper;
import com.github.ecsoya.sword.wallet.service.IUserWalletRecordService;

/**
 * The Class UserWalletRecordServiceImpl.
 */
@Service
public class UserWalletRecordServiceImpl implements IUserWalletRecordService {

	/** The user wallet record mapper. */
	@Autowired
	private UserWalletRecordMapper userWalletRecordMapper;

	/** The user wallet record mapper 0. */
	@Autowired
	private UserWalletRecord0Mapper userWalletRecordMapper0;

	/** The user wallet record mapper 1. */
	@Autowired
	private UserWalletRecord1Mapper userWalletRecordMapper1;

	/** The user wallet record mapper 2. */
	@Autowired
	private UserWalletRecord2Mapper userWalletRecordMapper2;

	/**
	 * Select user wallet record by id.
	 *
	 * @param id   the id
	 * @param kind the kind
	 * @return the user wallet record
	 */
	@Override
	public UserWalletRecord selectUserWalletRecordById(Long id, Integer kind) {
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			return userWalletRecordMapper0.selectUserWalletRecordById(id);
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			return userWalletRecordMapper1.selectUserWalletRecordById(id);
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			return userWalletRecordMapper2.selectUserWalletRecordById(id);
		}
		return userWalletRecordMapper.selectUserWalletRecordById(id);
	}

	/**
	 * Select user wallet record list.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return the list
	 */
	@Override
	public List<UserWalletRecord> selectUserWalletRecordList(UserWalletRecord userWalletRecord) {
		Integer kind = userWalletRecord.getKind();
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			return userWalletRecordMapper0.selectUserWalletRecordList(userWalletRecord);
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			return userWalletRecordMapper1.selectUserWalletRecordList(userWalletRecord);
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			return userWalletRecordMapper2.selectUserWalletRecordList(userWalletRecord);
		}
		return userWalletRecordMapper.selectUserWalletRecordList(userWalletRecord);
	}

	/**
	 * Insert user wallet record.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return the int
	 */
	@Override
	public int insertUserWalletRecord(UserWalletRecord userWalletRecord) {
		if (userWalletRecord.getId() == null) {
			userWalletRecord.setId(IdWorker.getId());
		}
		if (userWalletRecord.getCreateTime() == null) {
			userWalletRecord.setCreateTime(DateUtils.getNowDate());
		}
		Integer kind = userWalletRecord.getKind();
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			return userWalletRecordMapper0.insertUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			return userWalletRecordMapper1.insertUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			return userWalletRecordMapper2.insertUserWalletRecord(userWalletRecord);
		}
		return userWalletRecordMapper.insertUserWalletRecord(userWalletRecord);
	}

	/**
	 * Update user wallet record.
	 *
	 * @param userWalletRecord the user wallet record
	 * @return the int
	 */
	@Override
	public int updateUserWalletRecord(UserWalletRecord userWalletRecord) {
		userWalletRecord.setUpdateTime(DateUtils.getNowDate());
		Integer kind = userWalletRecord.getKind();
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			return userWalletRecordMapper0.updateUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			return userWalletRecordMapper1.updateUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			return userWalletRecordMapper2.updateUserWalletRecord(userWalletRecord);
		}
		return userWalletRecordMapper.updateUserWalletRecord(userWalletRecord);
	}

	/**
	 * Select user wallet record amount.
	 *
	 * @param record the record
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserWalletRecordAmount(UserWalletRecord record) {
		Integer kind = record.getKind();
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			return MathUtils.nullToZero(userWalletRecordMapper0.selectUserWalletRecordAmount(record));
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			return MathUtils.nullToZero(userWalletRecordMapper1.selectUserWalletRecordAmount(record));
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			return MathUtils.nullToZero(userWalletRecordMapper2.selectUserWalletRecordAmount(record));
		}
		return MathUtils.nullToZero(userWalletRecordMapper.selectUserWalletRecordAmount(record));
	}

	/**
	 * Select user wallet record one.
	 *
	 * @param query the query
	 * @return the user wallet record
	 */
	@Override
	public UserWalletRecord selectUserWalletRecordOne(UserWalletRecord query) {
		if (query == null) {
			return null;
		}
		Integer kind = query.getKind();
		if (UserWalletRecord.KIND_AMOUNT.equals(kind)) {
			return userWalletRecordMapper0.selectUserWalletRecordOne(query);
		} else if (UserWalletRecord.KIND_FROZEN.equals(kind)) {
			return userWalletRecordMapper1.selectUserWalletRecordOne(query);
		} else if (UserWalletRecord.KIND_LOCKED.equals(kind)) {
			return userWalletRecordMapper2.selectUserWalletRecordOne(query);
		}
		return null;
	}
}
