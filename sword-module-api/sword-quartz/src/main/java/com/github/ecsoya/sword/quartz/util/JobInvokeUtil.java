package com.github.ecsoya.sword.quartz.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * The Class JobInvokeUtil.
 */
public class JobInvokeUtil {

	/**
	 * Invoke method.
	 *
	 * @param sysJob the sys job
	 * @throws Exception the exception
	 */
	public static void invokeMethod(SysJob sysJob) throws Exception {
		final String invokeTarget = sysJob.getInvokeTarget();
		final String beanName = getBeanName(invokeTarget);
		final String methodName = getMethodName(invokeTarget);
		final List<Object[]> methodParams = getMethodParams(invokeTarget);

		if (!isValidClassName(beanName)) {
			final Object bean = SpringUtils.getBean(beanName);
			invokeMethod(bean, methodName, methodParams);
		} else {
			final Object bean = Class.forName(beanName).newInstance();
			invokeMethod(bean, methodName, methodParams);
		}
	}

	/**
	 * Invoke method.
	 *
	 * @param bean         the bean
	 * @param methodName   the method name
	 * @param methodParams the method params
	 * @throws NoSuchMethodException     the no such method exception
	 * @throws SecurityException         the security exception
	 * @throws IllegalAccessException    the illegal access exception
	 * @throws IllegalArgumentException  the illegal argument exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (StringUtils.isNotNull(methodParams) && methodParams.size() > 0) {
			final Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
			method.invoke(bean, getMethodParamsValue(methodParams));
		} else {
			final Method method = bean.getClass().getDeclaredMethod(methodName);
			method.invoke(bean);
		}
	}

	/**
	 * Checks if is valid class name.
	 *
	 * @param invokeTarget the invoke target
	 * @return true, if is valid class name
	 */
	public static boolean isValidClassName(String invokeTarget) {
		return org.apache.commons.lang3.StringUtils.countMatches(invokeTarget, ".") > 1;
	}

	/**
	 * Gets the bean name.
	 *
	 * @param invokeTarget the invoke target
	 * @return the bean name
	 */
	public static String getBeanName(String invokeTarget) {
		final String beanName = org.apache.commons.lang3.StringUtils.substringBefore(invokeTarget, "(");
		return org.apache.commons.lang3.StringUtils.substringBeforeLast(beanName, ".");
	}

	/**
	 * Gets the method name.
	 *
	 * @param invokeTarget the invoke target
	 * @return the method name
	 */
	public static String getMethodName(String invokeTarget) {
		final String methodName = org.apache.commons.lang3.StringUtils.substringBefore(invokeTarget, "(");
		return org.apache.commons.lang3.StringUtils.substringAfterLast(methodName, ".");
	}

	/**
	 * Gets the method params.
	 *
	 * @param invokeTarget the invoke target
	 * @return the method params
	 */
	public static List<Object[]> getMethodParams(String invokeTarget) {
		final String methodStr = org.apache.commons.lang3.StringUtils.substringBetween(invokeTarget, "(", ")");
		if (StringUtils.isEmpty(methodStr)) {
			return null;
		}
		final String[] methodParams = methodStr.split(",");
		final List<Object[]> classs = new LinkedList<>();
		for (int i = 0; i < methodParams.length; i++) {
			final String str = org.apache.commons.lang3.StringUtils.trimToEmpty(methodParams[i]);
			// String字符串类型，包含'
			if (org.apache.commons.lang3.StringUtils.contains(str, "'")) {
				classs.add(new Object[] { org.apache.commons.lang3.StringUtils.replace(str, "'", ""), String.class });
			}
			// boolean布尔类型，等于true或者false
			else if (org.apache.commons.lang3.StringUtils.equals(str, "true")
					|| org.apache.commons.lang3.StringUtils.equalsIgnoreCase(str, "false")) {
				classs.add(new Object[] { Boolean.valueOf(str), Boolean.class });
			}
			// long长整形，包含L
			else if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, "L")) {
				classs.add(new Object[] {
						Long.valueOf(org.apache.commons.lang3.StringUtils.replaceIgnoreCase(str, "L", "")),
						Long.class });
			}
			// double浮点类型，包含D
			else if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(str, "D")) {
				classs.add(new Object[] {
						Double.valueOf(org.apache.commons.lang3.StringUtils.replaceIgnoreCase(str, "D", "")),
						Double.class });
			}
			// 其他类型归类为整形
			else {
				classs.add(new Object[] { Integer.valueOf(str), Integer.class });
			}
		}
		return classs;
	}

	/**
	 * Gets the method params type.
	 *
	 * @param methodParams the method params
	 * @return the method params type
	 */
	public static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
		final Class<?>[] classs = new Class<?>[methodParams.size()];
		int index = 0;
		for (final Object[] os : methodParams) {
			classs[index] = (Class<?>) os[1];
			index++;
		}
		return classs;
	}

	/**
	 * Gets the method params value.
	 *
	 * @param methodParams the method params
	 * @return the method params value
	 */
	public static Object[] getMethodParamsValue(List<Object[]> methodParams) {
		final Object[] classs = new Object[methodParams.size()];
		int index = 0;
		for (final Object[] os : methodParams) {
			classs[index] = os[0];
			index++;
		}
		return classs;
	}
}
