package com.soyatec.sword.udun.domain;

import com.soyatec.sword.udun.constants.CoinType;

public class UTrade extends WalletNotify {
	// 币种类型
	private String mainCoinType;
	// 代币类型，erc20为合约地址
	private String coinType;
	// 提币申请单号
	private String businessId;

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

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public boolean isErcToken() {
		return !this.mainCoinType.equalsIgnoreCase(this.coinType)
				&& this.mainCoinType.equalsIgnoreCase(CoinType.Ethereum.getCode());
	}

	public boolean isUsdt() {
		return this.mainCoinType.equalsIgnoreCase(CoinType.Bitcoin.getCode()) && this.coinType.equalsIgnoreCase("31");
	}
}
