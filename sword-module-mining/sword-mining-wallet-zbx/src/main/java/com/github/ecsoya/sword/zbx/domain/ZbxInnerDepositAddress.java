package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxInnerDepositAddress.
 */
public class ZbxInnerDepositAddress extends ZbxResponse {

	/** The symbol. */
	private String symbol;

	/** The is inner. */
	private Integer isInner; // 是否内部充值地址 0否 1是

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the checks if is inner.
	 *
	 * @return the checks if is inner
	 */
	public Integer getIsInner() {
		return isInner;
	}

	/**
	 * Sets the checks if is inner.
	 *
	 * @param isInner the new checks if is inner
	 */
	public void setIsInner(Integer isInner) {
		this.isInner = isInner;
	}

	/**
	 * Checks if is inner.
	 *
	 * @return true, if is inner
	 */
	public boolean isInner() {
		return this.isInner == 1;
	}
}
