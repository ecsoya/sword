package com.soyatec.sword.wallet.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.soyatec.sword.wallet.domain.UserWalletRecord;

/**
 * 用户钱包记录Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserWalletRecordMapper {
	/**
	 * 查询用户钱包记录
	 *
	 * @param id 用户钱包记录ID
	 * @return 用户钱包记录
	 */
	public UserWalletRecord selectUserWalletRecordById(Long id);

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

	/**
	 * 删除用户钱包记录
	 *
	 * @param id 用户钱包记录ID
	 * @return 结果
	 */
	public int deleteUserWalletRecordById(Long id);

	/**
	 * 批量删除用户钱包记录
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWalletRecordByIds(String[] ids);

	public BigDecimal selectUserWalletRecordAmount(UserWalletRecord record);

	public UserWalletRecord selectUserWalletRecordOne(UserWalletRecord query);
}
