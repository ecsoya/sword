package com.github.ecsoya.sword.local.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.wallet.domain.Address;
import com.github.ecsoya.sword.wallet.domain.WithdrawalResponse;
import com.github.ecsoya.sword.wallet.service.IWalletBusinessService;

@Service
public class LocalWalletBusinessService implements IWalletBusinessService {

	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("参数错误");
		}
		Address address = new Address();
		address.setSymbol(symbol);
		address.setChain(chain);
		address.setAddress(symbol.toUpperCase() + "_" + IdWorker.get32UUID());
		return CommonResult.success(address);
	}

	@Override
	public CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address) {
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)) {
			return CommonResult.fail("参数错误");
		}
		return CommonResult.success();
	}

	@Override
	public CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount, boolean auto) {
		if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)
				|| amount == null || amount.doubleValue() < 0) {
			return CommonResult.fail("参数错误");
		}
		WithdrawalResponse data = new WithdrawalResponse();
		data.setAddress(address);
		data.setAmount(amount);
		data.setMemo(memo);
		data.setOrderNo(orderNo);
		data.setStatus(WithdrawalResponse.STATUS_MANUAL);
		return CommonResult.success(data);
	}

}
