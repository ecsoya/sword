package com.github.ecsoya.sword.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * The Class GlobalCorsConfig.
 */
@Configuration
public class GlobalCorsConfig {
//	@Bean
//	public CorsFilter corsFilter() {
//		CorsConfiguration config = new CorsConfiguration();
//		config.addAllowedOriginPattern("*");
//		config.setAllowCredentials(true);
//		config.addAllowedMethod("*");
//		config.addAllowedHeader("*");
//
//		UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//		configSource.registerCorsConfiguration("/**", config);
//
//		return new CorsFilter(configSource);
//	}

	/**
	 * Cors filter.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOriginPattern("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
		final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(0);
		return bean;
	}
}