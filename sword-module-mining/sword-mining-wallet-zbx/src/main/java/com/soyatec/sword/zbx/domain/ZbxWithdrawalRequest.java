package com.soyatec.sword.zbx.domain;

public class ZbxWithdrawalRequest extends ZbxRequest {

	public ZbxWithdrawalRequest(String address, String memo, String orderNo, double amount, String symbol,
			String chain) {
		super(symbol);
		addParam("address", address);
		addParam("memo", memo);
		addParam("orderNo", orderNo);
		addParam("amount", amount);
		addParam("symbol", symbol);
		addParam("chain", chain);
	}

}
