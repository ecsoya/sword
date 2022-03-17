package com.github.ecsoya.sword.record.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;

/**
 * 用户提现Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserWithdrawalRecordService {
	/**
	 * 查询用户提现
	 *
	 * @param id 用户提现ID
	 * @return 用户提现
	 */
	public UserWithdrawalRecord selectUserWithdrawalRecordById(Long id);

	/**
	 * 查询用户提现列表
	 *
	 * @param userWithdrawalRecord 用户提现
	 * @return 用户提现集合
	 */
	public List<UserWithdrawalRecord> selectUserWithdrawalRecordList(UserWithdrawalRecord userWithdrawalRecord);

	/**
	 * 新增用户提现
	 *
	 * @param userWithdrawalRecord 用户提现
	 * @return 结果
	 */
	public int insertUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord);

	/**
	 * 修改用户提现
	 *
	 * @param userWithdrawalRecord 用户提现
	 * @return 结果
	 */
	public int updateUserWithdrawalRecord(UserWithdrawalRecord userWithdrawalRecord);

	/**
	 * 批量删除用户提现
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalRecordByIds(String ids);

	/**
	 * 删除用户提现信息
	 *
	 * @param id 用户提现ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalRecordById(Long id);

	public int updateUserWithdrawalRecordHeightByTxId(String txId, Long height);

	public BigDecimal selectUserWithdrawalAmountByDate(String symbol, Date start, Date end);

	public BigDecimal selectUserWithdrawalAmount(String symbol);

	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);

}
