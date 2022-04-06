package com.github.ecsoya.sword.message.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * The Class MessageConfig.
 */
@Configuration
public class MessageConfig {

	/**
	 * Server endpoint exporter.
	 *
	 * @return the server endpoint exporter
	 */
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
