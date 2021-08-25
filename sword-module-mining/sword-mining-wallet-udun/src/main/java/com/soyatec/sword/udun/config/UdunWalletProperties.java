package com.soyatec.sword.udun.config;

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

import com.soyatec.sword.common.utils.StringUtils;

@Configuration
@ConfigurationProperties(prefix = "wallet.udun")
public class UdunWalletProperties {

	private String gateway;

	private String merchantId;
	private String merchantKey;

	private String walletId;

	private int connectTimeout = 1000;
	private int requestTimeout = 1000;
	private Boolean redirectEnabled = true;

	private String callbackUrl;

	private String addressLocation;

	private UdunWalletAddress addresses;

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getRequestTimeout() {
		return requestTimeout;
	}

	public void setRequestTimeout(int requestTimeout) {
		this.requestTimeout = requestTimeout;
	}

	public Boolean getRedirectEnabled() {
		return redirectEnabled;
	}

	public void setRedirectEnabled(Boolean redirectEnabled) {
		this.redirectEnabled = redirectEnabled;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getAddressLocation() {
		return addressLocation;
	}

	public void setAddressLocation(String addressLocation) {
		this.addressLocation = addressLocation;
	}

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

	public void setAddresses(UdunWalletAddress addresses) {
		this.addresses = addresses;
	}

}
