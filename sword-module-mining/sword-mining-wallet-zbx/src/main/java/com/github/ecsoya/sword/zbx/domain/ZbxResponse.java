package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxResponse.
 */
public abstract class ZbxResponse {

	/** The address. */
	private String address;

	/** The memo. */
	private String memo;

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
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * Sets the memo.
	 *
	 * @param memo the new memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
