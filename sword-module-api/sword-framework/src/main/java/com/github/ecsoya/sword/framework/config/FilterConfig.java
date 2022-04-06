package com.github.ecsoya.sword.framework.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.xss.XssFilter;

/**
 * The Class FilterConfig.
 */
@Configuration
public class FilterConfig {

	/** The enabled. */
	@Value("${xss.enabled}")
	private String enabled;

	/** The excludes. */
	@Value("${xss.excludes}")
	private String excludes;

	/** The url patterns. */
	@Value("${xss.urlPatterns}")
	private String urlPatterns;

	/**
	 * Xss filter registration.
	 *
	 * @return the filter registration bean
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	public FilterRegistrationBean xssFilterRegistration() {
		final FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new XssFilter());
		registration.addUrlPatterns(org.apache.commons.lang3.StringUtils.split(urlPatterns, ","));
		registration.setName("xssFilter");
		registration.setOrder(Integer.MAX_VALUE);
		final Map<String, String> initParameters = new HashMap<String, String>();
		initParameters.put("excludes", excludes);
		initParameters.put("enabled", enabled);
		registration.setInitParameters(initParameters);
		return registration;
	}
}
