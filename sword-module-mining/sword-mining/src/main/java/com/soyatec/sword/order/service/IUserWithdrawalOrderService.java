package com.soyatec.sword.order.service;

import java.math.BigDecimal;
import java.util.List;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.exceptions.TransactionException;
import com.soyatec.sword.order.domain.UserWithdrawalOrder;

/**
 * 提现订单Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface IUserWithdrawalOrderService {
	/**
	 * 查询提现订单
	 * 
	 * @param id 提现订单ID
	 * @return 提现订单
	 */
	public UserWithdrawalOrder selectUserWithdrawalOrderById(Long id);

	public UserWithdrawalOrder selectUserWithdrawalOrderByOrderNo(String orderNo);

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
	 * 批量删除提现订单
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalOrderByIds(String ids);

	/**
	 * 删除提现订单信息
	 * 
	 * @param id 提现订单ID
	 * @return 结果
	 */
	public int deleteUserWithdrawalOrderById(Long id);

	/**
	 * 用户发起提现申请
	 */
	public CommonResult<Object> withdrawal(Long userId, String symbol, String address, BigDecimal amount,
			String password) throws TransactionException;

	/**
	 * 用户取消提币
	 */
	public CommonResult<?> cancelWithdrawal(Long userId, String orderNo, String remark) throws TransactionException;

	/**
	 * 用户确认提币
	 */
	public CommonResult<?> confirmWithdrawal(Long userId, String orderNo) throws TransactionException;

	/**
	 * 后台提币审核拒绝
	 */
	public CommonResult<?> cancelWithdrawal(String orderNos, String remark);

	/**
	 * 后台提币审核通过
	 */
	public CommonResult<?> confirmWithdrawal(String orderNos);

	public BigDecimal selectUserWithdrawalAmount(UserWithdrawalOrder userWithdrawalOrder);

	public BigDecimal selectUserWithdrawalAmount(String symbol);

	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);

}
