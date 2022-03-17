package com.github.ecsoya.sword.framework.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.spring.boot.autoconfigure.properties.DruidStatProperties;
import com.alibaba.druid.util.Utils;
import com.github.ecsoya.sword.common.config.datasource.DynamicDataSource;
import com.github.ecsoya.sword.common.enums.DataSourceType;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.framework.config.properties.DruidProperties;

/**
 * druid 配置多数据源
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Configuration
public class DruidConfig {
	@Bean
	@ConfigurationProperties("spring.datasource.druid.master")
	public DataSource masterDataSource(DruidProperties druidProperties) {
		final DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		return druidProperties.dataSource(dataSource);
	}

	@Bean
	@ConfigurationProperties("spring.datasource.druid.slave")
	@ConditionalOnProperty(prefix = "spring.datasource.druid.slave", name = "enabled", havingValue = "true")
	public DataSource slaveDataSource(DruidProperties druidProperties) {
		final DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
		return druidProperties.dataSource(dataSource);
	}

	@Bean(name = "dynamicDataSource")
	@Primary
	public DynamicDataSource dataSource(DataSource masterDataSource) {
		final Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.MASTER.name(), masterDataSource);
		setDataSource(targetDataSources, DataSourceType.SLAVE.name(), "slaveDataSource");
		return new DynamicDataSource(masterDataSource, targetDataSources);
	}

	/**
	 * 设置数据源
	 *
	 * @param targetDataSources 备选数据源集合
	 * @param sourceName        数据源名称
	 * @param beanName          bean名称
	 */
	public void setDataSource(Map<Object, Object> targetDataSources, String sourceName, String beanName) {
		try {
			final DataSource dataSource = SpringUtils.getBean(beanName);
			targetDataSources.put(sourceName, dataSource);
		} catch (final Exception e) {
		}
	}

	/**
	 * 去除监控页面底部的广告
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@ConditionalOnProperty(name = "spring.datasource.druid.statViewServlet.enabled", havingValue = "true")
	public FilterRegistrationBean removeDruidFilterRegistrationBean(DruidStatProperties properties) {
		// 获取web监控页面的参数
		final DruidStatProperties.StatViewServlet config = properties.getStatViewServlet();
		// 提取common.js的配置路径
		final String pattern = config.getUrlPattern() != null ? config.getUrlPattern() : "/druid/*";
		final String commonJsPattern = pattern.replaceAll("\\*", "js/common.js");
		final String filePath = "support/http/resources/js/common.js";
		// 创建filter进行过滤
		final Filter filter = new Filter() {
			@Override
			public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
					throws IOException, ServletException {
				chain.doFilter(request, response);
				// 重置缓冲区，响应头不会被重置
				response.resetBuffer();
				// 获取common.js
				String text = Utils.readFromResource(filePath);
				// 正则替换banner, 除去底部的广告信息
				text = text.replaceAll("<a.*?banner\"></a><br/>", "");
				text = text.replaceAll("powered.*?shrek.wang</a>", "");
				response.getWriter().write(text);
			}

			@Override
			public void destroy() {
			}
		};
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns(commonJsPattern);
		return registrationBean;
	}
}
