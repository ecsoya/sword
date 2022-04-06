package com.github.ecsoya.sword.zbx.config;

/**
 * The Class ZbxTickerConfig.
 */
public class ZbxTickerConfig {

	/** The one url. */
	private String oneUrl = "https://api.zbx.one/data/api/v1/getTicker";

	/** The plus url. */
	private String plusUrl = "https://api.hexhk.com/data/api/v1/getTicker";

	/** The market. */
	private String market = "market";

	/**
	 * Gets the one url.
	 *
	 * @return the one url
	 */
	public String getOneUrl() {
		return oneUrl;
	}

	/**
	 * Sets the one url.
	 *
	 * @param oneUrl the new one url
	 */
	public void setOneUrl(String oneUrl) {
		this.oneUrl = oneUrl;
	}

	/**
	 * Gets the plus url.
	 *
	 * @return the plus url
	 */
	public String getPlusUrl() {
		return plusUrl;
	}

	/**
	 * Sets the plus url.
	 *
	 * @param plusUrl the new plus url
	 */
	public void setPlusUrl(String plusUrl) {
		this.plusUrl = plusUrl;
	}

	/**
	 * Gets the market.
	 *
	 * @return the market
	 */
	public String getMarket() {
		return market;
	}

	/**
	 * Sets the market.
	 *
	 * @param market the new market
	 */
	public void setMarket(String market) {
		this.market = market;
	}

}
