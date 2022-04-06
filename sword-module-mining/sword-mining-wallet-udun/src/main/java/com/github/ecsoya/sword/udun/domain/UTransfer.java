package com.github.ecsoya.sword.udun.domain;

import java.math.BigDecimal;

/**
 * The Class UTransfer.
 */
public class UTransfer {

	/** The merchant id. */
	// 商户号
	private String merchantId;

	/** The address. */
	// 转账地址
	private String address;

	/** The main coin type. */
	// 币种类型
	private String mainCoinType;

	/** The coin type. */
	// 子币种类型
	private String coinType;

	/** The amount. */
	// 转账数量
	private BigDecimal amount;

	/** The call url. */
	// 转账回调地址
	private String callUrl;

	/** The business id. */
	// 提币申请单号
	private String businessId;

	/** The memo. */
	// 备注
	private String memo;

	/**
	 * Gets the merchant id.
	 *
	 * @return the merchant id
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * Sets the merchant id.
	 *
	 * @param merchantId the new merchant id
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the call url.
	 *
	 * @return the call url
	 */
	public String getCallUrl() {
		return callUrl;
	}

	/**
	 * Sets the call url.
	 *
	 * @param callUrl the new call url
	 */
	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
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
