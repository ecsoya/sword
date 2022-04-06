package com.github.ecsoya.sword.business.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.order.domain.UserWithdrawalOrder;

/**
 * The Interface IWithdrawalNotifyService.
 */
public interface IWithdrawalNotifyService {

	/**
	 * Notify.
	 *
	 * @param order the order
	 * @return the int
	 */
	int notify(UserWithdrawalOrder order);

	/**
	 * Confirm order.
	 *
	 * @param token the token
	 * @return the common result
	 */
	CommonResult<?> confirmOrder(String token);

}
