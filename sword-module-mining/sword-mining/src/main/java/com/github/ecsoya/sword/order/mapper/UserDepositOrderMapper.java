package com.github.ecsoya.sword.order.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.github.ecsoya.sword.order.domain.UserDepositOrder;

/**
 * The Interface UserDepositOrderMapper.
 */
public interface UserDepositOrderMapper {

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
	 * Delete user deposit order by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserDepositOrderById(Long id);

	/**
	 * Delete user deposit order by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserDepositOrderByIds(String[] ids);

	/**
	 * Select user deposit order by tx id.
	 *
	 * @param txId the tx id
	 * @return the user deposit order
	 */
	public UserDepositOrder selectUserDepositOrderByTxId(String txId);

	/**
	 * Select user deposit amount.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the big decimal
	 */
	public BigDecimal selectUserDepositAmount(UserDepositOrder userDepositOrder);
}
