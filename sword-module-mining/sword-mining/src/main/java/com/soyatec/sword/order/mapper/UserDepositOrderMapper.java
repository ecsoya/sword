package com.soyatec.sword.order.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.soyatec.sword.order.domain.UserDepositOrder;

/**
 * 充值订单Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public interface UserDepositOrderMapper {
	/**
	 * 查询充值订单
	 * 
	 * @param id 充值订单ID
	 * @return 充值订单
	 */
	public UserDepositOrder selectUserDepositOrderById(Long id);

	/**
	 * 查询充值订单列表
	 * 
	 * @param userDepositOrder 充值订单
	 * @return 充值订单集合
	 */
	public List<UserDepositOrder> selectUserDepositOrderList(UserDepositOrder userDepositOrder);

	/**
	 * 新增充值订单
	 * 
	 * @param userDepositOrder 充值订单
	 * @return 结果
	 */
	public int insertUserDepositOrder(UserDepositOrder userDepositOrder);

	/**
	 * 修改充值订单
	 * 
	 * @param userDepositOrder 充值订单
	 * @return 结果
	 */
	public int updateUserDepositOrder(UserDepositOrder userDepositOrder);

	/**
	 * 删除充值订单
	 * 
	 * @param id 充值订单ID
	 * @return 结果
	 */
	public int deleteUserDepositOrderById(Long id);

	/**
	 * 批量删除充值订单
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserDepositOrderByIds(String[] ids);

	public UserDepositOrder selectUserDepositOrderByTxId(String txId);

	public BigDecimal selectUserDepositAmount(UserDepositOrder userDepositOrder);
}
