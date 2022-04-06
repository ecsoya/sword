package com.github.ecsoya.sword.udun.http.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.ecsoya.sword.udun.constants.API;
import com.github.ecsoya.sword.udun.domain.UAddress;
import com.github.ecsoya.sword.udun.domain.USupportCoin;
import com.github.ecsoya.sword.udun.domain.UTransaction;
import com.github.ecsoya.sword.udun.domain.UTransfer;
import com.github.ecsoya.sword.udun.http.ResponseMessage;
import com.github.ecsoya.sword.udun.utils.HttpUtil;

/**
 * The Class UdunWalletClient.
 */
public class UdunWalletClient implements WalletClient<String> {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(UdunWalletClient.class);

	/** The gateway. */
	private String gateway;

	/** The connection exception. */
	private static String CONNECTION_EXCEPTION = "connect exception";

	/** The encode type. */
	private static String ENCODE_TYPE = "UTF-8";

	/** The content type value. */
	private static String CONTENT_TYPE_VALUE = "application/json";

	/** The content type name. */
	private static String CONTENT_TYPE_NAME = "Content-Type";

	/** The connect timeout. */
	private int connectTimeout = 1000;

	/** The request timeout. */
	private int requestTimeout = 1000;

	/** The redirect enabled. */
	private Boolean redirectEnabled = true;

	/** The merchant id. */
	private String merchantId;

	/** The merchant key. */
	private String merchantKey;

	/** The Constant cookieStore. */
	public static final CookieStore cookieStore = new BasicCookieStore();

	/** The request config. */
	public RequestConfig requestConfig;

