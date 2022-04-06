package com.github.ecsoya.sword.common.config.datasource;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * The Class DynamicDataSource.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/**
	 * Instantiates a new dynamic data source.
	 *
	 * @param defaultTargetDataSource the default target data source
	 * @param targetDataSources       the target data sources
	 */
	public DynamicDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources) {
		super.setDefaultTargetDataSource(defaultTargetDataSource);
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	/**
	 * Determine current lookup key.
	 *
	 * @return the object
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceContextHolder.getDataSourceType();
	}
}