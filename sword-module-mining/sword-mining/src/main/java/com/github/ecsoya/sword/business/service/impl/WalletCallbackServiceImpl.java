package com.github.ecsoya.sword.business.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.business.service.IWalletCallbackService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;

@Service
@ConditionalOnMissingBean(IWalletCallbackService.class)
public class WalletCallbackServiceImpl implements IWalletCallbackService<Object> {

	@Override
	public CommonResult<?> processOrder(Object rawData) {
		return CommonResult.fail("Not implemented");
	}

}