	/**
	 * Instantiates a new udun wallet client.
	 */
	public UdunWalletClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(requestTimeout).setRedirectsEnabled(redirectEnabled).build();
		this.requestConfig = requestConfig;
	}

	/**
	 * Instantiates a new udun wallet client.
	 *
	 * @param gateway    the gateway
	 * @param merchantId the merchant id
	 * @param key        the key
	 */
	public UdunWalletClient(String gateway, String merchantId, String key) {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(requestTimeout).setRedirectsEnabled(redirectEnabled).build();
		this.gateway = gateway;
		this.merchantId = merchantId;
		this.merchantKey = key;
		this.requestConfig = requestConfig;
	}

	/**
	 * Instantiates a new udun wallet client.
	 *
	 * @param host          the host
	 * @param merchantId    the merchant id
	 * @param key           the key
	 * @param requestConfig the request config
	 */
	public UdunWalletClient(String host, String merchantId, String key, RequestConfig requestConfig) {
		this.gateway = host;
		this.merchantId = merchantId;
		this.merchantKey = key;
		this.requestConfig = requestConfig;
	}

	/**
	 * Creates the coin address.
	 *
	 * @param mainCoinType the main coin type
	 * @param callbackUrl  the callback url
	 * @param alias        the alias
	 * @param walletId     the wallet id
	 * @return the response message
	 * @throws Exception the exception
	 */
	public ResponseMessage<UAddress> createCoinAddress(String mainCoinType, String callbackUrl, String alias,
			String walletId) throws Exception {
		JSONArray body = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("merchantId", this.merchantId);
		item.put("coinType", mainCoinType);
		item.put("callUrl", callbackUrl);
		if (alias != null && alias != "") {
			item.put("alias", alias);
		} else
			item.put("alias", "");
		if (walletId != null && walletId != "") {
			item.put("walletId", walletId);
		} else
			item.put("walletId", "");
		body.add(item);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, body.toJSONString());
		ResponseMessage<String> response = post(API.CREATE_ADDRESS, map);
		ResponseMessage<UAddress> result = new ResponseMessage<>(response.getCode(), response.getMessage());
		if (result.getCode() == ResponseMessage.SUCCESS_CODE) {
			result.setData(UAddress.parse(response.getData()));
		}
		return result;
	}

	/**
	 * Creates the batch coin address.
	 *
	 * @param mainCoinType the main coin type
	 * @param callbackUrl  the callback url
	 * @param count        the count
	 * @return the response message
	 * @throws Exception the exception
	 */
	public ResponseMessage<List<UAddress>> createBatchCoinAddress(String mainCoinType, String callbackUrl, int count)
			throws Exception {
		JSONArray body = new JSONArray();
		JSONObject item = new JSONObject();
		item.put("merchantId", this.merchantId);
		item.put("coinType", mainCoinType);
		item.put("callUrl", callbackUrl);
		item.put("count", count);
		body.add(item);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, body.toJSONString());
		ResponseMessage<String> response = post(API.CREATE_BATCH_ADDRESS, map);
		ResponseMessage<List<UAddress>> result = new ResponseMessage<>(response.getCode(), response.getMessage());
		if (result.getCode() == ResponseMessage.SUCCESS_CODE) {
			result.setData(JSONArray.parseArray(response.getData(), UAddress.class));
		}
		return result;
	}

	/**
	 * Transfer.
	 *
	 * @param orderId      the order id
	 * @param amount       the amount
	 * @param mainCoinType the main coin type
	 * @param subCoinType  the sub coin type
	 * @param address      the address
	 * @param callbackUrl  the callback url
	 * @param memo         the memo
	 * @return the response message
	 * @throws Exception the exception
	 */
	public ResponseMessage<String> transfer(String orderId, BigDecimal amount, String mainCoinType, String subCoinType,
			String address, String callbackUrl, String memo) throws Exception {
		UTransfer transfer = new UTransfer();
		transfer.setAddress(address);
		transfer.setMainCoinType(mainCoinType);
		transfer.setCoinType(subCoinType);
		transfer.setBusinessId(orderId);
		transfer.setMerchantId(merchantId);
		transfer.setAmount(amount);
		transfer.setCallUrl(callbackUrl);
		transfer.setMemo(memo);
		JSONArray body = new JSONArray();
		body.add(transfer);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, body.toJSONString());
		ResponseMessage<String> response = post(API.WITHDRAW, map);
		return response;
	}

	/**
	 * Auto transfer.
	 *
	 * @param orderId      the order id
	 * @param amount       the amount
	 * @param mainCoinType the main coin type
	 * @param subCoinType  the sub coin type
	 * @param address      the address
	 * @param callbackUrl  the callback url
	 * @param memo         the memo
	 * @return the response message
	 * @throws Exception the exception
	 */
	public ResponseMessage<String> autoTransfer(String orderId, BigDecimal amount, String mainCoinType,
			String subCoinType, String address, String callbackUrl, String memo) throws Exception {
		UTransfer transfer = new UTransfer();
		transfer.setAddress(address);
		transfer.setMainCoinType(mainCoinType);
		transfer.setCoinType(subCoinType);
		transfer.setBusinessId(orderId);
		transfer.setMerchantId(merchantId);
		transfer.setAmount(amount);
		transfer.setCallUrl(callbackUrl);
		transfer.setMemo(memo);
		JSONArray body = new JSONArray();
		body.add(transfer);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, body.toJSONString());
		ResponseMessage<String> response = post(API.AUTO_WITHDRAW, map);
		return response;
	}

	/**
	 * Check address.
	 *
	 * @param mainCoinType the main coin type
	 * @param address      the address
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkAddress(String mainCoinType, String address) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("merchantId", merchantId);
		jsonObject.put("mainCoinType", mainCoinType);
		jsonObject.put("address", address);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, "[" + jsonObject.toJSONString() + "]");
		ResponseMessage<String> response = post(API.CHECK_ADDRESS, map);
		return response.getCode() == 200;
		// return (boolean) JSONObject.parse(response.getData());
	}

	/**
	 * Gets the support coin.
	 *
	 * @param showBalance the show balance
	 * @return the support coin
	 * @throws Exception the exception
	 */
	public List<USupportCoin> getSupportCoin(Boolean showBalance) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("merchantId", merchantId);
		jsonObject.put("showBalance", showBalance);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, jsonObject.toJSONString());
		ResponseMessage<String> response = post(API.SUPPORT_COIN, map);
		List<USupportCoin> supportCoinList = JSONObject.parseArray(response.getData(), USupportCoin.class);
		return supportCoinList;
	}

	/**
	 * Check support.
	 *
	 * @param merchantId the merchant id
	 * @param coinName   the coin name
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkSupport(Long merchantId, String coinName) throws Exception {
		boolean supported = false;
		List<USupportCoin> supportCoinList = getSupportCoin(false);
		for (USupportCoin supportCoin : supportCoinList) {
			if (supportCoin.getName().equals(coinName)) {
				supported = true;
				break;
			}
		}
		return supported;
	}

	/**
	 * Check proxy.
	 *
	 * @param amount       the amount
	 * @param mainCoinType the main coin type
	 * @param subCoinType  the sub coin type
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean checkProxy(BigDecimal amount, String mainCoinType, String subCoinType) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mainCoinType", mainCoinType);
		jsonObject.put("subCoinType", subCoinType);
		jsonObject.put("amount", amount);
		jsonObject.put("merchantId", merchantId);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, jsonObject.toJSONString());
		ResponseMessage<String> response = post(API.CHECK_PROXY, map);
		return (boolean) JSONObject.parse(response.getData());
	}

	/**
	 * Query transaction.
	 *
	 * @param mainCoinType   the main coin type
	 * @param coinType       the coin type
	 * @param tradeId        the trade id
	 * @param tradeType      the trade type
	 * @param address        the address
	 * @param startTimestamp the start timestamp
	 * @param endTimestamp   the end timestamp
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<UTransaction> queryTransaction(String mainCoinType, String coinType, String tradeId, Integer tradeType,
			String address, String startTimestamp, String endTimestamp) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("mainCoinType", mainCoinType);
		jsonObject.put("coinType", coinType);
		jsonObject.put("tradeId", tradeId);
		jsonObject.put("tradeType", tradeType);
		jsonObject.put("address", address);
		jsonObject.put("startTimestamp", startTimestamp);
		jsonObject.put("endTimestamp", endTimestamp);
		Map<String, String> map = HttpUtil.wrapperParams(this.merchantKey, jsonObject.toJSONString());
		ResponseMessage<String> response = post(API.TRANSACTION, map);
		List<UTransaction> trades = JSONObject.parseArray(response.getData(), UTransaction.class);
		return trades;
	}

	/**
	 * Post.
	 *
	 * @param url the url
	 * @param map the map
	 * @return the response message
	 */
	public ResponseMessage<String> post(String url, Map<String, String> map) {
		CloseableHttpClient httpCilent = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpPost httpPost = new HttpPost(gateway + url);
		httpPost.setConfig(requestConfig);
		HttpResponse httpResponse = null;
		try {
			StringEntity entity = new StringEntity(JSONObject.toJSONString(map), Charset.forName(ENCODE_TYPE));
			entity.setContentType(CONTENT_TYPE_VALUE);
			entity.setContentEncoding(ENCODE_TYPE);
			httpPost.addHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_VALUE);
			httpPost.setEntity(entity);
			httpResponse = httpCilent.execute(httpPost);
			String strResult;
			ResponseMessage<String> response;
			if (httpResponse != null) {
				log.info("httpResponse:{}", httpResponse.getStatusLine().toString());
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
					log.debug("strResult:,{}", strResult);
					JSONObject json = JSONObject.parseObject(strResult);
					response = ResponseMessage.success(json.getInteger("code"), json.getString("message"));
					if (json.getString("data") != null) {
						response.setData(json.getString("data"));
					}
				} else {
					strResult = "Error Response: " + httpResponse.getStatusLine().toString();
					JSONObject json = JSONObject.parseObject(strResult);
					response = ResponseMessage.error(json.getInteger("code"), json.getString("message"));
				}
				log.info("Response:,{}", response);
				return response;
			}
		} catch (IOException e) {
			log.error("IOException:{}", e);
		} finally {
			if (httpCilent != null) {
				try {
					httpCilent.close();
				} catch (IOException e) {
					log.error("http client close exception:{}", e);
				}
			}
		}
		log.error(CONNECTION_EXCEPTION);
		return ResponseMessage.error(CONNECTION_EXCEPTION);
	}

	/**
	 * Gets the.
	 *
	 * @param url the url
	 * @return the response message
	 */
	public ResponseMessage<String> get(String url) {
		CloseableHttpClient httpCilent = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		String strResult;
		ResponseMessage<String> response;
		try {
			HttpResponse httpResponse = httpCilent.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
				response = ResponseMessage.success();
				response.setData(strResult);
			} else {
				strResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
				response = ResponseMessage.error(strResult);
			}
			log.info("Response:,{}", response);
			return response;
		} catch (IOException e) {
			log.error("Request exception:{}", e);
		} finally {
			if (httpCilent != null) {
				try {
					httpCilent.close();
				} catch (IOException e) {
					log.error("http client close exception:{}", e);
				}
			}
		}
		log.error(CONNECTION_EXCEPTION);
		return ResponseMessage.error(CONNECTION_EXCEPTION);
	}

	/**
	 * Sets the cookie store.
	 *
	 * @param cookielist the new cookie store
	 */
	public static void setCookieStore(List<BasicClientCookie> cookielist) {
		for (BasicClientCookie cookie : cookielist) {
			UdunWalletClient.cookieStore.addCookie(cookie);
		}
	}

	/**
	 * Creates the cookie.
	 *
	 * @param cookielist the cookielist
	 */
	public static void createCookie(List<BasicClientCookie> cookielist) {
		for (BasicClientCookie cookie : cookielist) {
			UdunWalletClient.cookieStore.addCookie(cookie);
		}
	}

}
