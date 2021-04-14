package com.soyatec.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.utils.MathUtils;
import com.soyatec.sword.wallet.domain.UserWalletRecord;
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

	/**
	 * 查询用户钱包记录
	 *
	 * @param id 用户钱包记录ID
	 * @return 用户钱包记录
	 */
	@Override
	public UserWalletRecord selectUserWalletRecordById(Long id) {
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
		return userWalletRecordMapper.updateUserWalletRecord(userWalletRecord);
	}

	/**
	 * 删除用户钱包记录对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletRecordByIds(String ids) {
		return userWalletRecordMapper.deleteUserWalletRecordByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户钱包记录信息
	 *
	 * @param id 用户钱包记录ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWalletRecordById(Long id) {
		return userWalletRecordMapper.deleteUserWalletRecordById(id);
	}

	@Override
	public BigDecimal selectUserWalletRecordAmount(UserWalletRecord record) {
		return MathUtils.nullToZero(userWalletRecordMapper.selectUserWalletRecordAmount(record));
	}
}
