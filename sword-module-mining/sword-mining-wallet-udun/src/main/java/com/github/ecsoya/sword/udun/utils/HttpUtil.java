package com.github.ecsoya.sword.udun.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * The Class HttpUtil.
 */
public class HttpUtil {

	/**
	 * Wrapper params.
	 *
	 * @param key  the key
	 * @param body the body
	 * @return the map
	 * @throws Exception the exception
	 */
	public static Map<String, String> wrapperParams(String key, String body) throws Exception {
		String timestamp = System.currentTimeMillis() + "";
		String nonce = String.valueOf(getNonceString(8));
		String sign = SignUtil.sign(key, timestamp, nonce, body);
		Map<String, String> map = new HashMap<>();
		map.put("body", body);
		map.put("sign", sign);
		map.put("timestamp", timestamp);
		map.put("nonce", nonce);
		return map;
	}

	/**
	 * Gets the nonce string.
	 *
	 * @param len the len
	 * @return the nonce string
	 */
	public static String getNonceString(int len) {
		String seed = "1234567890";
		StringBuffer tmp = new StringBuffer();
		for (int i = 0; i < len; i++) {
			tmp.append(seed.charAt(getRandomNumber(0, 9)));
		}
		return tmp.toString();
	}

	/**
	 * Gets the random number.
	 *
	 * @param from the from
	 * @param to   the to
	 * @return the random number
	 */
	public static int getRandomNumber(int from, int to) {
		float a = from + (to - from) * (new Random().nextFloat());
		int b = (int) a;
		return ((a - b) > 0.5 ? 1 : 0) + b;
	}

	/**
	 * Check sign.
	 *
	 * @param key       the key
	 * @param timestamp the timestamp
	 * @param nonce     the nonce
	 * @param body      the body
	 * @param sign      the sign
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public static boolean checkSign(String key, String timestamp, String nonce, String body, String sign)
			throws Exception {
		String checkSign = SignUtil.sign(key, timestamp, nonce, body);
		return checkSign.equals(sign);
	}
}
