package com.github.ecsoya.sword.business.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;

public interface IWithdrawalNotifyService {

	/**
	 * 给管理员发提币申请消息
	 */
	int notify(UserWithdrawalOrder order);

	/**
	 * 确认订单
	 */
	CommonResult<?> confirmOrder(String token);

}
