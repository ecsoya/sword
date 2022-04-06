package com.github.ecsoya.sword.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.ecsoya.sword.common.enums.DataSourceType;

/**
 * The Interface DataSource.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DataSource {

	/**
	 * Value.
	 *
	 * @return the data source type
	 */
	public DataSourceType value() default DataSourceType.MASTER;
}
