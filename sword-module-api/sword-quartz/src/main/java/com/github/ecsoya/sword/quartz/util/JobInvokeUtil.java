package com.github.ecsoya.sword.quartz.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * 任务执行工具
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class JobInvokeUtil {
	/**
	 * 执行方法
	 *
	 * @param sysJob 系统任务
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
	 * 调用任务方法
	 *
	 * @param bean         目标对象
	 * @param methodName   方法名称
	 * @param methodParams 方法参数
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
	 * 校验是否为为class包名
	 *
	 * @param invokeTarget 名称
	 * @return true是 false否
	 */
	public static boolean isValidClassName(String invokeTarget) {
		return org.apache.commons.lang3.StringUtils.countMatches(invokeTarget, ".") > 1;
	}

	/**
	 * 获取bean名称
	 *
	 * @param invokeTarget 目标字符串
	 * @return bean名称
	 */
	public static String getBeanName(String invokeTarget) {
		final String beanName = org.apache.commons.lang3.StringUtils.substringBefore(invokeTarget, "(");
		return org.apache.commons.lang3.StringUtils.substringBeforeLast(beanName, ".");
	}

	/**
	 * 获取bean方法
	 *
	 * @param invokeTarget 目标字符串
	 * @return method方法
	 */
	public static String getMethodName(String invokeTarget) {
		final String methodName = org.apache.commons.lang3.StringUtils.substringBefore(invokeTarget, "(");
		return org.apache.commons.lang3.StringUtils.substringAfterLast(methodName, ".");
	}

	/**
	 * 获取method方法参数相关列表
	 *
	 * @param invokeTarget 目标字符串
	 * @return method方法相关参数列表
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
	 * 获取参数类型
	 *
	 * @param methodParams 参数相关列表
	 * @return 参数类型列表
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
	 * 获取参数值
	 *
	 * @param methodParams 参数相关列表
	 * @return 参数值列表
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
