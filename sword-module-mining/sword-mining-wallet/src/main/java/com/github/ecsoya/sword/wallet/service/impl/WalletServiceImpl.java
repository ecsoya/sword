package com.github.ecsoya.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.wallet.domain.Address;
import com.github.ecsoya.sword.wallet.domain.Ticker;
import com.github.ecsoya.sword.wallet.domain.WithdrawalResponse;
import com.github.ecsoya.sword.wallet.service.IWalletBusinessService;
import com.github.ecsoya.sword.wallet.service.IWalletService;
import com.github.ecsoya.sword.wallet.service.IWalletTickerService;

/**
 * The Class WalletServiceImpl.
 */
@Service
public class WalletServiceImpl implements IWalletService {

	/** The delegate service. */
	@Autowired(required = false)
	private IWalletBusinessService delegateService;

	/** The ticker service. */
	@Autowired
	private IWalletTickerService tickerService;

	/**
	 * Gets the usdt cny price.
	 *
	 * @return the usdt cny price
	 */
	@Override
	public BigDecimal getUsdtCnyPrice() {
		return tickerService.getUsdtCnyPrice();
	}

	/**
	 * Gets the cny usd exchange rate.
	 *
	 * @return the cny usd exchange rate
	 */
	@Override
	public BigDecimal getCnyUsdExchangeRate() {
		return tickerService.getCnyUsdExchangeRate();
	}

	/**
	 * Gets the ticker.
	 *
	 * @param symbol the symbol
	 * @return the ticker
	 */
	@Override
	public CommonResult<Ticker> getTicker(String symbol) {
		return tickerService.getTicker(symbol);
	}

	/**
	 * Gets the price.
	 *
	 * @param symbol the symbol
	 * @return the price
	 */
	@Override
	public BigDecimal getPrice(String symbol) {
		return tickerService.getPrice(symbol);
	}

	/**
	 * Gets the ticker from history.
	 *
	 * @param symbol the symbol
	 * @param time   the time
	 * @return the ticker from history
	 */
	@Override
	public Ticker getTickerFromHistory(String symbol, Date time) {
		return tickerService.getTickerFromHistory(symbol, time);
	}

	/**
	 * Update ticker cache.
	 *
	 * @param symbol the symbol
	 * @return the ticker
	 */
	@Override
	public Ticker updateTickerCache(String symbol) {
		return tickerService.updateTickerCache(symbol);
	}

	/**
	 * Gets the deposit address.
	 *
	 * @param symbol the symbol
	 * @param chain  the chain
	 * @return the deposit address
	 */
	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		return delegateService.getDepositAddress(symbol, chain);
	}

	/**
	 * Gets the deposit address value.
	 *
	 * @param symbol the symbol
	 * @param chain  the chain
	 * @return the deposit address value
	 */
	@Override
	public String getDepositAddressValue(String symbol, String chain) {
		final CommonResult<Address> result = getDepositAddress(symbol, chain);
		if (result.isSuccess(true)) {
			return result.getData().getAddress();
		}
		return null;
	}

	/**
	 * Exchange to usdt.
	 *
	 * @param symbol the symbol
	 * @param value  the value
	 * @return the big decimal
	 */
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

	/**
	 * Exchange from usdt.
	 *
	 * @param symbol the symbol
	 * @param value  the value
	 * @return the big decimal
	 */
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

	/**
	 * Withdrawal.
	 *
	 * @param orderNo the order no
	 * @param symbol  the symbol
	 * @param chain   the chain
	 * @param address the address
	 * @param memo    the memo
	 * @param amount  the amount
	 * @param auto    the auto
	 * @return the common result
	 */
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

	/**
	 * Check withdrawal address.
	 *
	 * @param symbol  the symbol
	 * @param chain   the chain
	 * @param address the address
	 * @return the common result
	 */
	@Override
	public CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		return delegateService.checkWithdrawalAddress(symbol, chain, address);
	}

	/**
	 * Checks if is local.
	 *
	 * @return true, if is local
	 */
	@Override
	public boolean isLocal() {
		return delegateService != null
				&& "LocalWalletBusinessService".equals(delegateService.getClass().getSimpleName());
	}
}
