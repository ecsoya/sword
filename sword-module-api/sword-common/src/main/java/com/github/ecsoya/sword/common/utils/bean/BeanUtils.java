package com.github.ecsoya.sword.common.utils.bean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class BeanUtils.
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

	/** The Constant BEAN_METHOD_PROP_INDEX. */
	private static final int BEAN_METHOD_PROP_INDEX = 3;

	/** The Constant GET_PATTERN. */
	private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

	/** The Constant SET_PATTERN. */
	private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

	/**
	 * Copy bean prop.
	 *
	 * @param dest the dest
	 * @param src  the src
	 */
	public static void copyBeanProp(Object dest, Object src) {
		try {
			copyProperties(src, dest);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the setter methods.
	 *
	 * @param obj the obj
	 * @return the setter methods
	 */
	public static List<Method> getSetterMethods(Object obj) {
		// setter方法列表
		final List<Method> setterMethods = new ArrayList<Method>();

		// 获取所有方法
		final Method[] methods = obj.getClass().getMethods();

		// 查找setter方法

		for (final Method method : methods) {
			final Matcher m = SET_PATTERN.matcher(method.getName());
			if (m.matches() && (method.getParameterTypes().length == 1)) {
				setterMethods.add(method);
			}
		}
		// 返回setter方法列表
		return setterMethods;
	}

	/**
	 * Gets the getter methods.
	 *
	 * @param obj the obj
	 * @return the getter methods
	 */

	public static List<Method> getGetterMethods(Object obj) {
		// getter方法列表
		final List<Method> getterMethods = new ArrayList<Method>();
		// 获取所有方法
		final Method[] methods = obj.getClass().getMethods();
		// 查找getter方法
		for (final Method method : methods) {
			final Matcher m = GET_PATTERN.matcher(method.getName());
			if (m.matches() && (method.getParameterTypes().length == 0)) {
				getterMethods.add(method);
			}
		}
		// 返回getter方法列表
		return getterMethods;
	}

	/**
	 * Checks if is method prop equals.
	 *
	 * @param m1 the m 1
	 * @param m2 the m 2
	 * @return true, if is method prop equals
	 */

	public static boolean isMethodPropEquals(String m1, String m2) {
		return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
	}
}
