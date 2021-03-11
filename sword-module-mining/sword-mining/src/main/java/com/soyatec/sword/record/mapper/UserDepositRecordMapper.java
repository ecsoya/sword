package com.soyatec.sword.record.mapper;

import java.util.List;

import com.soyatec.sword.record.domain.UserDepositRecord;

/**
 * 用户充值Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserDepositRecordMapper {
	/**
	 * 查询用户充值
	 * 
	 * @param id 用户充值ID
	 * @return 用户充值
	 */
	public UserDepositRecord selectUserDepositRecordById(Long id);

	/**
	 * 查询用户充值列表
	 * 
	 * @param userDepositRecord 用户充值
	 * @return 用户充值集合
	 */
	public List<UserDepositRecord> selectUserDepositRecordList(UserDepositRecord userDepositRecord);

	/**
	 * 新增用户充值
	 * 
	 * @param userDepositRecord 用户充值
	 * @return 结果
	 */
	public int insertUserDepositRecord(UserDepositRecord userDepositRecord);

	/**
	 * 修改用户充值
	 * 
	 * @param userDepositRecord 用户充值
	 * @return 结果
	 */
	public int updateUserDepositRecord(UserDepositRecord userDepositRecord);

	/**
	 * 删除用户充值
	 * 
	 * @param id 用户充值ID
	 * @return 结果
	 */
	public int deleteUserDepositRecordById(Long id);

	/**
	 * 批量删除用户充值
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserDepositRecordByIds(String[] ids);

	public UserDepositRecord selectUserDepositRecordByTxId(String txId);
}
