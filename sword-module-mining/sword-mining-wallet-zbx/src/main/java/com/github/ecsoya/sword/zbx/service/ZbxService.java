package com.github.ecsoya.sword.zbx.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.http.HttpUtils;
import com.github.ecsoya.sword.wallet.domain.Address;
import com.github.ecsoya.sword.wallet.domain.Ticker;
import com.github.ecsoya.sword.wallet.domain.WithdrawalResponse;
import com.github.ecsoya.sword.wallet.service.IWalletBusinessService;
import com.github.ecsoya.sword.zbx.config.ZbxWalletConfig;
import com.github.ecsoya.sword.zbx.domain.ZbxDepositAddress;
import com.github.ecsoya.sword.zbx.domain.ZbxResponse;
import com.github.ecsoya.sword.zbx.domain.ZbxTicker;
import com.github.ecsoya.sword.zbx.utils.HttpUtil;

@Service
public class ZbxService implements IWalletBusinessService {

	private static final Logger log = LoggerFactory.getLogger(ZbxService.class);
	@Autowired
	private ZbxWalletConfig apiConfig;

	public CommonResult<Ticker> getTicker(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("参数错误");
		}
		String paramValue = symbol + "_usdt";
		log.info("getTicker for {}", paramValue);
		try {
			paramValue = URLEncoder.encode(paramValue, "utf-8").toString();
		} catch (final UnsupportedEncodingException e1) {
		}
		final String paramName = apiConfig.getTicker().getMarket();
		final String url = apiConfig.getTicker().getPlusUrl();
		try {
			final String json = HttpUtils.sendSSLPost(url, paramName + "=" + paramValue);
			if (json != null) {
				final ZbxTicker ticker = JSON.parseObject(json, ZbxTicker.class);
				if (ticker != null) {
					ticker.setDate(DateUtils.getNowDate());
					ticker.setSymbol(symbol);
					return CommonResult.success(ticker);
				}
			}
		} catch (final Exception e) {
			log.debug("getTicker failed to use cache value");
		}
		return CommonResult.fail();
	}

	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("参数错误");
		}
		final Map<String, Object> values = new HashMap<>();
		values.put("symbol", symbol);
		values.put("chain", chain);
		final Map<String, Object> params = getSignedParams(values);
		final String url = apiConfig.getUrl() + apiConfig.getGetDepositAddress();
		final CommonResult<ZbxDepositAddress> post = post(url, params, ZbxDepositAddress.class);
		if (post.isSuccess(true)) {
			final Address address = new Address();
			address.setSymbol(symbol);
			address.setChain(chain);
			address.setAddress(post.getData().getAddress());
			return CommonResult.success(address);
		}
		return CommonResult.fail(post.getInfo());
	}

	@Override
	public CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount, boolean auto) {
		if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)
				|| amount == null) {
			return CommonResult.fail("参数错误");
		}
		final Map<String, Object> values = new HashMap<>();
		values.put("symbol", symbol);
		values.put("chain", chain);
		values.put("orderNo", orderNo);
		values.put("address", address);
		values.put("memo", memo);
		values.put("amount", amount.doubleValue());
		final String url = apiConfig.getUrl() + apiConfig.getWithdrawal();
		return post(url, getSignedParams(values), WithdrawalResponse.class);
	}

	private Map<String, Object> getSignedParams(Map<String, Object> params) {
		final Map<String, Object> signingParams = new HashMap<String, Object>();
		signingParams.put("accessKey", apiConfig.getAccessKey());
		signingParams.put("nonce", System.currentTimeMillis());

		final Map<String, Object> appendParams = new HashMap<String, Object>();
		if (params != null) {
			final Set<Entry<String, Object>> entrySet = params.entrySet();
			for (final Entry<String, Object> entry : entrySet) {
				final String key = entry.getKey();
				final Object value = entry.getValue();
				if (key == null) {
					continue;
				}
				if (value == null) {
					continue;
				} else if ("".equals(value)) {
					appendParams.put(key, value);
				} else {
					signingParams.put(key, value);
				}
			}
		}
		signingParams.put("sign", HttpUtil.getSignature(signingParams, apiConfig.getSecretKey()));
		signingParams.putAll(appendParams);
		return signingParams;
	}

	private <T> CommonResult<T> post(String url, Map<String, Object> params, Class<T> type) {
		final String paramToUrl = HttpUtil.paramToUrl(params);
		log.debug("发送请求：url=" + url + "?" + paramToUrl);
		try {
			final String json = HttpUtils.sendSSLPost(url, paramToUrl);
			if (StringUtils.isEmpty(json)) {
				return CommonResult.fail("提币接口错误");
			}
			return parse(type, json);
		} catch (final Exception e) {
			return CommonResult.fail("提币接口异常");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> CommonResult<T> parse(Class<T> type, String json) {
		log.debug("发送请求返回：" + json);
		try {
			final ParserConfig config = ParserConfig.getGlobalInstance();
			final ObjectDeserializer deserializer = config.getDeserializer(type);
			config.putDeserializer(ZbxResponse.class, deserializer);
			final CommonResult obj = JSON.parseObject(json, CommonResult.class, config);
			if (obj != null) {
				final Object data = obj.getData();
				if (data instanceof JSONObject) {
					final T t = ((JSONObject) data).toJavaObject(type);
					obj.setData(t);
				}
				return obj;
			}
			return CommonResult.fail();
		} catch (final Exception e) {
			return CommonResult.fail();
		}
	}

	@Override
	public CommonResult<?> checkWithdrawalAddress(String symbol, String chain, String address) {
		if (StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)) {
			return CommonResult.fail("参数错误");
		}
		return CommonResult.success(address);
	}
}
