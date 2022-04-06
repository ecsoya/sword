package com.github.ecsoya.sword.business.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.business.service.IWalletCallbackService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;

/**
 * The Class WalletCallbackServiceImpl.
 */
@Service
@ConditionalOnMissingBean(IWalletCallbackService.class)
public class WalletCallbackServiceImpl implements IWalletCallbackService<Object> {

	/**
	 * Process order.
	 *
	 * @param rawData the raw data
	 * @return the common result
	 */
	@Override
	public CommonResult<?> processOrder(Object rawData) {
		return CommonResult.fail("Not implemented");
	}

}
