package com.github.ecsoya.sword.zbx.domain;

public class ZbxCheckDepositAddressRequest extends ZbxRequest {

	public ZbxCheckDepositAddressRequest(String address, String memo, String symbol) {
		super(symbol);
		addParam("address", address);
		addParam("memo", memo);
	}

}
