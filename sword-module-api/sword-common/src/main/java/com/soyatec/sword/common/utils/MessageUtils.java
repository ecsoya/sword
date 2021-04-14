package com.soyatec.sword.common.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.soyatec.sword.common.utils.spring.SpringUtils;

/**
 * 获取i18n资源文件
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class MessageUtils {
	/**
	 * 根据消息键和参数 获取消息 委托给spring messageSource
	 *
	 * @param code 消息键
	 * @param args 参数
	 * @return 获取国际化翻译值
	 */
	public static String message(String code, Object... args) {
		final MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	/**
	 * 根据消息键和参数 获取消息 委托给spring messageSource
	 *
	 * @param code 消息键
	 * @param args 参数
	 * @return 获取国际化翻译值
	 */
	public static String message(Locale locale, String code, Object... args) {
		final MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
		return messageSource.getMessage(code, args, locale);
	}
}
