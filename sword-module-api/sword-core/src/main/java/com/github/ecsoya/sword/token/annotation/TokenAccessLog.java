package com.github.ecsoya.sword.token.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface TokenAccessLog.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAccessLog {

	/**
	 * Title.
	 *
	 * @return the string
	 */
	public String title() default "";

	/**
	 * Description.
	 *
	 * @return the string
	 */
	public String description() default "";

	/**
	 * Checks if is save request data.
	 *
	 * @return true, if is save request data
	 */
	public boolean isSaveRequestData() default true;
}
