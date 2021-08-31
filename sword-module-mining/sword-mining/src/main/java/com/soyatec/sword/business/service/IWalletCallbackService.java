package com.soyatec.sword.business.service;

import com.soyatec.sword.common.core.domain.CommonResult;

public interface IWalletCallbackService<T> {

	/**
	 * 处理充提订单
	 */
	CommonResult<?> processOrder(T rawData);

	/**
	 * 解析数据
	 */
	default CommonResult<T> parseRawData(String body) {
		return CommonResult.fail("Not Implemented");
	}
}
