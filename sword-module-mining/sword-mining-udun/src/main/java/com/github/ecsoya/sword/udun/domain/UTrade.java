package com.github.ecsoya.sword.udun.domain;

import com.github.ecsoya.sword.udun.constants.CoinType;

/**
 * The Class UTrade.
 */
public class UTrade extends WalletNotify {

	/** The main coin type. */
	// 币种类型
	private String mainCoinType;

	/** The coin type. */
	// 代币类型，erc20为合约地址
	private String coinType;

	/** The business id. */
	// 提币申请单号
	private String businessId;

	/**
	 * Gets the main coin type.
	 *
	 * @return the main coin type
	 */
	public String getMainCoinType() {
		return mainCoinType;
	}

	/**
	 * Sets the main coin type.
	 *
	 * @param mainCoinType the new main coin type
	 */
	public void setMainCoinType(String mainCoinType) {
		this.mainCoinType = mainCoinType;
	}

	/**
	 * Gets the coin type.
	 *
	 * @return the coin type
	 */
	public String getCoinType() {
		return coinType;
	}

	/**
	 * Sets the coin type.
	 *
	 * @param coinType the new coin type
	 */
	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	/**
	 * Gets the business id.
	 *
	 * @return the business id
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * Sets the business id.
	 *
	 * @param businessId the new business id
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	/**
	 * Checks if is erc token.
	 *
	 * @return true, if is erc token
	 */
	public boolean isErcToken() {
		return !this.mainCoinType.equalsIgnoreCase(this.coinType)
				&& this.mainCoinType.equalsIgnoreCase(CoinType.Ethereum.getCode());
	}

	/**
	 * Checks if is usdt.
	 *
	 * @return true, if is usdt
	 */
	public boolean isUsdt() {
		return this.mainCoinType.equalsIgnoreCase(CoinType.Bitcoin.getCode()) && this.coinType.equalsIgnoreCase("31");
	}
}
