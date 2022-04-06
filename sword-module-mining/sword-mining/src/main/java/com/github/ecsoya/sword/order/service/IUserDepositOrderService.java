package com.github.ecsoya.sword.order.service;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.order.domain.UserDepositOrder;

/**
 * The Interface IUserDepositOrderService.
 */
public interface IUserDepositOrderService {

	/**
	 * Select user deposit order by id.
	 *
	 * @param id the id
	 * @return the user deposit order
	 */
	public UserDepositOrder selectUserDepositOrderById(Long id);

	/**
	 * Select user deposit order list.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the list
	 */
	public List<UserDepositOrder> selectUserDepositOrderList(UserDepositOrder userDepositOrder);

	/**
	 * Insert user deposit order.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the int
	 */
	public int insertUserDepositOrder(UserDepositOrder userDepositOrder);

	/**
	 * Update user deposit order.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the int
	 */
	public int updateUserDepositOrder(UserDepositOrder userDepositOrder);

	/**
	 * Delete user deposit order by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserDepositOrderByIds(String ids);

	/**
	 * Delete user deposit order by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserDepositOrderById(Long id);

	/**
	 * Select user deposit amount.
	 *
	 * @param order the order
	 * @return the big decimal
	 */
	public BigDecimal selectUserDepositAmount(UserDepositOrder order);

	/**
	 * Select user deposit amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserDepositAmount(String symbol);

}
