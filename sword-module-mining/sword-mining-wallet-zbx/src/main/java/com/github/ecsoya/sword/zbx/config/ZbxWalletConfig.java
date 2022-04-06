package com.github.ecsoya.sword.zbx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The Class ZbxWalletConfig.
 */
@Configuration
@ConfigurationProperties("wallet.zbx")
public class ZbxWalletConfig {

	/** The ticker. */
	private ZbxTickerConfig ticker;

	/** The url. */
	private String url = "https://test.zbx.one";

	/** The access key. */
	private String accessKey = "8e03db41-68ae-4445-92f1-d6e7f88e27a6";

	/** The secret key. */
	private String secretKey = "f4c4dd54f42d7dcb1f3081c507a7927d87b8cc23";

	/** The get deposit address. */
	private String getDepositAddress = "/exchange-main/open/v1/test/getDepositAddress";

	/** The withdrawal. */
	private String withdrawal = "/exchange-main/open/v1/test/withdrawal";

	/** The check deposit address. */
	private String checkDepositAddress = "/exchange-main/open/v1/test/checkDepositAddress";

	/** The get user fund. */
	private String getUserFund = "/exchange-main/open/v1/test/getUserFund";

	/**
	 * Gets the ticker.
	 *
	 * @return the ticker
	 */
	public ZbxTickerConfig getTicker() {
		return ticker;
	}

	/**
	 * Sets the ticker.
	 *
	 * @param ticker the new ticker
	 */
	public void setTicker(ZbxTickerConfig ticker) {
		this.ticker = ticker;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the access key.
	 *
	 * @return the access key
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Sets the access key.
	 *
	 * @param accessKey the new access key
	 */
	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	/**
	 * Gets the secret key.
	 *
	 * @return the secret key
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * Sets the secret key.
	 *
	 * @param secretKey the new secret key
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	/**
	 * Gets the gets the deposit address.
	 *
	 * @return the gets the deposit address
	 */
	public String getGetDepositAddress() {
		return getDepositAddress;
	}

	/**
	 * Sets the gets the deposit address.
	 *
	 * @param getDepositAddress the new gets the deposit address
	 */
	public void setGetDepositAddress(String getDepositAddress) {
		this.getDepositAddress = getDepositAddress;
	}

	/**
	 * Gets the withdrawal.
	 *
	 * @return the withdrawal
	 */
	public String getWithdrawal() {
		return withdrawal;
	}

	/**
	 * Sets the withdrawal.
	 *
	 * @param withdrawal the new withdrawal
	 */
	public void setWithdrawal(String withdrawal) {
		this.withdrawal = withdrawal;
	}

	/**
	 * Gets the check deposit address.
	 *
	 * @return the check deposit address
	 */
	public String getCheckDepositAddress() {
		return checkDepositAddress;
	}

	/**
	 * Sets the check deposit address.
	 *
	 * @param checkDepositAddress the new check deposit address
	 */
	public void setCheckDepositAddress(String checkDepositAddress) {
		this.checkDepositAddress = checkDepositAddress;
	}

	/**
	 * Gets the gets the user fund.
	 *
	 * @return the gets the user fund
	 */
	public String getGetUserFund() {
		return getUserFund;
	}

	/**
	 * Sets the gets the user fund.
	 *
	 * @param getUserFund the new gets the user fund
	 */
	public void setGetUserFund(String getUserFund) {
		this.getUserFund = getUserFund;
	}

}
