package com.soyatec.sword.udun.config;

import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.soyatec.sword.udun.http.client.UdunWalletClient;

@Component
public class UdunConfig {

	@Autowired
	private UdunWalletProperties properties;

	@Bean
	public UdunWalletClient udunClient() {
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(properties.getConnectTimeout())
				.setConnectionRequestTimeout(properties.getRequestTimeout())
				.setRedirectsEnabled(properties.getRedirectEnabled()).build();
		return new UdunWalletClient(properties.getGateway(), properties.getMerchantId(), properties.getMerchantKey(),
				requestConfig);
	}
}
