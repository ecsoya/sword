package com.github.ecsoya.sword.common.utils.security;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Md5Utils.
 */
public class Md5Utils {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

	/**
	 * Md 5.
	 *
	 * @param s the s
	 * @return the byte[]
	 */
	private static byte[] md5(String s) {
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(s.getBytes("UTF-8"));
			final byte[] messageDigest = algorithm.digest();
			return messageDigest;
		} catch (final Exception e) {
			log.error("MD5 Error...", e);
		}
		return null;
	}

	/**
	 * To hex.
	 *
	 * @param hash the hash
	 * @return the string
	 */
	private static final String toHex(byte hash[]) {
		if (hash == null) {
			return null;
		}
		final StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if ((hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * Hash.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String hash(String s) {
		try {
			return new String(toHex(md5(s)).getBytes("UTF-8"), "UTF-8");
		} catch (final Exception e) {
			log.error("not supported charset...{}", e);
			return s;
		}
	}
}
