package com.github.ecsoya.sword.common.utils.spring;

import java.util.Objects;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class SpringUtils.
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

	/** The bean factory. */
	private static ConfigurableListableBeanFactory beanFactory;

	/** The application context. */
	private static ApplicationContext applicationContext;

	/**
	 * Post process bean factory.
	 *
	 * @param beanFactory the bean factory
	 * @throws BeansException the beans exception
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		SpringUtils.beanFactory = beanFactory;
	}

	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the new application context
	 * @throws BeansException the beans exception
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	/**
	 * Gets the bean.
	 *
	 * @param <T>  the generic type
	 * @param name the name
	 * @return the bean
	 * @throws BeansException the beans exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) beanFactory.getBean(name);
	}

	/**
	 * Gets the bean.
	 *
	 * @param <T> the generic type
	 * @param clz the clz
	 * @return the bean
	 * @throws BeansException the beans exception
	 */
	public static <T> T getBean(Class<T> clz) throws BeansException {
		final T result = beanFactory.getBean(clz);
		return result;
	}

	/**
	 * Contains bean.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public static boolean containsBean(String name) {
		return beanFactory.containsBean(name);
	}

	/**
	 * Checks if is singleton.
	 *
	 * @param name the name
	 * @return true, if is singleton
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.isSingleton(name);
	}

	/**
	 * Gets the type.
	 *
	 * @param name the name
	 * @return the type
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getType(name);
	}

	/**
	 * Gets the aliases.
	 *
	 * @param name the name
	 * @return the aliases
	 * @throws NoSuchBeanDefinitionException the no such bean definition exception
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return beanFactory.getAliases(name);
	}

	/**
	 * Gets the aop proxy.
	 *
	 * @param <T>     the generic type
	 * @param invoker the invoker
	 * @return the aop proxy
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getAopProxy(T invoker) {
		return (T) AopContext.currentProxy();
	}

	/**
	 * Gets the active profiles.
	 *
	 * @return the active profiles
	 */
	public static String[] getActiveProfiles() {
		return applicationContext.getEnvironment().getActiveProfiles();
	}

	/**
	 * Gets the active profile.
	 *
	 * @return the active profile
	 */
	public static String getActiveProfile() {
		final String[] activeProfiles = getActiveProfiles();
		return StringUtils.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
	}

	/**
	 * Test profile.
	 *
	 * @param profile the profile
	 * @return true, if successful
	 */
	public static boolean testProfile(String profile) {
		return Objects.equals(profile, getActiveProfile());
	}
}
