package com.soyatec.sword.business.service.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import com.soyatec.sword.business.service.IWalletCallbackService;
import com.soyatec.sword.common.core.domain.CommonResult;

@Service
@ConditionalOnMissingBean(IWalletCallbackService.class)
public class WalletCallbackServiceImpl implements IWalletCallbackService<Object> {

	@Override
	public CommonResult<?> processOrder(Object rawData) {
		return CommonResult.fail("Not implemented");
	}

}
