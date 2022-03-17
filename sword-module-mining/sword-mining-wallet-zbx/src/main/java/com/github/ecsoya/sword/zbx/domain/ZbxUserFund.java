package com.github.ecsoya.sword.zbx.domain;

public class ZbxUserFund extends ZbxResponse {

	private String[] xc;

	public String[] getXc() {
		return xc;
	}

	public void setXc(String[] xc) {
		this.xc = xc;
	}

	public Double getAmount() {
		if (xc == null || xc.length != 3) {
			return null;
		}
		return Double.parseDouble(xc[0].trim());
	}

	public Double getAvailable() {
		if (xc == null || xc.length != 3) {
			return null;
		}
		return Double.parseDouble(xc[1].trim());
	}

	public Double getFrozen() {
		if (xc == null || xc.length != 3) {
			return null;
		}
		return Double.parseDouble(xc[2].trim());
	}
}
