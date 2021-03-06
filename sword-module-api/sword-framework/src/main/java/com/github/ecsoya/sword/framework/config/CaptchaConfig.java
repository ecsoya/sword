package com.github.ecsoya.sword.framework.config;

import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER;
import static com.google.code.kaptcha.Constants.KAPTCHA_BORDER_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_HEIGHT;
import static com.google.code.kaptcha.Constants.KAPTCHA_IMAGE_WIDTH;
import static com.google.code.kaptcha.Constants.KAPTCHA_NOISE_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_NOISE_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_OBSCURIFICATOR_IMPL;
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_CONFIG_KEY;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_IMPL;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * The Class CaptchaConfig.
 */
@Configuration
public class CaptchaConfig {

	/**
	 * Gets the kaptcha bean.
	 *
	 * @return the kaptcha bean
	 */
	@Bean(name = "captchaProducer")
	public DefaultKaptcha getKaptchaBean() {
		final DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		final Properties properties = new Properties();
		// 是否有边框 默认为true 我们可以自己设置yes，no
		properties.setProperty(KAPTCHA_BORDER, "yes");
		properties.setProperty(KAPTCHA_BORDER_COLOR, GlobalConfig.getAdminColor());
		// 验证码文本字符颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, GlobalConfig.getAdminColor());
		// 验证码图片宽度 默认为200
		properties.setProperty(KAPTCHA_IMAGE_WIDTH, "190");
		// 验证码图片高度 默认为50
		properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "60");
		// 验证码文本字符大小 默认为40
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "42");
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "14");
		// KAPTCHA_SESSION_KEY
		properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCode");
		// 验证码文本字符长度 默认为5
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		// 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
		properties.setProperty(KAPTCHA_NOISE_COLOR, "white");
		// 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple
		// 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
		// 阴影com.google.code.kaptcha.impl.ShadowGimpy
		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.WaterRipple");
		// properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL,
		// "com.google.code.kaptcha.impl.ShadowGimpy");
		final Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

	/**
	 * Gets the kaptcha bean math.
	 *
	 * @return the kaptcha bean math
	 */
	@Bean(name = "captchaProducerMath")
	public DefaultKaptcha getKaptchaBeanMath() {
		final DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		final Properties properties = new Properties();
		// 是否有边框 默认为true 我们可以自己设置yes，no
		properties.setProperty(KAPTCHA_BORDER, "yes");
		// 边框颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_BORDER_COLOR, GlobalConfig.getAdminColor());
		// 验证码文本字符颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, GlobalConfig.getAdminColor());
		// 验证码图片宽度 默认为200
		properties.setProperty(KAPTCHA_IMAGE_WIDTH, "230");
		// 验证码图片高度 默认为50
		properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "70");
		// 验证码文本字符大小 默认为40
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "42");
		// KAPTCHA_SESSION_KEY
		properties.setProperty(KAPTCHA_SESSION_CONFIG_KEY, "kaptchaCodeMath");
		// 验证码文本生成器
		properties.setProperty(KAPTCHA_TEXTPRODUCER_IMPL,
				"com.github.ecsoya.sword.framework.config.KaptchaTextCreator");
		// 验证码文本字符间距 默认为2
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_SPACE, "14");
		// 验证码文本字符长度 默认为5
		properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
		// 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
		properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier");
		// 验证码噪点颜色 默认为Color.BLACK
		properties.setProperty(KAPTCHA_NOISE_COLOR, "white");
		// 干扰实现类
		properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
		// 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple
		// 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy
		// 阴影com.google.code.kaptcha.impl.ShadowGimpy
//		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.WaterRipple");
//		properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.FishEyeGimpy");
		final Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

	/**
	 * Apply color.
	 *
	 * @param producer the producer
	 * @return the producer
	 */
	public static Producer applyColor(Producer producer) {
		if (producer instanceof DefaultKaptcha) {
			final Config config = ((DefaultKaptcha) producer).getConfig();
			if (config != null) {
				config.getProperties().put(KAPTCHA_BORDER_COLOR, GlobalConfig.getAdminColor());
				config.getProperties().put(KAPTCHA_TEXTPRODUCER_FONT_COLOR, GlobalConfig.getAdminColor());
			}
		}
		return producer;
	}
}
