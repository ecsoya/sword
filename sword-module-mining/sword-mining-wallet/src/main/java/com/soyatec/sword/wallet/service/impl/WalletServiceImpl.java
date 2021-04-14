package com.soyatec.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.wallet.config.WalletConfig;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.domain.WithdrawalResponse;
import com.soyatec.sword.wallet.service.IWalletDelegateService;
import com.soyatec.sword.wallet.service.IWalletService;

@Service
public class WalletServiceImpl implements IWalletService {
	private static final Logger log = LoggerFactory.getLogger(IWalletService.class);

	private static final String TIME_FORMAT = "yyyyMMddHHmm";
	@Autowired(required = false)
	private IWalletDelegateService delegateService;

	@Autowired(required = false)
	private RedisTemplate<String, Object> templates;

	@Autowired
	private WalletConfig config;

	@Override
	public CommonResult<Ticker> getTicker(String symbol) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("币种错误");
		}
		final CommonResult<Ticker> ticker = delegateService.getTicker(symbol);
		if (ticker != null && ticker.isSuccess(true)) {
			final Ticker data = ticker.getData();
			// 缓存1
			setSimpleCache(symbol, data);
			// 缓存2
			putTickerToRedis(symbol, data);
			return ticker;
		}

		final Ticker cache = getSimpleCache(symbol);
		if (cache != null) {
			return CommonResult.success(cache);
		}

		return CommonResult.fail("无法获取实时价格");
	}

	private Ticker getSimpleCache(String name) {
		if (templates == null || StringUtils.isEmpty(name)) {
			return null;
		}
		try {
			return (Ticker) templates.opsForValue().get(name);
		} catch (final Exception e) {
			return null;
		}
	}

	private void setSimpleCache(String name, Ticker ticker) {
		if (templates == null || StringUtils.isEmpty(name) || ticker == null) {
			return;
		}
		templates.opsForValue().set(name, ticker, 10, TimeUnit.MINUTES);
	}

	@Override
	public BigDecimal getPrice(String symbol) {
		final CommonResult<Ticker> ticker = getTicker(symbol);
		if (ticker.isSuccess()) {
			final Ticker data = ticker.getData();
			if (data != null) {
				return data.getPrice();
			}
		}
		return BigDecimal.ZERO;
	}

	@Override
	public Ticker getTickerFromHistory(String symbol, Date time) {
		if (time == null || StringUtils.isEmpty(symbol)) {
			final Ticker ticker = new Ticker();
			ticker.setSymbol(symbol);
			ticker.setDate(time);
			ticker.setPrice(BigDecimal.ZERO);
			return ticker;
		}
		if (DateUtils.isDuring5Minutes(time)) {
			final CommonResult<Ticker> ticker = getTicker(symbol);
			if (ticker.isSuccess(true)) {
				return ticker.getData();
			}
		}
		Ticker ticker = getTikcerFromRedis(symbol, time);
		if (ticker == null && config != null) {
			final BigDecimal price = config.getPrice(symbol, time);
			if (price != null) {
				ticker = new Ticker();
				ticker.setSymbol(symbol);
				ticker.setDate(time);
				ticker.setPrice(price);
			}
		}
		if (ticker == null) {
			ticker = new Ticker();
			ticker.setSymbol(symbol);
			ticker.setDate(time);
			ticker.setPrice(BigDecimal.ZERO);
		}
		return ticker;
	}

	private String getTickerCacheName(String name, Date date) {
		return "sword-wallet:ticker:" + name + ":" + DateUtils.parseDateToStr(TIME_FORMAT, date);
	}

	private void putTickerToRedis(String name, Ticker ticker) {
		if (templates == null || name == null || ticker == null) {
			return;
		}
		final String cacheName = getTickerCacheName(name, DateUtils.getNowDate());
		templates.opsForValue().setIfAbsent(cacheName, ticker, 8, TimeUnit.DAYS);
		log.info("putTickerToRedis {}={}", cacheName, ticker);
	}

	private Ticker getTikcerFromRedis(String name, Date date) {
		if (templates == null || StringUtils.isEmpty(name)) {
			return null;
		}
		if (date == null) {
			date = DateUtils.getNowDate();
		}
		String cacheName = getTickerCacheName(name, date);

		int count = 0;
		Ticker ticker = null;
		while (cacheName != null) {
			final Object object = templates.opsForValue().get(cacheName);
			if (object instanceof Ticker) {
				ticker = (Ticker) object;
				break;
			}
			count++;
			date = org.apache.commons.lang3.time.DateUtils.addMinutes(date, -1);
			cacheName = getTickerCacheName(name, date);
			if (count > 60) {
				log.warn("over 60 times");
				break;
			}
		}
		return ticker;
	}

	@Override
	public Ticker updateTickerCache(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return null;
		}
		final CommonResult<Ticker> ticker = getTicker(symbol);
		return ticker.getData();
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
			String memo, BigDecimal amount) {
		if (delegateService == null || delegateService == this) {
			return CommonResult.fail("钱包尚未实现");
		}
		return delegateService.withdrawal(orderNo, symbol, chain, address, null, amount);
	}
}
