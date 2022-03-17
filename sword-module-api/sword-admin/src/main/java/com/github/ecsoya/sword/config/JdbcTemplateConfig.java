package com.github.ecsoya.sword.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.config.datasource.DynamicDataSource;
import com.github.ecsoya.sword.config.jdbc.JdbcTemplateSupport;

@Configuration
public class JdbcTemplateConfig {

	@Bean
	public JdbcTemplateSupport jdbcTemplateSupport(DynamicDataSource dataSource) {
		return new JdbcTemplateSupport(dataSource, true);
	}
}
