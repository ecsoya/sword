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

	@Override
	public CommonResult<Ticker> getTicker(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("币种错误");
		}
		if (delegateService == null) {
			return CommonResult.fail("无钱包对接");
		}
		CommonResult<Ticker> ticker = delegateService.getTicker(symbol);
		if (ticker != null && ticker.isSuccess(true)) {
			Ticker data = ticker.getData();
			// 缓存1
			setSimpleCache(symbol, data);
			// 缓存2
			putTickerToRedis(symbol, data);
			return ticker;
		}

		Ticker cache = getSimpleCache(symbol);
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
		} catch (Exception e) {
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
		CommonResult<Ticker> ticker = getTicker(symbol);
		if (ticker.isSuccess()) {
			Ticker data = ticker.getData();
			if (data != null) {
				return data.getPrice();
			}
		}
		return BigDecimal.ZERO;
	}

	@Override
	public Ticker getTickerFromHistory(String symbol, Date time) {
		if (time == null || StringUtils.isEmpty(symbol)) {
			Ticker ticker = new Ticker();
			ticker.setSymbol(symbol);
			ticker.setDate(time);
			ticker.setPrice(BigDecimal.ZERO);
			return ticker;
		}
		if (DateUtils.isDuring5Minutes(time)) {
			CommonResult<Ticker> ticker = getTicker(symbol);
			if (ticker.isSuccess(true)) {
				return ticker.getData();
			}
		}
		return getTikcerFromRedis(symbol, time);
	}

	private String getTickerCacheName(String name, Date date) {
		return "beeplus-wallet:ticker:" + name + ":" + DateUtils.parseDateToStr(TIME_FORMAT, date);
	}

	private void putTickerToRedis(String name, Ticker ticker) {
		if (templates == null || name == null || ticker == null) {
			return;
		}
		String cacheName = getTickerCacheName(name, DateUtils.getNowDate());
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
			Object object = templates.opsForValue().get(cacheName);
			if (object instanceof Ticker) {
				ticker = (Ticker) object;
				break;
			}
			count++;
			date = DateUtils.addMinutes(date, -1);
			cacheName = getTickerCacheName(name, date);
			if (count > 60) {
				log.warn("over 60 times");
				break;
			}
		}
		if (ticker == null) {
			ticker = new Ticker();
			ticker.setSymbol(name);
			ticker.setDate(date);
			ticker.setPrice(BigDecimal.valueOf(0.1));
		}
		return ticker;
	}

	@Override
	public Ticker updateTickerCache(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return null;
		}
		CommonResult<Ticker> ticker = getTicker(symbol);
		return ticker.getData();
	}

	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		if (delegateService != null) {
			return delegateService.getDepositAddress(symbol, chain);
		}
		return CommonResult.fail("无钱包对接");
	}

	@Override
	public String getDepositAddressValue(String symbol, String chain) {
		CommonResult<Address> result = getDepositAddress(symbol, chain);
		if (result.isSuccess(true)) {
			return result.getData().getAddress();
		}
		return null;
	}

	@Override
	public BigDecimal exchangeToUsdt(String symbol, BigDecimal value) {
		if (StringUtils.isEmpty(symbol) || StringUtils.equals("usdt", symbol) || value == null) {
			return value;
		}
		BigDecimal price = getPrice(symbol);
		if (price == null) {
			return null;
		}
		return value.multiply(price);
	}

	@Override
	public BigDecimal exchangeFromUsdt(String symbol, BigDecimal value) {
		if (StringUtils.isEmpty(symbol) || StringUtils.equals("usdt", symbol) || value == null) {
			return value;
		}
		BigDecimal price = getPrice(symbol);
		if (price == null || price.doubleValue() == 0) {
			return null;
		}
		return value.divide(price, 6, RoundingMode.HALF_UP);
	}

	@Override
	public CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address, String memo,
			BigDecimal amount) {
		if (delegateService != null) {
			return delegateService.withdrawal(orderNo, symbol, chain, address, null, amount);
		}
		return CommonResult.fail("无钱包对接");
	}
}