package com.github.ecsoya.sword.token.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义操作日志记录注解
 *
 * @author AngryRED (jin.liu@soyatec.com)
 *
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenAccessLog {
	/**
	 * 模块
	 */
	public String title() default "";

	public String description() default "";

	/**
	 * 是否保存请求的参数
	 */
	public boolean isSaveRequestData() default true;
}
