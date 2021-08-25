package com.soyatec.sword.udun.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.udun.config.UdunWalletAddress;
import com.soyatec.sword.udun.config.UdunWalletProperties;
import com.soyatec.sword.udun.constants.CoinType;
import com.soyatec.sword.udun.domain.UAddress;
import com.soyatec.sword.udun.http.ResponseMessage;
import com.soyatec.sword.udun.http.client.UdunWalletClient;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.WithdrawalResponse;
import com.soyatec.sword.wallet.service.IWalletBusinessService;

@Service
public class UdunWalletBusinessService implements IWalletBusinessService {
	@Autowired
	private UdunWalletClient udunClient;
	@Autowired
	private UdunWalletProperties properties;

	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		CoinType coinType = CoinType.ofSymbol(symbol);
		if (coinType == null) {
			return CommonResult.fail("暂不支持");
		}
		UAddress ua = createCoinAddress(coinType, "", properties.getWalletId());
		if (ua == null) {
			UdunWalletAddress addresses = properties.getAddresses();
			if (addresses != null) {
				String addr = addresses.getAddress(symbol);
				if (StringUtils.isNotEmpty(addr)) {
					Address address = new Address();
					address.setSymbol(symbol);
					address.setAddress(addr);
					return CommonResult.build(address);
				}
			}
			return CommonResult.fail("网络错误");
		}
		Address address = new Address();
		address.setSymbol(symbol);
		address.setAddress(ua.getAddress());
		return CommonResult.build(address);
	}

	private UAddress createCoinAddress(CoinType coinType, String alias, String walletId) {
		// String realCallbackUrl = StringUtils.isEmpty(callbackUrl) ? (host +
		// "/bipay/notify") : callbackUrl;
		String callbackUrl = properties.getCallbackUrl();
		try {
			ResponseMessage<UAddress> resp = udunClient.createCoinAddress(coinType.getCode(), callbackUrl, alias,
					walletId);
			return resp.getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address) {
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)) {
			return CommonResult.fail("参数错误");
		}
		CoinType coinType = CoinType.ofSymbol(symbol);
		if (coinType == null) {
			return CommonResult.fail("币种错误");
		}
		try {
			if (udunClient.checkAddress(coinType.getCode(), address)) {
				return CommonResult.success(address);
			}
		} catch (Exception e) {
			return CommonResult.fail("网络错误");
		}
		return CommonResult.fail(4183, "提币地址错误");
	}

	@Override
	public CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount) {
		if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)
				|| amount == null || amount.doubleValue() <= 0) {
			return CommonResult.fail("参数错误");
		}
		CoinType coinType = CoinType.ofSymbol(symbol);
		if (coinType == null) {
			return CommonResult.fail("币种错误");
		}
		ResponseMessage<String> transfer = transfer(orderNo, amount, coinType.getCode(), coinType.getSubCoinType(),
				address, memo);
		if (transfer == null || !transfer.isSuccess()) {
			return CommonResult.fail(transfer.getMessage());
		}
		String data = transfer.getData();
		WithdrawalResponse res = JSON.parseObject(data, WithdrawalResponse.class);
		return CommonResult.success(res);
	}

	private ResponseMessage<String> transfer(String orderId, BigDecimal amount, String mainCoinType, String coinType,
			String address, String memo) {
		String callbackUrl = properties.getCallbackUrl();
		try {
			ResponseMessage<String> resp = udunClient.transfer(orderId, amount, mainCoinType, coinType, address,
					callbackUrl, memo);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseMessage.error("提交转币失败");
	}
}
