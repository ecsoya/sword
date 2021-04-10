package com.soyatec.sword.zbx.service;

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
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.http.HttpUtils;
import com.soyatec.sword.wallet.domain.Address;
import com.soyatec.sword.wallet.domain.Ticker;
import com.soyatec.sword.wallet.domain.WithdrawalResponse;
import com.soyatec.sword.wallet.service.IWalletDelegateService;
import com.soyatec.sword.zbx.config.ZbxWalletConfig;
import com.soyatec.sword.zbx.domain.ZbxDepositAddress;
import com.soyatec.sword.zbx.domain.ZbxResponse;
import com.soyatec.sword.zbx.domain.ZbxTicker;
import com.soyatec.sword.zbx.utils.HttpUtil;

@Service
public class ZbxService implements IWalletDelegateService {

	private static final Logger log = LoggerFactory.getLogger(ZbxService.class);
	@Autowired
	private ZbxWalletConfig apiConfig;

	@Override
	public CommonResult<Ticker> getTicker(String symbol) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("参数错误");
		}
		String paramValue = symbol + "_usdt";
		log.info("getTicker for {}", paramValue);
		try {
			paramValue = URLEncoder.encode(paramValue, "utf-8").toString();
		} catch (UnsupportedEncodingException e1) {
		}
		String paramName = apiConfig.getTicker().getMarket();
		String url = apiConfig.getTicker().getPlusUrl();
		try {
			String json = HttpUtils.sendSSLPost(url, paramName + "=" + paramValue);
			if (json != null) {
				ZbxTicker ticker = JSON.parseObject(json, ZbxTicker.class);
				if (ticker != null) {
					ticker.setDate(DateUtils.getNowDate());
					ticker.setSymbol(symbol);
					return CommonResult.success(ticker);
				}
			}
		} catch (Exception e) {
			log.debug("getTicker failed to use cache value");
		}
		return CommonResult.fail();
	}

	@Override
	public CommonResult<Address> getDepositAddress(String symbol, String chain) {
		if (StringUtils.isEmpty(symbol)) {
			return CommonResult.fail("参数错误");
		}
		Map<String, Object> values = new HashMap<>();
		values.put("symbol", symbol);
		values.put("chain", chain);
		Map<String, Object> params = getSignedParams(values);
		String url = apiConfig.getUrl() + apiConfig.getGetDepositAddress();
		CommonResult<ZbxDepositAddress> post = post(url, params, ZbxDepositAddress.class);
		if (post.isSuccess(true)) {
			Address address = new Address();
			address.setSymbol(symbol);
			address.setChain(chain);
			address.setAddress(post.getData().getAddress());
			return CommonResult.success(address);
		}
		return CommonResult.fail(post.getInfo());
	}

	@Override
	public CommonResult<WithdrawalResponse> withdrawal(String orderNo, String symbol, String chain, String address,
			String memo, BigDecimal amount) {
		if (StringUtils.isEmpty(orderNo) || StringUtils.isEmpty(symbol) || StringUtils.isEmpty(address)
				|| amount == null) {
			return CommonResult.fail("参数错误");
		}
		Map<String, Object> values = new HashMap<>();
		values.put("symbol", symbol);
		values.put("chain", chain);
		values.put("orderNo", orderNo);
		values.put("address", address);
		values.put("memo", memo);
		values.put("amount", amount.doubleValue());
		String url = apiConfig.getUrl() + apiConfig.getWithdrawal();
		return post(url, getSignedParams(values), WithdrawalResponse.class);
	}

	private Map<String, Object> getSignedParams(Map<String, Object> params) {
		Map<String, Object> signingParams = new HashMap<String, Object>();
		signingParams.put("accessKey", apiConfig.getAccessKey());
		signingParams.put("nonce", System.currentTimeMillis());

		Map<String, Object> appendParams = new HashMap<String, Object>();
		if (params != null) {
			Set<Entry<String, Object>> entrySet = params.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				String key = entry.getKey();
				Object value = entry.getValue();
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
		String paramToUrl = HttpUtil.paramToUrl(params);
		log.debug("发送请求：url=" + url + "?" + paramToUrl);
		try {
			String json = HttpUtils.sendSSLPost(url, paramToUrl);
			if (StringUtils.isEmpty(json)) {
				return CommonResult.fail("提币接口错误");
			}
			return parse(type, json);
		} catch (Exception e) {
			return CommonResult.fail("提币接口异常");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> CommonResult<T> parse(Class<T> type, String json) {
		log.debug("发送请求返回：" + json);
		try {
			ParserConfig config = ParserConfig.getGlobalInstance();
			ObjectDeserializer deserializer = config.getDeserializer(type);
			config.putDeserializer(ZbxResponse.class, deserializer);
			CommonResult obj = JSON.parseObject(json, CommonResult.class, config);
			if (obj != null) {
				Object data = obj.getData();
				if (data instanceof JSONObject) {
					T t = ((JSONObject) data).toJavaObject(type);
					obj.setData(t);
				}
				return obj;
			}
			return CommonResult.fail();
		} catch (Exception e) {
			return CommonResult.fail();
		}
	}
}
