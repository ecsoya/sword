package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxUserFund.
 */
public class ZbxUserFund extends ZbxResponse {

	/** The xc. */
	private String[] xc;

	/**
	 * Gets the xc.
	 *
	 * @return the xc
	 */
	public String[] getXc() {
		return xc;
	}

	/**
	 * Sets the xc.
	 *
	 * @param xc the new xc
	 */
	public void setXc(String[] xc) {
		this.xc = xc;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		if (xc == null || xc.length != 3) {
			return null;
		}
		return Double.parseDouble(xc[0].trim());
	}

	/**
	 * Gets the available.
	 *
	 * @return the available
	 */
	public Double getAvailable() {
		if (xc == null || xc.length != 3) {
			return null;
		}
		return Double.parseDouble(xc[1].trim());
	}

	/**
	 * Gets the frozen.
	 *
	 * @return the frozen
	 */
	public Double getFrozen() {
		if (xc == null || xc.length != 3) {
			return null;
		}
		return Double.parseDouble(xc[2].trim());
	}
}
