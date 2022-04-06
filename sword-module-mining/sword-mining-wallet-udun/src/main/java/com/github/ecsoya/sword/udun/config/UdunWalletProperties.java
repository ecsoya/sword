package com.github.ecsoya.sword.udun.config;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class UdunWalletProperties.
 */
@Configuration
@ConfigurationProperties(prefix = "wallet.udun")
public class UdunWalletProperties {

	/** The gateway. */
	private String gateway;

	/** The merchant id. */
	private String merchantId;

	/** The merchant key. */
	private String merchantKey;

	/** The wallet id. */
	private String walletId;

	/** The connect timeout. */
	private int connectTimeout = 1000;

	/** The request timeout. */
	private int requestTimeout = 1000;

	/** The redirect enabled. */
	private Boolean redirectEnabled = true;

	/** The callback url. */
	private String callbackUrl;

	/** The address location. */
	private String addressLocation;

	/** The addresses. */
	private UdunWalletAddress addresses;

	/**
	 * Gets the gateway.
	 *
	 * @return the gateway
	 */
	public String getGateway() {
		return gateway;
	}

	/**
	 * Sets the gateway.
	 *
	 * @param gateway the new gateway
	 */
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	/**
	 * Gets the merchant id.
	 *
	 * @return the merchant id
	 */
	public String getMerchantId() {
		return merchantId;
	}

	/**
	 * Sets the merchant id.
	 *
	 * @param merchantId the new merchant id
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * Gets the merchant key.
	 *
	 * @return the merchant key
	 */
	public String getMerchantKey() {
		return merchantKey;
	}

	/**
	 * Sets the merchant key.
	 *
	 * @param merchantKey the new merchant key
	 */
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	/**
	 * Gets the connect timeout.
	 *
	 * @return the connect timeout
	 */
	public int getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * Sets the connect timeout.
	 *
	 * @param connectTimeout the new connect timeout
	 */
	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * Gets the request timeout.
	 *
	 * @return the request timeout
	 */
	public int getRequestTimeout() {
		return requestTimeout;
	}

	/**
	 * Sets the request timeout.
	 *
	 * @param requestTimeout the new request timeout
	 */
	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	/**
	 * Gets the redirect enabled.
	 *
	 * @return the redirect enabled
	 */
	public Boolean getRedirectEnabled() {
		return redirectEnabled;
	}

	/**
	 * Sets the redirect enabled.
	 *
	 * @param redirectEnabled the new redirect enabled
	 */
	public void setRedirectEnabled(Boolean redirectEnabled) {
		this.redirectEnabled = redirectEnabled;
	}

	/**
	 * Gets the callback url.
	 *
	 * @return the callback url
	 */
	public String getCallbackUrl() {
		return callbackUrl;
	}

	/**
	 * Sets the callback url.
	 *
	 * @param callbackUrl the new callback url
	 */
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	/**
	 * Gets the wallet id.
	 *
	 * @return the wallet id
	 */
	public String getWalletId() {
		return walletId;
	}

	/**
	 * Sets the wallet id.
	 *
	 * @param walletId the new wallet id
	 */
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	/**
	 * Gets the address location.
	 *
	 * @return the address location
	 */
	public String getAddressLocation() {
		return addressLocation;
	}

	/**
	 * Sets the address location.
	 *
	 * @param addressLocation the new address location
	 */
	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

	/**
	 * Gets the addresses.
	 *
	 * @return the addresses
	 */
	public UdunWalletAddress getAddresses() {
		if (addresses == null && StringUtils.isNotEmpty(addressLocation)) {
			addresses = new UdunWalletAddress();
			YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
			try {
				List<PropertySource<?>> list = loader.load("address", new ClassPathResource(addressLocation));
				for (PropertySource<?> property : list) {
					Object source = property.getSource();
					if (source instanceof Map<?, ?>) {
						Map<?, ?> map = (Map<?, ?>) source;
						Set<?> entrySet = map.entrySet();
						for (Object obj : entrySet) {
							Entry<?, ?> entry = (Entry<?, ?>) obj;
							String key = (String) entry.getKey();
							Object value = entry.getValue();
							String addr = null;
							if (value instanceof OriginTrackedValue) {
								addr = ((OriginTrackedValue) value).getValue().toString();
							} else {
								addr = value.toString();
							}
							if (key.startsWith("address.fil")) {
								addresses.addFil(addr);
							} else if (key.startsWith("address.usdt")) {
								addresses.addUsdt(addr);
							}
						}
					}
				}
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		return addresses;
	}

	/**
	 * Sets the addresses.
	 *
	 * @param addresses the new addresses
	 */
	public void setAddresses(UdunWalletAddress addresses) {
		this.addresses = addresses;
	}

}
