package com.soyatec.sword.order.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.soyatec.sword.order.domain.UserWithdrawalOrder;

/**
 * 提现订单Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserWithdrawalOrderMapper {
	/**
	 * 查询提现订单
	 * 
	 * @param id 提现订单ID
	 * @return 提现订单
	 */
	public UserWithdrawalOrder selectUserWithdrawalOrderById(Long id);

	/**
	 * 查询提现订单列表
	 * 
	 * @param userWithdrawalOrder 提现订单
	 * @return 提现订单集合
	 */
	public List<UserWithdrawalOrder> selectUserWithdrawalOrderList(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * 新增提现订单
	 * 
	 * @param userWithdrawalOrder 提现订单
	 * @return 结果
	 */
	public int insertUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * 修改提现订单
	 * 
	 * @param userWithdrawalOrder 提现订单
	 * @return 结果
	 */
	public int updateUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * 删除提现订单
	 * 
	 * @param id 提现订单ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalOrderById(Long id);

	/**
	 * 批量删除提现订单
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalOrderByIds(String[] ids);

	public UserWithdrawalOrder selectUserWithdrawalOrderByOrderNo(String orderNo);

	public BigDecimal selectUserWithdrawalAmountByDate(@Param("symbol") String symbol, @Param("start") Date start,
			@Param("end") Date end);

	public BigDecimal selectUserWithdrawalAmount(UserWithdrawalOrder userWithdrawalOrder);

	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);
}
