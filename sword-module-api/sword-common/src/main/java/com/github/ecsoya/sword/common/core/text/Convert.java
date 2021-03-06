package com.github.ecsoya.sword.common.core.text;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class Convert.
 */
public class Convert {

	/**
	 * To str.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the string
	 */
	public static String toStr(Object value, String defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		if (value instanceof String) {
			return (String) value;
		}
		return value.toString();
	}

	/**
	 * To str.
	 *
	 * @param value the value
	 * @return the string
	 */
	public static String toStr(Object value) {
		return toStr(value, null);
	}

	/**
	 * To char.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the character
	 */
	public static Character toChar(Object value, Character defaultValue) {
		if (null == value) {
			return defaultValue;
		}
		if (value instanceof Character) {
			return (Character) value;
		}

		final String valueStr = toStr(value, null);
		return StringUtils.isEmpty(valueStr) ? defaultValue : valueStr.charAt(0);
	}

	/**
	 * To char.
	 *
	 * @param value the value
	 * @return the character
	 */
	public static Character toChar(Object value) {
		return toChar(value, null);
	}

	/**
	 * To byte.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the byte
	 */
	public static Byte toByte(Object value, Byte defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Byte) {
			return (Byte) value;
		}
		if (value instanceof Number) {
			return ((Number) value).byteValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Byte.parseByte(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To byte.
	 *
	 * @param value the value
	 * @return the byte
	 */
	public static Byte toByte(Object value) {
		return toByte(value, null);
	}

	/**
	 * To short.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the short
	 */
	public static Short toShort(Object value, Short defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Short) {
			return (Short) value;
		}
		if (value instanceof Number) {
			return ((Number) value).shortValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Short.parseShort(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To short.
	 *
	 * @param value the value
	 * @return the short
	 */
	public static Short toShort(Object value) {
		return toShort(value, null);
	}

	/**
	 * To number.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the number
	 */
	public static Number toNumber(Object value, Number defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Number) {
			return (Number) value;
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return NumberFormat.getInstance().parse(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To number.
	 *
	 * @param value the value
	 * @return the number
	 */
	public static Number toNumber(Object value) {
		return toNumber(value, null);
	}

	/**
	 * To int.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the integer
	 */
	public static Integer toInt(Object value, Integer defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Integer) {
			return (Integer) value;
		}
		if (value instanceof Number) {
			return ((Number) value).intValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To int.
	 *
	 * @param value the value
	 * @return the integer
	 */
	public static Integer toInt(Object value) {
		return toInt(value, null);
	}

	/**
	 * To int array.
	 *
	 * @param str the str
	 * @return the integer[]
	 */
	public static Integer[] toIntArray(String str) {
		return toIntArray(",", str);
	}

	/**
	 * To long array.
	 *
	 * @param str the str
	 * @return the long[]
	 */
	public static Long[] toLongArray(String str) {
		return toLongArray(",", str);
	}

	/**
	 * To int array.
	 *
	 * @param split the split
	 * @param str   the str
	 * @return the integer[]
	 */
	public static Integer[] toIntArray(String split, String str) {
		if (StringUtils.isEmpty(str)) {
			return new Integer[] {};
		}
		String[] arr = str.split(split);
		final Integer[] ints = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			final Integer v = toInt(arr[i], 0);
			ints[i] = v;
		}
		return ints;
	}

	/**
	 * To long array.
	 *
	 * @param split the split
	 * @param str   the str
	 * @return the long[]
	 */
	public static Long[] toLongArray(String split, String str) {
		if (StringUtils.isEmpty(str)) {
			return new Long[] {};
		}
		String[] arr = str.split(split);
		final Long[] longs = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			final Long v = toLong(arr[i], null);
			longs[i] = v;
		}
		return longs;
	}

	/**
	 * To str array.
	 *
	 * @param str the str
	 * @return the string[]
	 */
	public static String[] toStrArray(String str) {
		return toStrArray(",", str);
	}

	/**
	 * To str array.
	 *
	 * @param split the split
	 * @param str   the str
	 * @return the string[]
	 */
	public static String[] toStrArray(String split, String str) {
		return str.split(split);
	}

	/**
	 * To long.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the long
	 */
	public static Long toLong(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Long) {
			return (Long) value;
		}
		if (value instanceof Number) {
			return ((Number) value).longValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			// ?????????????????????
			return new BigDecimal(valueStr.trim()).longValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To long.
	 *
	 * @param value the value
	 * @return the long
	 */
	public static Long toLong(Object value) {
		return toLong(value, null);
	}

	/**
	 * To double.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the double
	 */
	public static Double toDouble(Object value, Double defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Double) {
			return (Double) value;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			// ?????????????????????
			return new BigDecimal(valueStr.trim()).doubleValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To double.
	 *
	 * @param value the value
	 * @return the double
	 */
	public static Double toDouble(Object value) {
		return toDouble(value, null);
	}

	/**
	 * To float.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the float
	 */
	public static Float toFloat(Object value, Float defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Float) {
			return (Float) value;
		}
		if (value instanceof Number) {
			return ((Number) value).floatValue();
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(valueStr.trim());
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To float.
	 *
	 * @param value the value
	 * @return the float
	 */
	public static Float toFloat(Object value) {
		return toFloat(value, null);
	}

	/**
	 * To bool.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the boolean
	 */
	public static Boolean toBool(Object value, Boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Boolean) {
			return (Boolean) value;
		}
		String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		valueStr = valueStr.trim().toLowerCase();
		switch (valueStr) {
		case "true":
			return true;
		case "false":
			return false;
		case "yes":
			return true;
		case "ok":
			return true;
		case "no":
			return false;
		case "1":
			return true;
		case "0":
			return false;
		default:
			return defaultValue;
		}
	}

	/**
	 * To bool.
	 *
	 * @param value the value
	 * @return the boolean
	 */
	public static Boolean toBool(Object value) {
		return toBool(value, null);
	}

	/**
	 * To enum.
	 *
	 * @param <E>          the element type
	 * @param clazz        the clazz
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the e
	 */
	public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value, E defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (clazz.isAssignableFrom(value.getClass())) {
			@SuppressWarnings("unchecked")
			E myE = (E) value;
			return myE;
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Enum.valueOf(clazz, valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To enum.
	 *
	 * @param <E>   the element type
	 * @param clazz the clazz
	 * @param value the value
	 * @return the e
	 */
	public static <E extends Enum<E>> E toEnum(Class<E> clazz, Object value) {
		return toEnum(clazz, value, null);
	}

	/**
	 * To big integer.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the big integer
	 */
	public static BigInteger toBigInteger(Object value, BigInteger defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof BigInteger) {
			return (BigInteger) value;
		}
		if (value instanceof Long) {
			return BigInteger.valueOf((Long) value);
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return new BigInteger(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To big integer.
	 *
	 * @param value the value
	 * @return the big integer
	 */
	public static BigInteger toBigInteger(Object value) {
		return toBigInteger(value, null);
	}

	/**
	 * To big decimal.
	 *
	 * @param value        the value
	 * @param defaultValue the default value
	 * @return the big decimal
	 */
	public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof BigDecimal) {
			return (BigDecimal) value;
		}
		if (value instanceof Long) {
			return new BigDecimal((Long) value);
		}
		if (value instanceof Double) {
			return new BigDecimal((Double) value);
		}
		if (value instanceof Integer) {
			return new BigDecimal((Integer) value);
		}
		final String valueStr = toStr(value, null);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return new BigDecimal(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * To big decimal.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	public static BigDecimal toBigDecimal(Object value) {
		return toBigDecimal(value, null);
	}

	/**
	 * Utf 8 str.
	 *
	 * @param obj the obj
	 * @return the string
	 */
	public static String utf8Str(Object obj) {
		return str(obj, CharsetKit.CHARSET_UTF_8);
	}

	/**
	 * Str.
	 *
	 * @param obj         the obj
	 * @param charsetName the charset name
	 * @return the string
	 */
	public static String str(Object obj, String charsetName) {
		return str(obj, Charset.forName(charsetName));
	}

	/**
	 * Str.
	 *
	 * @param obj     the obj
	 * @param charset the charset
	 * @return the string
	 */
	public static String str(Object obj, Charset charset) {
		if (null == obj) {
			return null;
		}

		if (obj instanceof String) {
			return (String) obj;
		} else if (obj instanceof byte[]) {
			return str((byte[]) obj, charset);
		} else if (obj instanceof Byte[]) {
			byte[] bytes = ArrayUtils.toPrimitive((Byte[]) obj);
			return str(bytes, charset);
		} else if (obj instanceof ByteBuffer) {
			return str((ByteBuffer) obj, charset);
		}
		return obj.toString();
	}

	/**
	 * Str.
	 *
	 * @param bytes   the bytes
	 * @param charset the charset
	 * @return the string
	 */
	public static String str(byte[] bytes, String charset) {
		return str(bytes, StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}

	/**
	 * Str.
	 *
	 * @param data    the data
	 * @param charset the charset
	 * @return the string
	 */
	public static String str(byte[] data, Charset charset) {
		if (data == null) {
			return null;
		}

		if (null == charset) {
			return new String(data);
		}
		return new String(data, charset);
	}

	/**
	 * Str.
	 *
	 * @param data    the data
	 * @param charset the charset
	 * @return the string
	 */
	public static String str(ByteBuffer data, String charset) {
		if (data == null) {
			return null;
		}

		return str(data, Charset.forName(charset));
	}

	/**
	 * Str.
	 *
	 * @param data    the data
	 * @param charset the charset
	 * @return the string
	 */
	public static String str(ByteBuffer data, Charset charset) {
		if (null == charset) {
			charset = Charset.defaultCharset();
		}
		return charset.decode(data).toString();
	}

	// -----------------------------------------------------------------------
	// ??????????????????
	/**
	 * To SBC.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String toSBC(String input) {
		return toSBC(input, null);
	}

	/**
	 * To SBC.
	 *
	 * @param input         the input
	 * @param notConvertSet the not convert set
	 * @return the string
	 */
	public static String toSBC(String input, Set<Character> notConvertSet) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (null != notConvertSet && notConvertSet.contains(c[i])) {
				// ????????????????????????
				continue;
			}

			if (c[i] == ' ') {
				c[i] = '\u3000';
			} else if (c[i] < '\177') {
				c[i] = (char) (c[i] + 65248);

			}
		}
		return new String(c);
	}

	/**
	 * To DBC.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String toDBC(String input) {
		return toDBC(input, null);
	}

	/**
	 * To DBC.
	 *
	 * @param text          the text
	 * @param notConvertSet the not convert set
	 * @return the string
	 */
	public static String toDBC(String text, Set<Character> notConvertSet) {
		char c[] = text.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (null != notConvertSet && notConvertSet.contains(c[i])) {
				// ????????????????????????
				continue;
			}

			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		String returnString = new String(c);

		return returnString;
	}

	/**
	 * Digit uppercase.
	 *
	 * @param n the n
	 * @return the string
	 */
	public static String digitUppercase(double n) {
		String[] fraction = { "???", "???" };
		String[] digit = { "???", "???", "???", "???", "???", "???", "???", "???", "???", "???" };
		String[][] unit = { { "???", "???", "???" }, { "", "???", "???", "???" } };

		String head = n < 0 ? "???" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(???.)+", "");
		}
		if (s.length() < 1) {
			s = "???";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(???.)*???$", "").replaceAll("^$", "???") + unit[0][i] + s;
		}
		return head + s.replaceAll("(???.)*??????", "???").replaceFirst("(???.)+", "").replaceAll("(???.)+", "???").replaceAll("^???$",
				"?????????");
	}
}
