package com.soyatec.sword.mining.service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.zbx.domain.ZbxOrderRawData;

public interface IWalletCallbackService {
	/**
	 * 处理充提订单
	 */
	CommonResult<?> processOrder(ZbxOrderRawData rawData);
}
