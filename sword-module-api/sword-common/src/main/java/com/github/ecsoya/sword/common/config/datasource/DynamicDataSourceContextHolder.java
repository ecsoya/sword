package com.github.ecsoya.sword.common.config.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DynamicDataSourceContextHolder.
 */
public class DynamicDataSourceContextHolder {

	/** The Constant log. */
	public static final Logger log = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);

	/** The Constant CONTEXT_HOLDER. */
	private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

	/**
	 * Sets the data source type.
	 *
	 * @param dsType the new data source type
	 */
	public static void setDataSourceType(String dsType) {
		log.info("切换到{}数据源", dsType);
		CONTEXT_HOLDER.set(dsType);
	}

	/**
	 * Gets the data source type.
	 *
	 * @return the data source type
	 */
	public static String getDataSourceType() {
		return CONTEXT_HOLDER.get();
	}

	/**
	 * Clear data source type.
	 */
	public static void clearDataSourceType() {
		CONTEXT_HOLDER.remove();
	}
}
