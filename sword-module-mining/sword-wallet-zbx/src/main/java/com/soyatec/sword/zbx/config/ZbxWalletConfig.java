package com.soyatec.sword.zbx.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("wallet.zbx")
public class ZbxWalletConfig {

	private ZbxTickerConfig ticker;

	private String url = "https://test.zbx.one";

	private String accessKey = "8e03db41-68ae-4445-92f1-d6e7f88e27a6";

	private String secretKey = "f4c4dd54f42d7dcb1f3081c507a7927d87b8cc23";

	private String getDepositAddress = "/exchange-main/open/v1/test/getDepositAddress";

	private String withdrawal = "/exchange-main/open/v1/test/withdrawal";

	private String checkDepositAddress = "/exchange-main/open/v1/test/checkDepositAddress";

	private String getUserFund = "/exchange-main/open/v1/test/getUserFund";

	public ZbxTickerConfig getTicker() {
		return ticker;
	}

	public void setTicker(ZbxTickerConfig ticker) {
		this.ticker = ticker;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getGetDepositAddress() {
		return getDepositAddress;
	}

	public void setGetDepositAddress(String getDepositAddress) {
		this.getDepositAddress = getDepositAddress;
	}

	public String getWithdrawal() {
		return withdrawal;
	}

	public void setWithdrawal(String withdrawal) {
		this.withdrawal = withdrawal;
	}

	public String getCheckDepositAddress() {
		return checkDepositAddress;
	}

	public void setCheckDepositAddress(String checkDepositAddress) {
		this.checkDepositAddress = checkDepositAddress;
	}

	public String getGetUserFund() {
		return getUserFund;
	}

	public void setGetUserFund(String getUserFund) {
		this.getUserFund = getUserFund;
	}

}
