package com.github.ecsoya.sword.record.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.record.domain.UserWithdrawalRecord;

/**
 * 用户提现Mapper接口
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserWithdrawalRecordMapper {
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
	 * 删除用户提现
	 *
	 * @param id 用户提现ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalRecordById(Long id);

	/**
	 * 批量删除用户提现
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalRecordByIds(String[] ids);

	public int updateUserWithdrawalRecordHeightByTxId(@Param("txId") String txId, @Param("height") Long height);

	public BigDecimal selectUserWithdrawalAmountByDate(@Param("symbol") String symbol, @Param("start") Date start,
			@Param("end") Date end);

	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);
}
