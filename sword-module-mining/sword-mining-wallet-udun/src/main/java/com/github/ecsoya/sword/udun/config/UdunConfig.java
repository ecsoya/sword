package com.github.ecsoya.sword.udun.config;

import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.udun.http.client.UdunWalletClient;

/**
 * The Class UdunConfig.
 */
@Component
public class UdunConfig {

	/** The properties. */
	@Autowired
	private UdunWalletProperties properties;

	/**
	 * Udun client.
	 *
	 * @return the udun wallet client
	 */
	@Bean
	public UdunWalletClient udunClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(properties.getConnectTimeout())
				.setConnectionRequestTimeout(properties.getRequestTimeout())
				.setRedirectsEnabled(properties.getRedirectEnabled()).build();
		return new UdunWalletClient(properties.getGateway(), properties.getMerchantId(), properties.getMerchantKey(),
				requestConfig);
	}
}
