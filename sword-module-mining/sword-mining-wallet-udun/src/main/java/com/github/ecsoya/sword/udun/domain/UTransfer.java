package com.github.ecsoya.sword.udun.domain;

import java.math.BigDecimal;

public class UTransfer {

	// 商户号
	private String merchantId;
	// 转账地址
	private String address;
	// 币种类型
	private String mainCoinType;
	// 子币种类型
	private String coinType;
	// 转账数量
	private BigDecimal amount;
	// 转账回调地址
	private String callUrl;
	// 提币申请单号
	private String businessId;
	// 备注
	private String memo;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMainCoinType() {
		return mainCoinType;
	}

	public void setMainCoinType(String mainCoinType) {
		this.mainCoinType = mainCoinType;
	}

	public String getCoinType() {
		return coinType;
	}

	public void setCoinType(String coinType) {
		this.coinType = coinType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCallUrl() {
		return callUrl;
	}

	public void setCallUrl(String callUrl) {
		this.callUrl = callUrl;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
