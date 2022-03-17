package com.github.ecsoya.sword.zbx.config;

public class ZbxTickerConfig {

	private String oneUrl = "https://api.zbx.one/data/api/v1/getTicker";

	private String plusUrl = "https://api.hexhk.com/data/api/v1/getTicker";

	private String market = "market";

	public String getOneUrl() {
		return oneUrl;
	}

	public void setOneUrl(String oneUrl) {
		this.oneUrl = oneUrl;
	}

	public String getPlusUrl() {
		return plusUrl;
	}

	public void setPlusUrl(String plusUrl) {
		this.plusUrl = plusUrl;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

}
