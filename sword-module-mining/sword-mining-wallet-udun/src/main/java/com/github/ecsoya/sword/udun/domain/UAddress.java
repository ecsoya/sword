package com.github.ecsoya.sword.udun.domain;

import com.alibaba.fastjson.JSON;

/**
 * The Class UAddress.
 */
public class UAddress {

	/** The address. */
	private String address;

	/** The coin type. */
	private int coinType;

	/**
	 * Parses the.
	 *
	 * @param json the json
	 * @return the u address
	 */
	public static UAddress parse(String json) {
		return JSON.parseObject(json, UAddress.class);
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the coin type.
	 *
	 * @return the coin type
	 */
	public int getCoinType() {
		return coinType;
	}

	/**
	 * Sets the coin type.
	 *
	 * @param coinType the new coin type
	 */
	public void setCoinType(int coinType) {
		this.coinType = coinType;
	}
}
