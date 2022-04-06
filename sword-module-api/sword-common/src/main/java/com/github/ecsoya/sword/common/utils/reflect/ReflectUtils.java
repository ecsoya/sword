package com.github.ecsoya.sword.common.utils.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.poi.ss.usermodel.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;

/**
 * The Class ReflectUtils.
 */
@SuppressWarnings("rawtypes")
public class ReflectUtils {

	/** The Constant SETTER_PREFIX. */
	private static final String SETTER_PREFIX = "set";

	/** The Constant GETTER_PREFIX. */
	private static final String GETTER_PREFIX = "get";

	/** The Constant CGLIB_CLASS_SEPARATOR. */
	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	/** The logger. */
	private static Logger logger = LoggerFactory.getLogger(ReflectUtils.class);

	/**
	 * Invoke getter.
	 *
	 * @param <E>          the element type
	 * @param obj          the obj
	 * @param propertyName the property name
	 * @return the e
	 */
	@SuppressWarnings("unchecked")
	public static <E> E invokeGetter(Object obj, String propertyName) {
		Object object = obj;
		for (final String name : StringUtils.split(propertyName, ".")) {
			final String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return (E) object;
	}

	/**
	 * Invoke setter.
	 *
	 * @param <E>          the element type
	 * @param obj          the obj
	 * @param propertyName the property name
	 * @param value        the value
	 */
	public static <E> void invokeSetter(Object obj, String propertyName, E value) {
		Object object = obj;
		final String[] names = StringUtils.split(propertyName, ".");
		for (int i = 0; i < names.length; i++) {
			if (i < names.length - 1) {
				final String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			} else {
				final String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}

	/**
	 * Gets the field value.
	 *
	 * @param <E>       the element type
	 * @param obj       the obj
	 * @param fieldName the field name
	 * @return the field value
	 */
	@SuppressWarnings("unchecked")
	public static <E> E getFieldValue(final Object obj, final String fieldName) {
		final Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
			return null;
		}
		E result = null;
		try {
			result = (E) field.get(obj);
		} catch (final IllegalAccessException e) {
			logger.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * Sets the field value.
	 *
	 * @param <E>       the element type
	 * @param obj       the obj
	 * @param fieldName the field name
	 * @param value     the value
	 */
	public static <E> void setFieldValue(final Object obj, final String fieldName, final E value) {
		final Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			// throw new IllegalArgumentException("在 [" + obj.getClass() + "] 中，没有找到 [" +
			// fieldName + "] 字段 ");
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + fieldName + "] 字段 ");
			return;
		}
		try {
			field.set(obj, value);
		} catch (final IllegalAccessException e) {
			logger.error("不可能抛出的异常: {}", e.getMessage());
		}
	}

	/**
	 * Invoke method.
	 *
	 * @param <E>            the element type
	 * @param obj            the obj
	 * @param methodName     the method name
	 * @param parameterTypes the parameter types
	 * @param args           the args
	 * @return the e
	 */
	@SuppressWarnings("unchecked")
	public static <E> E invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		if (obj == null || methodName == null) {
			return null;
		}
		final Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
			return null;
		}
		try {
			return (E) method.invoke(obj, args);
		} catch (final Exception e) {
			final String msg = "method: " + method + ", obj: " + obj + ", args: " + args + "";
			throw convertReflectionExceptionToUnchecked(msg, e);
		}
	}

