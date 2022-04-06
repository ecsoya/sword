package com.github.ecsoya.sword.common.utils.uuid;

/**
 * The Class IdUtils.
 */
public class IdUtils {

	/**
	 * Random UUID.
	 *
	 * @return the string
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Simple UUID.
	 *
	 * @return the string
	 */
	public static String simpleUUID() {
		return UUID.randomUUID().toString(true);
	}

	/**
	 * Fast UUID.
	 *
	 * @return the string
	 */
	public static String fastUUID() {
		return UUID.fastUUID().toString();
	}

	/**
	 * Fast simple UUID.
	 *
	 * @return the string
	 */
	public static String fastSimpleUUID() {
		return UUID.fastUUID().toString(true);
	}
}
