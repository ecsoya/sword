package com.github.ecsoya.sword.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.enums.OperatorType;

/**
 * The Interface Log.
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

	/**
	 * Title.
	 *
	 * @return the string
	 */
	public String title() default "";

	/**
	 * Business type.
	 *
	 * @return the business type
	 */
	public BusinessType businessType() default BusinessType.OTHER;

	/**
	 * Operator type.
	 *
	 * @return the operator type
	 */
	public OperatorType operatorType() default OperatorType.MANAGE;

	/**
	 * Checks if is save request data.
	 *
	 * @return true, if is save request data
	 */
	public boolean isSaveRequestData() default true;
}
