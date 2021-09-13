package com.soyatec.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.domain.WithdrawalResponse;
import com.soyatec.sword.wallet.service.IWalletBusinessService;
import com.soyatec.sword.wallet.service.IWalletService;
import com.soyatec.sword.wallet.service.IWalletTickerService;

@Service
public class WalletServiceImpl implements IWalletService {

	@Autowired(required = false)
	private IWalletBusinessService delegateService;

	@Autowired
	private IWalletTickerService tickerService;

	@Override
	public BigDecimal getUsdtCnyPrice() {
		return tickerService.getUsdtCnyPrice();
	}

	@Override
	public BigDecimal getCnyUsdExchangeRate() {
		return tickerService.getCnyUsdExchangeRate();
	}

	@Override
	public CommonResult<Ticker> getTicker(String symbol) {
		return tickerService.getTicker(symbol);
	}

	@Override
	public BigDecimal getPrice(String symbol) {
		return tickerService.getPrice(symbol);
	}

	@Override
	public Ticker getTickerFromHistory(String symbol, Date time) {
		return tickerService.getTickerFromHistory(symbol, time);
	}

	@Override
	public Ticker updateTickerCache(String symbol) {
		return tickerService.updateTickerCache(symbol);
	}

	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		return delegateService.getDepositAddress(symbol, chain);
	}

	@Override
	public String getDepositAddressValue(String symbol, String chain) {
		final CommonResult<Address> result = getDepositAddress(symbol, chain);
		if (result.isSuccess(true)) {
			return result.getData().getAddress();
		}
		return null;
	}

	@Override
	public BigDecimal exchangeToUsdt(String symbol, BigDecimal value) {
		if (StringUtils.isEmpty(symbol) || org.apache.commons.lang3.StringUtils.equals("usdt", symbol)
				|| value == null) {
			return value;
		}
		final BigDecimal price = getPrice(symbol);
		if (price == null) {
			return null;
		}
		return value.multiply(price);
	}

	@Override
	public BigDecimal exchangeFromUsdt(String symbol, BigDecimal value) {
		if (StringUtils.isEmpty(symbol) || org.apache.commons.lang3.StringUtils.equals("usdt", symbol)
				|| value == null) {
			return value;
		}
		final BigDecimal price = getPrice(symbol);
		if (price == null || price.doubleValue() == 0) {
			return null;
		}
		return value.divide(price, 6, RoundingMode.HALF_UP);
	}

	@Override
	public CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount, boolean auto) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		CommonResult<WithdrawalResponse> withdrawal = delegateService.withdrawal(orderNo, symbol, chain, address, null,
				amount, true);
		if (withdrawal.isSuccess(true) && "usdt".equals(symbol)) {
			WithdrawalResponse data = withdrawal.getData();
			BigDecimal fees = data.getFees();
			String feesSymbol = data.getFeesSymbol();
			if (fees != null && !symbol.equals(feesSymbol)) {
				data.setFees(exchangeToUsdt(feesSymbol, fees));
			}
		}
		return withdrawal;
	}

	@Override
	public CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		return delegateService.checkWithdrawalAddress(symbol, chain, address);
	}
}