	/**
	 * Invoke method by name.
	 *
	 * @param <E>        the element type
	 * @param obj        the obj
	 * @param methodName the method name
	 * @param args       the args
	 * @return the e
	 */
	@SuppressWarnings("unchecked")
	public static <E> E invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
		final Method method = getAccessibleMethodByName(obj, methodName, args.length);
		if (method == null) {
			// 如果为空不报错，直接返回空。
			logger.debug("在 [" + obj.getClass() + "] 中，没有找到 [" + methodName + "] 方法 ");
			return null;
		}
		try {
			// 类型转换（将参数数据类型转换为目标方法参数类型）
			final Class<?>[] cs = method.getParameterTypes();
			for (int i = 0; i < cs.length; i++) {
				if (args[i] != null && !args[i].getClass().equals(cs[i])) {
					if (cs[i] == String.class) {
						args[i] = Convert.toStr(args[i]);
						if (StringUtils.endsWith((String) args[i], ".0")) {
							args[i] = StringUtils.substringBefore((String) args[i], ".0");
						}
					} else if (cs[i] == Integer.class) {
						args[i] = Convert.toInt(args[i]);
					} else if (cs[i] == Long.class) {
						args[i] = Convert.toLong(args[i]);
					} else if (cs[i] == Double.class) {
						args[i] = Convert.toDouble(args[i]);
					} else if (cs[i] == Float.class) {
						args[i] = Convert.toFloat(args[i]);
					} else if (cs[i] == Date.class) {
						if (args[i] instanceof String) {
							args[i] = DateUtils.parseDate(args[i]);
						} else {
							args[i] = DateUtil.getJavaDate((Double) args[i]);
						}
					} else if (cs[i] == boolean.class || cs[i] == Boolean.class) {
						args[i] = Convert.toBool(args[i]);
					}
				}
			}
			return (E) method.invoke(obj, args);
		} catch (final Exception e) {
			final String msg = "method: " + method + ", obj: " + obj + ", args: " + args + "";
			throw convertReflectionExceptionToUnchecked(msg, e);
		}
	}

	/**
	 * Gets the accessible field.
	 *
	 * @param obj       the obj
	 * @param fieldName the field name
	 * @return the accessible field
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		// 为空不报错。直接返回 null
		if (obj == null) {
			return null;
		}
		Validate.notBlank(fieldName, "fieldName can't be blank");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				final Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (final NoSuchFieldException e) {
				continue;
			}
		}
		return null;
	}

	/**
	 * Gets the accessible method.
	 *
	 * @param obj            the obj
	 * @param methodName     the method name
	 * @param parameterTypes the parameter types
	 * @return the accessible method
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		// 为空不报错。直接返回 null
		if (obj == null) {
			return null;
		}
		Validate.notBlank(methodName, "methodName can't be blank");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			try {
				final Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (final NoSuchMethodException e) {
				continue;
			}
		}
		return null;
	}

	/**
	 * Gets the accessible method by name.
	 *
	 * @param obj        the obj
	 * @param methodName the method name
	 * @param argsNum    the args num
	 * @return the accessible method by name
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName, int argsNum) {
		// 为空不报错。直接返回 null
		if (obj == null) {
			return null;
		}
		Validate.notBlank(methodName, "methodName can't be blank");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			final Method[] methods = searchType.getDeclaredMethods();
			for (final Method method : methods) {
				if (method.getName().equals(methodName) && method.getParameterTypes().length == argsNum) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * Make accessible.
	 *
	 * @param method the method
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * Make accessible.
	 *
	 * @param field the field
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * Gets the class genric type.
	 *
	 * @param <T>   the generic type
	 * @param clazz the clazz
	 * @return the class genric type
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 * Gets the class genric type.
	 *
	 * @param clazz the clazz
	 * @param index the index
	 * @return the class genric type
	 */
	public static Class getClassGenricType(final Class clazz, final int index) {
		final Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			logger.debug(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		final Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.debug("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
					+ params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.debug(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}

	/**
	 * Gets the user class.
	 *
	 * @param instance the instance
	 * @return the user class
	 */
	public static Class<?> getUserClass(Object instance) {
		if (instance == null) {
			throw new RuntimeException("Instance must not be null");
		}
		final Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			final Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}

	/**
	 * Convert reflection exception to unchecked.
	 *
	 * @param msg the msg
	 * @param e   the e
	 * @return the runtime exception
	 */
	public static RuntimeException convertReflectionExceptionToUnchecked(String msg, Exception e) {
		if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException
				|| e instanceof NoSuchMethodException) {
			return new IllegalArgumentException(msg, e);
		} else if (e instanceof InvocationTargetException) {
			return new RuntimeException(msg, ((InvocationTargetException) e).getTargetException());
		}
		return new RuntimeException(msg, e);
	}

	/**
	 * Gets the all fields.
	 *
	 * @param type the type
	 * @return the all fields
	 */
	public static List<Field> getAllFields(Class<?> type) {
		if (type == null || Object.class == type) {
			return Collections.emptyList();
		}
		final List<Field> fields = new ArrayList<>();
		final Field[] declaredFields = type.getDeclaredFields();
		for (final Field field : declaredFields) {
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			fields.add(field);
		}
		final List<Field> superFields = getAllFields(type.getSuperclass());
		if (!superFields.isEmpty()) {
			fields.addAll(superFields);
		}
		return fields;
	}
}
