package com.github.ecsoya.sword.udun.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The Class SignUtil.
 */
public class SignUtil {

	/**
	 * Sign.
	 *
	 * @param key       the key
	 * @param timestamp the timestamp
	 * @param nonce     the nonce
	 * @param type      the type
	 * @param body      the body
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String sign(String key, String timestamp, String nonce, String type, String body) throws Exception {
		return DigestUtils.md5Hex(body + key + nonce + timestamp + type).toLowerCase();
	}

	/**
	 * Sign.
	 *
	 * @param key       the key
	 * @param timestamp the timestamp
	 * @param nonce     the nonce
	 * @param body      the body
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String sign(String key, String timestamp, String nonce, String body) throws Exception {
		String raw = body + key + nonce + timestamp;
		String sign = DigestUtils.md5Hex(raw).toLowerCase();
		return sign;
	}

	/**
	 * Sign.
	 *
	 * @param key       the key
	 * @param timestamp the timestamp
	 * @param nonce     the nonce
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String sign(String key, String timestamp, String nonce) throws Exception {
		return DigestUtils.md5Hex(key + nonce + timestamp).toLowerCase();
	}
}
