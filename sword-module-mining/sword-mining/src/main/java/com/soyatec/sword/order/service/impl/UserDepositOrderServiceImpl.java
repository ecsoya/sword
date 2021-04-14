package com.soyatec.sword.order.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.order.domain.UserDepositOrder;
import com.soyatec.sword.order.mapper.UserDepositOrderMapper;
import com.soyatec.sword.order.service.IUserDepositOrderService;
import com.soyatec.sword.utils.MathUtils;

/**
 * 充值订单Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class UserDepositOrderServiceImpl implements IUserDepositOrderService {
	@Autowired
	private UserDepositOrderMapper userDepositOrderMapper;

	/**
	 * 查询充值订单
	 *
	 * @param id 充值订单ID
	 * @return 充值订单
	 */
	@Override
	public UserDepositOrder selectUserDepositOrderById(Long id) {
		return userDepositOrderMapper.selectUserDepositOrderById(id);
	}

	/**
	 * 查询充值订单列表
	 *
	 * @param userDepositOrder 充值订单
	 * @return 充值订单
	 */
	@Override
	public List<UserDepositOrder> selectUserDepositOrderList(UserDepositOrder userDepositOrder) {
		return userDepositOrderMapper.selectUserDepositOrderList(userDepositOrder);
	}

	/**
	 * 新增充值订单
	 *
	 * @param userDepositOrder 充值订单
	 * @return 结果
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
	 * 修改充值订单
	 *
	 * @param userDepositOrder 充值订单
	 * @return 结果
	 */
	@Override
	public int updateUserDepositOrder(UserDepositOrder userDepositOrder) {
		userDepositOrder.setUpdateTime(DateUtils.getNowDate());
		return userDepositOrderMapper.updateUserDepositOrder(userDepositOrder);
	}

	/**
	 * 删除充值订单对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserDepositOrderByIds(String ids) {
		return userDepositOrderMapper.deleteUserDepositOrderByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除充值订单信息
	 *
	 * @param id 充值订单ID
	 * @return 结果
	 */
	@Override
	public int deleteUserDepositOrderById(Long id) {
		return userDepositOrderMapper.deleteUserDepositOrderById(id);
	}

	@Override
	public BigDecimal selectUserDepositAmount(UserDepositOrder userDepositOrder) {
		return MathUtils.nullToZero(userDepositOrderMapper.selectUserDepositAmount(userDepositOrder));
	}

	@Override
	public BigDecimal selectUserDepositAmount(String symbol) {
		final UserDepositOrder query = new UserDepositOrder();
		query.setSymbol(symbol);
		return selectUserDepositAmount(query);
	}
}
