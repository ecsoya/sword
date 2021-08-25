package com.soyatec.sword.wallet.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.http.HttpUtils;
import com.soyatec.sword.wallet.config.WalletConfig;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.service.IWalletTickerService;

@Service
public class WalletTickerServiceImpl implements IWalletTickerService {

	private static final Logger log = LoggerFactory.getLogger(IWalletTickerService.class);

	private static final String TIME_FORMAT = "yyyyMMddHHmm";

	private static final String CNY_USD_EXCHANGE_RATE = "CNY_USD_EXCHANGE_RATE";
	private static final String URL_EXCHANGE_RATE = "https://openexchangerates.org/api/latest.json?app_id=10579f44977344d383975f6fcc3e0c33&symbols=CNY";

	private static final String USDT_CNY_PRICE = "USDT_CNY_PRICE";

	@Autowired(required = false)
	private RedisTemplate<String, Object> templates;

	@Autowired
	private WalletConfig config;

	@Override
	public CommonResult<Ticker> getTicker(String symbol) {
		final CommonResult<Ticker> ticker = getTickerByUsdt(symbol);
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

	private CommonResult<Ticker> getTickerByUsdt(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("币种错误");
		}
		String priceUrl = config.getPriceUrl();
		String name = config.getMarket();
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(priceUrl)) {
			return CommonResult.fail("配置错误");
		}
		String market = symbol.toUpperCase() + "_USDT";
		CommonResult<Ticker> ticker = getTickerForMarket(market);
		if (ticker.isSuccess()) {
			ticker.getData().setSymbol(symbol);
		}
		return ticker;
	}

	private CommonResult<Ticker> getTickerForMarket(String market) {
		String priceUrl = config.getPriceUrl();
		String name = config.getMarket();
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(priceUrl)) {
			return CommonResult.fail("配置错误");
		}
		String value = HttpUtils.sendGet(priceUrl, name + "=" + market);
		if (StringUtils.isEmpty(value)) {
			return CommonResult.fail("请求失败");
		}
		try {
			JSONArray array = JSON.parseArray(value);
			if (array.isEmpty()) {
				return CommonResult.fail("网络错误");
			}
			JSONObject json = (JSONObject) array.get(0);
			if (json == null) {
				return CommonResult.fail("数据错误");
			}
			Ticker ticker = new Ticker();
			ticker.setDate(DateUtils.getNowDate());
			ticker.setPrice(parse(json.getString("last")));
			ticker.setLow(parse(json.getString("low_24h")));
			ticker.setHigh(parse(json.getString("high_24h")));
			ticker.setRate(parse(json.getString("change_percentage")));
			ticker.setAsk(parse(json.getString("lowest_ask")));
			ticker.setBid(parse(json.getString("highest_bid")));

			return CommonResult.build(ticker);
		} catch (Exception e) {
			return CommonResult.fail("数据错误");
		}
	}

	private static BigDecimal parse(String value) {
		if (StringUtils.isEmpty(value)) {
			return BigDecimal.ZERO;
		}
		try {
			return new BigDecimal(value);
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
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

	@Override
	public Ticker updateTickerCache(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return null;
		}
		final CommonResult<Ticker> ticker = getTicker(symbol);
		return ticker.getData();
	}

	@Override
	public BigDecimal getCnyUsdExchangeRate() {
		BigDecimal exchangeRate = null;
		if (templates != null) {
			exchangeRate = (BigDecimal) templates.opsForValue().get(CNY_USD_EXCHANGE_RATE);
		}
		if (exchangeRate == null) {
			String post = HttpUtils.sendPost(URL_EXCHANGE_RATE, null);
			if (StringUtils.isNotEmpty(post)) {
				JSONObject json = JSON.parseObject(post);
				JSONObject rates = json.getJSONObject("rates");
				if (rates != null) {
					exchangeRate = rates.getBigDecimal("CNY");
				}
			}
			if (exchangeRate != null && templates != null) {
				templates.opsForValue().set(CNY_USD_EXCHANGE_RATE, exchangeRate, Duration.ofHours(2));
			}
		}
		return exchangeRate;
	}

	@Override
	public BigDecimal getUsdtCnyPrice() {
		BigDecimal usdt_cny = null;
		if (templates != null) {
			usdt_cny = (BigDecimal) templates.opsForValue().get(USDT_CNY_PRICE);
		}
		if (usdt_cny == null) {
			CommonResult<Ticker> ticker = getTickerForMarket("USDT_USD");
			if (ticker.isSuccess()) {

				BigDecimal usdt_usd = ticker.getData().getPrice();
				if (usdt_usd != null) {

					BigDecimal usd_cny = getCnyUsdExchangeRate();
					if (usd_cny != null) {

						usdt_cny = usdt_usd.multiply(usd_cny).setScale(2, RoundingMode.HALF_UP);
					}
				}
			}
			if (usdt_cny != null && templates != null) {
				templates.opsForValue().set(USDT_CNY_PRICE, usdt_cny, Duration.ofHours(2));
			}
		}
		if (usdt_cny == null) {
			usdt_cny = BigDecimal.valueOf(6.5);
		}
		return usdt_cny;
	}

}
