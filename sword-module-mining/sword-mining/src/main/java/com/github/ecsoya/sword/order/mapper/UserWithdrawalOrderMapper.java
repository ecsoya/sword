package com.github.ecsoya.sword.order.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;

/**
 * The Interface UserWithdrawalOrderMapper.
 */
public interface UserWithdrawalOrderMapper {

	/**
	 * Select user withdrawal order by id.
	 *
	 * @param id the id
	 * @return the user withdrawal order
	 */
	public UserWithdrawalOrder selectUserWithdrawalOrderById(Long id);

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
	 * Delete user withdrawal order by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserWithdrawalOrderById(Long id);

	/**
	 * Delete user withdrawal order by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserWithdrawalOrderByIds(String[] ids);

	/**
	 * Select user withdrawal order by order no.
	 *
	 * @param orderNo the order no
	 * @return the user withdrawal order
	 */
	public UserWithdrawalOrder selectUserWithdrawalOrderByOrderNo(String orderNo);

	/**
	 * Select user withdrawal amount by date.
	 *
	 * @param symbol the symbol
	 * @param start  the start
	 * @param end    the end
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmountByDate(@Param("symbol") String symbol, @Param("start") Date start,
			@Param("end") Date end);

	/**
	 * Select user withdrawal amount.
	 *
	 * @param userWithdrawalOrder the user withdrawal order
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalAmount(UserWithdrawalOrder userWithdrawalOrder);

	/**
	 * Select user withdrawal fee amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	public BigDecimal selectUserWithdrawalFeeAmount(String symbol);
}
