package com.github.ecsoya.sword.generator.util;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import com.github.ecsoya.sword.common.constant.Constants;

/**
 * The Class VelocityInitializer.
 */
public class VelocityInitializer {

	/**
	 * Inits the velocity.
	 */
	public static void initVelocity() {
		final Properties p = new Properties();
		try {
			// 加载classpath目录下的vm文件
			p.setProperty("file.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			// 定义字符集
			p.setProperty(RuntimeConstants.ENCODING_DEFAULT, Constants.UTF8);
			// p.setProperty(RuntimeConstants.OUTPUT_ENCODING, Constants.UTF8);
			p.setProperty("directive.foreach.counter.name", "velocityCount");
			p.setProperty("directive.foreach.counter.initial.value", "0");
			// 初始化Velocity引擎，指定配置Properties
			Velocity.init(p);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
