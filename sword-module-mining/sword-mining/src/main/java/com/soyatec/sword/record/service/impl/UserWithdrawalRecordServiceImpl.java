package com.soyatec.sword.record.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.record.domain.UserWithdrawalRecord;
import com.soyatec.sword.record.mapper.UserWithdrawalRecordMapper;
import com.soyatec.sword.record.service.IUserWithdrawalRecordService;

/**
 * 用户提现Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserWithdrawalRecordServiceImpl implements IUserWithdrawalRecordService {
	@Autowired
	private UserWithdrawalRecordMapper userWithdrawalRecordMapper;

	/**
	 * 查询用户提现
	 *
	 * @param id 用户提现ID
	 * @return 用户提现
	 */
	@Override
	public UserWithdrawalRecord selectUserWithdrawalRecordById(Long id) {
		return userWithdrawalRecordMapper.selectUserWithdrawalRecordById(id);
	}

	/**
	 * 查询用户提现列表
	 *
	 * @param userWithdrawalRecord 用户提现
	 * @return 用户提现
	 */
	@Override
	public List<UserWithdrawalRecord> selectUserWithdrawalRecordList(UserWithdrawalRecord userWithdrawalRecord) {
		return userWithdrawalRecordMapper.selectUserWithdrawalRecordList(userWithdrawalRecord);
	}

	/**
	 * 新增用户提现
	 *
	 * @param userWithdrawalRecord 用户提现
	 * @return 结果
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
	 * 修改用户提现
	 *
	 * @param userWithdrawalRecord 用户提现
	 * @return 结果
	 */
	@Override
	public int updateUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord) {
		userWithdrawalRecord.setUpdateTime(DateUtils.getNowDate());
		return userWithdrawalRecordMapper.updateUserWithdrawalRecord(userWithdrawalRecord);
	}

	/**
	 * 删除用户提现对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWithdrawalRecordByIds(String ids) {
		return userWithdrawalRecordMapper.deleteUserWithdrawalRecordByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户提现信息
	 *
	 * @param id 用户提现ID
	 * @return 结果
	 */
	@Override
	public int deleteUserWithdrawalRecordById(Long id) {
		return userWithdrawalRecordMapper.deleteUserWithdrawalRecordById(id);
	}

	@Override
	public int updateUserWithdrawalRecordHeightByTxId(String txId, Long height) {
		if (StringUtils.isEmpty(txId) || height == null || height.longValue() < 0) {
			return 0;
		}
		return userWithdrawalRecordMapper.updateUserWithdrawalRecordHeightByTxId(txId, height);
	}

	@Override
	public BigDecimal selectUserWithdrawalAmountByDate(String symbol, Date start, Date end) {
		return userWithdrawalRecordMapper.selectUserWithdrawalAmountByDate(symbol, start, end);
	}
}
