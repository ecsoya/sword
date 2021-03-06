package com.github.ecsoya.sword.framework.config;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * The Class I18nConfig.
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

	/**
	 * Locale resolver.
	 *
	 * @return the locale resolver
	 */
	@Bean
	public LocaleResolver localeResolver() {
		final SessionLocaleResolver slr = new SessionLocaleResolver();
		// 默认语言
		slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return slr;
	}

	/**
	 * Locale change interceptor.
	 *
	 * @return the locale change interceptor
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		final LocaleChangeInterceptor lci = new HeaderLocaleChangeInterceptor();
		// 参数名
		lci.setParamName("lang");
		return lci;
	}

	/**
	 * Adds the interceptors.
	 *
	 * @param registry the registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	/**
	 * The Class HeaderLocaleChangeInterceptor.
	 */
	public static final class HeaderLocaleChangeInterceptor extends LocaleChangeInterceptor {

		/**
		 * Pre handle.
		 *
		 * @param request  the request
		 * @param response the response
		 * @param handler  the handler
		 * @return true, if successful
		 * @throws ServletException the servlet exception
		 */
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws ServletException {
			String newLocale = request.getHeader(getParamName());
			if (newLocale != null) {
				LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
				if (localeResolver == null) {
					throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
				}
				try {
					localeResolver.setLocale(request, response, parseLocaleValue(newLocale));
				} catch (IllegalArgumentException ex) {
					if (isIgnoreInvalidLocale()) {
						if (logger.isDebugEnabled()) {
							logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + ex.getMessage());
						}
					} else {
						throw ex;
					}
				}
				return true;
			}
			return super.preHandle(request, response, handler);
		}

		/**
		 * Parses the locale value.
		 *
		 * @param localeValue the locale value
		 * @return the locale
		 */
		@Override
		protected Locale parseLocaleValue(String localeValue) {
			if ("cn".equalsIgnoreCase(localeValue) || "zh".equalsIgnoreCase(localeValue) || "chn".equals(localeValue)) {
				return Locale.SIMPLIFIED_CHINESE;
			}
			return super.parseLocaleValue(localeValue);
		}
	}
}