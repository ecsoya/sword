package com.github.ecsoya.sword.order.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.order.domain.UserDepositOrder;
import com.github.ecsoya.sword.order.mapper.UserDepositOrderMapper;
import com.github.ecsoya.sword.order.service.IUserDepositOrderService;
import com.github.ecsoya.sword.utils.MathUtils;

/**
 * The Class UserDepositOrderServiceImpl.
 */
@Service
public class UserDepositOrderServiceImpl implements IUserDepositOrderService {

	/** The user deposit order mapper. */
	@Autowired
	private UserDepositOrderMapper userDepositOrderMapper;

	/**
	 * Select user deposit order by id.
	 *
	 * @param id the id
	 * @return the user deposit order
	 */
	@Override
	public UserDepositOrder selectUserDepositOrderById(Long id) {
		return userDepositOrderMapper.selectUserDepositOrderById(id);
	}

	/**
	 * Select user deposit order list.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the list
	 */
	@Override
	public List<UserDepositOrder> selectUserDepositOrderList(UserDepositOrder userDepositOrder) {
		return userDepositOrderMapper.selectUserDepositOrderList(userDepositOrder);
	}

	/**
	 * Insert user deposit order.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the int
	 */
	@Override
	public int insertUserDepositOrder(UserDepositOrder userDepositOrder) {
		if (userDepositOrder.getId() == null) {
			userDepositOrder.setId(IdWorker.getId());
		}
		if (userDepositOrder.getCreateTime() == null) {
			userDepositOrder.setCreateTime(DateUtils.getNowDate());
		}
		return userDepositOrderMapper.insertUserDepositOrder(userDepositOrder);
	}

	/**
	 * Update user deposit order.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the int
	 */
	@Override
	public int updateUserDepositOrder(UserDepositOrder userDepositOrder) {
		userDepositOrder.setUpdateTime(DateUtils.getNowDate());
		return userDepositOrderMapper.updateUserDepositOrder(userDepositOrder);
	}

	/**
	 * Delete user deposit order by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteUserDepositOrderByIds(String ids) {
		return userDepositOrderMapper.deleteUserDepositOrderByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete user deposit order by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteUserDepositOrderById(Long id) {
		return userDepositOrderMapper.deleteUserDepositOrderById(id);
	}

	/**
	 * Select user deposit amount.
	 *
	 * @param userDepositOrder the user deposit order
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserDepositAmount(UserDepositOrder userDepositOrder) {
		return MathUtils.nullToZero(userDepositOrderMapper.selectUserDepositAmount(userDepositOrder));
	}

	/**
	 * Select user deposit amount.
	 *
	 * @param symbol the symbol
	 * @return the big decimal
	 */
	@Override
	public BigDecimal selectUserDepositAmount(String symbol) {
		final UserDepositOrder query = new UserDepositOrder();
		query.setSymbol(symbol);
		return selectUserDepositAmount(query);
	}
}
