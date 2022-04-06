package com.github.ecsoya.sword.common.utils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.github.ecsoya.sword.common.core.text.StrFormatter;

/**
 * The Class StringUtils.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

	/** The Constant NULLSTR. */
	private static final String NULLSTR = "";

	/** The Constant SEPARATOR. */
	private static final char SEPARATOR = '_';

	/** The Constant RANDOM_HOLDER. */
	private static final String RANDOM_HOLDER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/** The Constant MAIL_REGEX. */
	private static final String MAIL_REGEX = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/** The random. */
	private static SecureRandom random = new SecureRandom();

	/**
	 * Random str.
	 *
	 * @param length the length
	 * @return the string
	 */
	public static String randomStr(int length) {
		final Random random = new Random();
		final StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			final int number = random.nextInt(62);
			sb.append(RANDOM_HOLDER.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * Random num.
	 *
	 * @param length the length
	 * @return the string
	 */
	public static String randomNum(int length) {
		String num = "";
		for (int i = 0; i < length; i++) {
			num = num + String.valueOf((int) Math.floor(Math.random() * 9 + 1));
		}
		return num;
	}

	/**
	 * Random salt.
	 *
	 * @param numBytes the num bytes
	 * @return the byte[]
	 */
	public static byte[] randomSalt(int numBytes) {
		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	/**
	 * Nvl.
	 *
	 * @param <T>          the generic type
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the t
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}

	/**
	 * Checks if is empty.
	 *
	 * @param coll the coll
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Collection<?> coll) {
		return isNull(coll) || coll.isEmpty();
	}

	/**
	 * Checks if is empty.
	 *
	 * @param obj the obj
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || obj.equals("");
	}

	/**
	 * Checks if is not empty.
	 *
	 * @param coll the coll
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}

	/**
	 * Checks if is empty.
	 *
	 * @param objects the objects
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Object[] objects) {
		return isNull(objects) || (objects.length == 0);
	}

	/**
	 * Checks if is not empty.
	 *
	 * @param objects the objects
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(Object[] objects) {
		return !isEmpty(objects);
	}

	/**
	 * Checks if is empty.
	 *
	 * @param map the map
	 * @return true, if is empty
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return isNull(map) || map.isEmpty();
	}

	/**
	 * Checks if is not empty.
	 *
	 * @param map the map
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return !isEmpty(map);
	}

	/**
	 * Checks if is empty.
	 *
	 * @param str the str
	 * @return true, if is empty
	 */
	public static boolean isEmpty(String str) {
		return isNull(str) || NULLSTR.equals(str.trim());
	}

	/**
	 * Checks if is not empty.
	 *
	 * @param str the str
	 * @return true, if is not empty
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * Checks if is null.
	 *
	 * @param object the object
	 * @return true, if is null
	 */
	public static boolean isNull(Object object) {
		return object == null;
	}

	/**
	 * Checks if is not null.
	 *
	 * @param object the object
	 * @return true, if is not null
	 */
	public static boolean isNotNull(Object object) {
		return !isNull(object);
	}

	/**
	 * Checks if is array.
	 *
	 * @param object the object
	 * @return true, if is array
	 */
	public static boolean isArray(Object object) {
		return isNotNull(object) && object.getClass().isArray();
	}

	/**
	 * Trim.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String trim(String str) {
		return (str == null ? "" : str.trim());
	}

	/**
	 * Substring.
	 *
	 * @param str   the str
	 * @param start the start
	 * @return the string
	 */
	public static String substring(final String str, int start) {
		if (str == null) {
			return NULLSTR;
		}

		if (start < 0) {
			start = str.length() + start;
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return NULLSTR;
		}

		return str.substring(start);
	}

	/**
	 * Substring.
	 *
	 * @param str   the str
	 * @param start the start
	 * @param end   the end
	 * @return the string
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return NULLSTR;
		}

		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}

		if (end > str.length()) {
			end = str.length();
		}

		if (start > end) {
			return NULLSTR;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	/**
	 * Format.
	 *
	 * @param template the template
	 * @param params   the params
	 * @return the string
	 */
	public static String format(String template, Object... params) {
		if (isEmpty(params) || isEmpty(template)) {
			return template;
		}
		return StrFormatter.format(template, params);
	}

	/**
	 * Format all.
	 *
	 * @param template the template
	 * @param param    the param
	 * @return the string
	 */
	public static String formatAll(String template, Object param) {
		if (isEmpty(template)) {
			return template;
		}
		if (param == null) {
			return format(template);
		}
		int count = StringUtils.countMatches(template, "{}");
		Object[] params = new Object[count];
		Arrays.fill(params, param);
		return format(template, params);
	}

	/**
	 * To under score case.
	 *
	 * @param str the str
	 * @return the string
	 */
	public static String toUnderScoreCase(String str) {
		if (str == null) {
			return null;
		}
		final StringBuilder sb = new StringBuilder();
		// 前置字符是否大写
		boolean preCharIsUpperCase = true;
		// 当前字符是否大写
		boolean curreCharIsUpperCase = true;
		// 下一字符是否大写
		boolean nexteCharIsUpperCase = true;
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			if (i > 0) {
				preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
			} else {
				preCharIsUpperCase = false;
			}

			curreCharIsUpperCase = Character.isUpperCase(c);

			if (i < (str.length() - 1)) {
				nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
			}

			if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
				sb.append(SEPARATOR);
			} else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
				sb.append(SEPARATOR);
			}
			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * In string ignore case.
	 *
	 * @param str  the str
	 * @param strs the strs
	 * @return true, if successful
	 */
	public static boolean inStringIgnoreCase(String str, String... strs) {
		if (str != null && strs != null) {
			for (final String s : strs) {
				if (str.equalsIgnoreCase(trim(s))) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Convert to camel case.
	 *
	 * @param name the name
	 * @return the string
	 */
	public static String convertToCamelCase(String name) {
		final StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将首字母大写
			return name.substring(0, 1).toUpperCase() + name.substring(1);
		}
		// 用下划线将原始字符串分割
		final String[] camels = name.split("_");
		for (final String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 首字母大写
			result.append(camel.substring(0, 1).toUpperCase());
			result.append(camel.substring(1).toLowerCase());
		}
		return result.toString();
	}

	/**
	 * To camel case.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = s.toLowerCase();
		final StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Cast.
	 *
	 * @param <T> the generic type
	 * @param obj the obj
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		return (T) obj;
	}

	/**
	 * Array to string.
	 *
	 * @param array the array
	 * @return the string
	 */
	public static String arrayToString(Object[] array) {
		if (array == null || array.length == 0) {
			return "";
		}
		final StringBuffer buf = new StringBuffer();
		for (final Object object : array) {
			if (buf.length() != 0) {
				buf.append(",");
			}
			buf.append(object.toString());
		}
		return new String(buf);
	}

	/**
	 * To string.
	 *
	 * @param object the object
	 * @return the string
	 */
	public static String toString(Object object) {
		return object == null ? null : object.toString();
	}

	/**
	 * Replace format.
	 *
	 * @param str          the str
	 * @param replacements the replacements
	 * @return the string
	 */
	public static String replaceFormat(String str, Object... replacements) {
		if (str == null || str.equals("")) {
			return str;
		}
		String result = str;
		if (replacements != null && replacements.length > 0) {
			for (int i = 0; i < replacements.length; i++) {
				result = org.apache.commons.lang3.StringUtils.replace(result, "{" + i + "}",
						replacements[i].toString());
			}
		}
		return result;
	}

	/**
	 * Matches.
	 *
	 * @param regex  the regex
	 * @param string the string
	 * @return true, if successful
	 */
	public static boolean matches(String regex, String string) {
		if (StringUtils.isEmpty(regex) || isEmpty(string)) {
			return false;
		}
		return string.matches(regex);
	}

	/**
	 * Encrypt password.
	 *
	 * @param username the username
	 * @param password the password
	 * @param salt     the salt
	 * @return the string
	 */
	public static String encryptPassword(String username, String password, String salt) {
		return new Md5Hash(username + password + salt).toHex();
	}

	/**
	 * Checks if is valid email.
	 *
	 * @param email the email
	 * @return true, if is valid email
	 */
	public static boolean isValidEmail(String email) {
		if (isEmpty(email)) {
			return false;
		}
		final Pattern p = Pattern.compile(MAIL_REGEX);
		final Matcher m = p.matcher(email);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println(encryptPassword("14", "111111", "764758"));
	}
}
