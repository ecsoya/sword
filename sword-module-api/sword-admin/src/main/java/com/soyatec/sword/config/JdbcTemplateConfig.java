package com.soyatec.sword.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.soyatec.sword.common.config.datasource.DynamicDataSource;
import com.soyatec.sword.config.jdbc.JdbcTemplateSupport;

@Configuration
public class JdbcTemplateConfig {

	@Bean
	public JdbcTemplateSupport jdbcTemplateSupport(DynamicDataSource dataSource) {
		return new JdbcTemplateSupport(dataSource, true);
	}
}
