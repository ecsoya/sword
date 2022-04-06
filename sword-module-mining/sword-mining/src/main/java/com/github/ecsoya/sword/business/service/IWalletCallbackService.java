package com.github.ecsoya.sword.business.service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Interface IWalletCallbackService.
 *
 * @param <T> the generic type
 */
public interface IWalletCallbackService<T> {

	/**
	 * Process order.
	 *
	 * @param rawData the raw data
	 * @return the common result
	 */
	CommonResult<?> processOrder(T rawData);

	/**
	 * Parses the raw data.
	 *
	 * @param body the body
	 * @return the common result
	 */
	default CommonResult<T> parseRawData(String body) {
		return CommonResult.fail("Not Implemented");
	}
}
