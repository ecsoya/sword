package com.github.ecsoya.sword.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface DataScope.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

	/**
	 * Dept alias.
	 *
	 * @return the string
	 */
	public String deptAlias() default "";

	/**
	 * User alias.
	 *
	 * @return the string
	 */
	public String userAlias() default "";

	/**
	 * User id column.
	 *
	 * @return the string
	 */
	public String userIdColumn() default "user_id";
}
