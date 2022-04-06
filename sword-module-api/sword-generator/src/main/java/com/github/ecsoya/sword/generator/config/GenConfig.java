package com.github.ecsoya.sword.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * The Class GenConfig.
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = { "classpath:generator.yml" })
public class GenConfig {

	/** The author. */
	public static String author;

	/** The package name. */
	public static String packageName;

	/** The auto remove pre. */
	public static boolean autoRemovePre;

	/** The table prefix. */
	public static String tablePrefix;

	/** The domain package prefix. */
	private static String domainPackagePrefix;

	/**
	 * Gets the 作者.
	 *
	 * @return the 作者
	 */
	public static String getAuthor() {
		return author;
	}

	/**
	 * Sets the 作者.
	 *
	 * @param author the new 作者
	 */
	@Value("${author}")
	public void setAuthor(String author) {
		GenConfig.author = author;
	}

	/**
	 * Gets the 生成包路径.
	 *
	 * @return the 生成包路径
	 */
	public static String getPackageName() {
		return packageName;
	}

	/**
	 * Sets the 生成包路径.
	 *
	 * @param packageName the new 生成包路径
	 */
	@Value("${packageName}")
	public void setPackageName(String packageName) {
		GenConfig.packageName = packageName;
	}

	/**
	 * Gets the 自动去除表前缀，默认是false.
	 *
	 * @return the 自动去除表前缀，默认是false
	 */
	public static boolean getAutoRemovePre() {
		return autoRemovePre;
	}

	/**
	 * Sets the 自动去除表前缀，默认是false.
	 *
	 * @param autoRemovePre the new 自动去除表前缀，默认是false
	 */
	@Value("${autoRemovePre}")
	public void setAutoRemovePre(boolean autoRemovePre) {
		GenConfig.autoRemovePre = autoRemovePre;
	}

	/**
	 * Gets the 表前缀(类名不会包含表前缀).
	 *
	 * @return the 表前缀(类名不会包含表前缀)
	 */
	public static String getTablePrefix() {
		return tablePrefix;
	}

	/**
	 * Sets the 表前缀(类名不会包含表前缀).
	 *
	 * @param tablePrefix the new 表前缀(类名不会包含表前缀)
	 */
	@Value("${tablePrefix}")
	public void setTablePrefix(String tablePrefix) {
		GenConfig.tablePrefix = tablePrefix;
	}

	/**
	 * Gets the domain package prefix.
	 *
	 * @return the domain package prefix
	 */
	public static String getDomainPackagePrefix() {
		return domainPackagePrefix;
	}

	/**
	 * Sets the domain package prefix.
	 *
	 * @param domainPackagePrefix the new domain package prefix
	 */
	@Value("${domainPackagePrefix}")
	public void setDomainPackagePrefix(String domainPackagePrefix) {
		GenConfig.domainPackagePrefix = domainPackagePrefix;
	}
}
