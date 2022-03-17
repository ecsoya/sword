package com.github.ecsoya.sword.wallet.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.wallet.domain.UserWalletRecord;

/**
 * 用户钱包记录Service接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserWalletRecordService {
	/**
	 * 查询用户钱包记录
	 *
	 * @param id 用户钱包记录ID
	 * @return 用户钱包记录
	 */
	public UserWalletRecord selectUserWalletRecordById(Long id, Integer kind);

	/**
	 * 查询用户钱包记录列表
	 *
	 * @param userWalletRecord 用户钱包记录
	 * @return 用户钱包记录集合
	 */
	public List<UserWalletRecord> selectUserWalletRecordList(UserWalletRecord userWalletRecord);

	/**
	 * 新增用户钱包记录
	 *
	 * @param userWalletRecord 用户钱包记录
	 * @return 结果
	 */
	public int insertUserWalletRecord(UserWalletRecord userWalletRecord);

	/**
	 * 修改用户钱包记录
	 *
	 * @param userWalletRecord 用户钱包记录
	 * @return 结果
	 */
	public int updateUserWalletRecord(UserWalletRecord userWalletRecord);

	public BigDecimal selectUserWalletRecordAmount(UserWalletRecord record);

	public UserWalletRecord selectUserWalletRecordOne(UserWalletRecord query);
}
