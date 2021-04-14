package com.soyatec.sword.zbx.domain;

/**
 * ZbxResponse
 *
 * @author ecsoya
 *
 */
public abstract class ZbxResponse {

	private String address;

	private String memo;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
