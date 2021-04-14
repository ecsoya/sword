package com.soyatec.sword.generator.util;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;

import com.soyatec.sword.common.constant.Constants;

/**
 * VelocityEngine工厂
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class VelocityInitializer {
	/**
	 * 初始化vm方法
	 */
	public static void initVelocity() {
		final Properties p = new Properties();
		try {
			// 加载classpath目录下的vm文件
			p.setProperty("file.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
			// 定义字符集
			p.setProperty(RuntimeConstants.ENCODING_DEFAULT, Constants.UTF8);
			p.setProperty(RuntimeConstants.OUTPUT_ENCODING, Constants.UTF8);
			// 初始化Velocity引擎，指定配置Properties
			Velocity.init(p);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}
}
