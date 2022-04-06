package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxCheckDepositAddressRequest.
 */
public class ZbxCheckDepositAddressRequest extends ZbxRequest {

	/**
	 * Instantiates a new zbx check deposit address request.
	 *
	 * @param address the address
	 * @param memo    the memo
	 * @param symbol  the symbol
	 */
	public ZbxCheckDepositAddressRequest(String address, String memo, String symbol) {
		super(symbol);
		addParam("address", address);
		addParam("memo", memo);
	}

}
