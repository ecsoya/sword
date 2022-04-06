package com.github.ecsoya.sword.common.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.github.ecsoya.sword.common.utils.spring.SpringUtils;

/**
 * The Class MessageUtils.
 */
public class MessageUtils {

	/**
	 * Message.
	 *
	 * @param code the code
	 * @param args the args
	 * @return the string
	 */
	public static String message(String code, Object... args) {
		final MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

	/**
	 * Message.
	 *
	 * @param locale the locale
	 * @param code   the code
	 * @param args   the args
	 * @return the string
	 */
	public static String message(Locale locale, String code, Object... args) {
		final MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
		return messageSource.getMessage(code, args, locale);
	}
}
