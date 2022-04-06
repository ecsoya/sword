package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxWithdrawalRequest.
 */
public class ZbxWithdrawalRequest extends ZbxRequest {

	/**
	 * Instantiates a new zbx withdrawal request.
	 *
	 * @param address the address
	 * @param memo    the memo
	 * @param orderNo the order no
	 * @param amount  the amount
	 * @param symbol  the symbol
	 * @param chain   the chain
	 */
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
