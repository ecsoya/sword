package com.soyatec.sword.udun.domain;

import com.alibaba.fastjson.JSON;

public class UAddress {
	private String address;
	private int coinType;

	public static UAddress parse(String json) {
		return JSON.parseObject(json, UAddress.class);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCoinType() {
		return coinType;
	}

	public void setCoinType(int coinType) {
		this.coinType = coinType;
	}
}
