package com.github.ecsoya.sword.order.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.exceptions.TransactionException;
import com.github.ecsoya.sword.order.domain.UserWithdrawalManual;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;

/**
 * The Interface IUserWithdrawalOrderService.
 */
public interface IUserWithdrawalOrderService {

	/**
	 * Select user withdrawal order by id.
	 *
	 * @param id the id
	 * @return the user withdrawal order
	 */
	public UserWithdrawalOrder selectUserWithdrawalOrderById(Long id);

	/**
	 * Select user withdrawal order by order no.
	 *
	 * @param orderNo the order no
	 * @return the user withdrawal order
	 */
	public UserWithdrawalOrder selectUserWithdrawalOrderByOrderNo(String orderNo);

	/**
	 * Select user withdrawal order list.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the list
	 */
	public List<UserWithdrawalOrder> selectUserWithdrawalOrderList(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * Insert user withdrawal order.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the int
	 */
	public int insertUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * Update user withdrawal order.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the int
	 */
	public int updateUserWithdrawalOrder(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * Delete user withdrawal order by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWithdrawalOrderByIds(String ids);

	/**
	 * Delete user withdrawal order by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWithdrawalOrderById(Long id);

	/**
	 * Withdrawal.
	 *
	 * @param userId   the user id
	 * @param symbol   the symbol
	 * @param address  the address
	 * @param amount   the amount
	 * @param password the password
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	public CommonResult<UserWithdrawalOrder> withdrawal(Long userId, String symbol, String address, BigDecimal amount,
			String password) throws TransactionException;

	/**
	 * Cancel withdrawal.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 * @param remark  the remark
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	public CommonResult<?> cancelWithdrawal(Long userId, String orderNo, String remark) throws TransactionException;

	/**
	 * Confirm withdrawal.
	 *
	 * @param userId  the user id
	 * @param orderNo the order no
	 * @return the common result
	 * @throws TransactionException the transaction exception
	 */
	public CommonResult<?> confirmWithdrawal(Long userId, String orderNo) throws TransactionException;

	/**
	 * Cancel withdrawal.
	 *
	 * @param orderNos the order nos
	 * @param remark   the remark
	 * @return the common result
	 */
	public CommonResult<?> cancelWithdrawal(String orderNos, String remark);

	/**
	 * Confirm withdrawal.
	 *
	 * @param orderNos the order nos
	 * @return the common result
	 */
	public CommonResult<?> confirmWithdrawal(String orderNos);

	/**
	 * Select user withdrawal amount.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmount(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * Select user withdrawal amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmount(String symbol);

	/**
	 * Select user withdrawal fee amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);

	/**
	 * Manual withdrawal record.
	 *
	 * @param manual the manual
	 * @return the common result
	 */
	public CommonResult<?> manualWithdrawalRecord(UserWithdrawalManual manual);

}
