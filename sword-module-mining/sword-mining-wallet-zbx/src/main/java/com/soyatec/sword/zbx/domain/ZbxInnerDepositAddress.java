package com.soyatec.sword.zbx.domain;

/**
 *
 * ZBX内部地址
 *
 * @author ecsoya
 *
 */
public class ZbxInnerDepositAddress extends ZbxResponse {

	private String symbol;

	private Integer isInner; // 是否内部充值地址 0否 1是

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Integer getIsInner() {
		return isInner;
	}

	public void setIsInner(Integer isInner) {
		this.isInner = isInner;
	}

	public boolean isInner() {
		return this.isInner == 1;
	}
}
