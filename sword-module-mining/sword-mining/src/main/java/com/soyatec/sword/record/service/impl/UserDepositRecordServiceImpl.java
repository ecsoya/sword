package com.soyatec.sword.record.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.record.domain.UserDepositRecord;
import com.soyatec.sword.record.mapper.UserDepositRecordMapper;
import com.soyatec.sword.record.service.IUserDepositRecordService;

/**
 * 用户充值Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserDepositRecordServiceImpl implements IUserDepositRecordService {
	@Autowired
	private UserDepositRecordMapper userDepositRecordMapper;

	/**
	 * 查询用户充值
	 * 
	 * @param id 用户充值ID
	 * @return 用户充值
	 */
	@Override
	public UserDepositRecord selectUserDepositRecordById(Long id) {
		return userDepositRecordMapper.selectUserDepositRecordById(id);
	}

	/**
	 * 查询用户充值列表
	 * 
	 * @param userDepositRecord 用户充值
	 * @return 用户充值
	 */
	@Override
	public List<UserDepositRecord> selectUserDepositRecordList(UserDepositRecord userDepositRecord) {
		return userDepositRecordMapper.selectUserDepositRecordList(userDepositRecord);
	}

	/**
	 * 新增用户充值
	 * 
	 * @param userDepositRecord 用户充值
	 * @return 结果
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
	 * 修改用户充值
	 * 
	 * @param userDepositRecord 用户充值
	 * @return 结果
	 */
	@Override
	public int updateUserDepositRecord(UserDepositRecord userDepositRecord) {
		userDepositRecord.setUpdateTime(DateUtils.getNowDate());
		return userDepositRecordMapper.updateUserDepositRecord(userDepositRecord);
	}

	/**
	 * 删除用户充值对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserDepositRecordByIds(String ids) {
		return userDepositRecordMapper.deleteUserDepositRecordByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除用户充值信息
	 * 
	 * @param id 用户充值ID
	 * @return 结果
	 */
	@Override
	public int deleteUserDepositRecordById(Long id) {
		return userDepositRecordMapper.deleteUserDepositRecordById(id);
	}

	@Override
	public UserDepositRecord selectUserDepositRecordByTxId(String txId) {
		if (StringUtils.isEmpty(txId)) {
			return null;
		}
		return userDepositRecordMapper.selectUserDepositRecordByTxId(txId);
	}
}
