package com.soyatec.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.wallet.domain.UserWalletRecord;
import com.soyatec.sword.wallet.mapper.UserWalletRecord0Mapper;
import com.soyatec.sword.wallet.mapper.UserWalletRecord1Mapper;
import com.soyatec.sword.wallet.mapper.UserWalletRecord2Mapper;
import com.soyatec.sword.wallet.mapper.UserWalletRecordMapper;
import com.soyatec.sword.wallet.service.IUserWalletRecordService;

/**
 * 用户钱包记录Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserWalletRecordServiceImpl implements IUserWalletRecordService {
	@Autowired
	private UserWalletRecordMapper userWalletRecordMapper;
	@Autowired
	private UserWalletRecord0Mapper userWalletRecordMapper0;
	@Autowired
	private UserWalletRecord1Mapper userWalletRecordMapper1;
	@Autowired
	private UserWalletRecord2Mapper userWalletRecordMapper2;

	/**
	 * 查询用户钱包记录
	 *
	 * @param id 用户钱包记录ID
	 * @return 用户钱包记录
	 */
	@Override
	public UserWalletRecord selectUserWalletRecordById(Long id, Integer type) {
		if (UserWalletRecord.KIND_AMOUNT.equals(type)) {
			return userWalletRecordMapper0.selectUserWalletRecordById(id);
		} else if (UserWalletRecord.KIND_FROZEN.equals(type)) {
			return userWalletRecordMapper1.selectUserWalletRecordById(id);
		} else if (UserWalletRecord.KIND_LOCKED.equals(type)) {
			return userWalletRecordMapper2.selectUserWalletRecordById(id);
		}
		return userWalletRecordMapper.selectUserWalletRecordById(id);
	}

	/**
	 * 查询用户钱包记录列表
	 *
	 * @param userWalletRecord 用户钱包记录
	 * @return 用户钱包记录
	 */
	@Override
	public List<UserWalletRecord> selectUserWalletRecordList(UserWalletRecord userWalletRecord) {
		Integer type = userWalletRecord.getType();
		if (UserWalletRecord.KIND_AMOUNT.equals(type)) {
			return userWalletRecordMapper0.selectUserWalletRecordList(userWalletRecord);
		} else if (UserWalletRecord.KIND_FROZEN.equals(type)) {
			return userWalletRecordMapper1.selectUserWalletRecordList(userWalletRecord);
		} else if (UserWalletRecord.KIND_LOCKED.equals(type)) {
			return userWalletRecordMapper2.selectUserWalletRecordList(userWalletRecord);
		}
		return userWalletRecordMapper.selectUserWalletRecordList(userWalletRecord);
	}

	/**
	 * 新增用户钱包记录
	 *
	 * @param userWalletRecord 用户钱包记录
	 * @return 结果
	 */
	@Override
	public int insertUserWalletRecord(UserWalletRecord userWalletRecord) {
		if (userWalletRecord.getId() == null) {
			userWalletRecord.setId(IdWorker.getId());
		}
		if (userWalletRecord.getCreateTime() == null) {
			userWalletRecord.setCreateTime(DateUtils.getNowDate());
		}
		Integer type = userWalletRecord.getType();
		if (UserWalletRecord.KIND_AMOUNT.equals(type)) {
			return userWalletRecordMapper0.insertUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_FROZEN.equals(type)) {
			return userWalletRecordMapper1.insertUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_LOCKED.equals(type)) {
			return userWalletRecordMapper2.insertUserWalletRecord(userWalletRecord);
		}
		return userWalletRecordMapper.insertUserWalletRecord(userWalletRecord);
	}

	/**
	 * 修改用户钱包记录
	 *
	 * @param userWalletRecord 用户钱包记录
	 * @return 结果
	 */
	@Override
	public int updateUserWalletRecord(UserWalletRecord userWalletRecord) {
		userWalletRecord.setUpdateTime(DateUtils.getNowDate());
		Integer type = userWalletRecord.getType();
		if (UserWalletRecord.KIND_AMOUNT.equals(type)) {
			return userWalletRecordMapper0.updateUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_FROZEN.equals(type)) {
			return userWalletRecordMapper1.updateUserWalletRecord(userWalletRecord);
		} else if (UserWalletRecord.KIND_LOCKED.equals(type)) {
			return userWalletRecordMapper2.updateUserWalletRecord(userWalletRecord);
		}
		return userWalletRecordMapper.updateUserWalletRecord(userWalletRecord);
	}

	@Override
	public BigDecimal selectUserWalletRecordAmount(UserWalletRecord record) {
		Integer type = record.getType();
		if (UserWalletRecord.KIND_AMOUNT.equals(type)) {
			return userWalletRecordMapper0.selectUserWalletRecordAmount(record);
		} else if (UserWalletRecord.KIND_FROZEN.equals(type)) {
			return userWalletRecordMapper1.selectUserWalletRecordAmount(record);
		} else if (UserWalletRecord.KIND_LOCKED.equals(type)) {
			return userWalletRecordMapper2.selectUserWalletRecordAmount(record);
		}
		return MathUtils.nullToZero(userWalletRecordMapper.selectUserWalletRecordAmount(record));
	}
}
