package com.github.ecsoya.sword.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The Interface Excels.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excels {

	/**
	 * Value.
	 *
	 * @return the excel[]
	 */
	Excel[] value();
}