package com.github.ecsoya.sword.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.util.NumberUtils;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class MathUtils.
 */
public class MathUtils {

	/**
	 * Instantiates a new math utils.
	 */
	private MathUtils() {
	}

	/**
	 * Checks if is positive.
	 *
	 * @param value the value
	 * @return true, if is positive
	 */
	public static boolean isPositive(BigDecimal value) {
		return value != null && value.compareTo(BigDecimal.ZERO) > 0;
	}

	/**
	 * Checks if is valid.
	 *
	 * @param value the value
	 * @return true, if is valid
	 */
	public static boolean isValid(BigDecimal value) {
		return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
	}

	/**
	 * Checks if is invalid.
	 *
	 * @param value the value
	 * @return true, if is invalid
	 */
	public static boolean isInvalid(BigDecimal value) {
		return value == null || value.doubleValue() < 0;
	}

	/**
	 * Checks if is empty.
	 *
	 * @param value the value
	 * @return true, if is empty
	 */
	public static boolean isEmpty(BigDecimal value) {
		return !isPositive(value);
	}

	/**
	 * Gt.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return true, if successful
	 */
	public static boolean gt(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) > 0;
	}

	/**
	 * Gte.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return true, if successful
	 */
	public static boolean gte(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) >= 0;
	}

	/**
	 * Lt.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return true, if successful
	 */
	public static boolean lt(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) < 0;
	}

	/**
	 * Lte.
	 *
	 * @param left  the left
	 * @param right the right
	 * @return true, if successful
	 */
	public static boolean lte(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) <= 0;
	}

	/**
	 * Plus.
	 *
	 * @param members the members
	 * @return the big decimal
	 */
	public static BigDecimal plus(BigDecimal... members) {
		BigDecimal sum = BigDecimal.ZERO;
		if (members != null) {
			for (final BigDecimal bigDecimal : members) {
				if (bigDecimal != null) {
					sum = sum.add(bigDecimal);
				}
			}
		}
		return sum;
	}

	/**
	 * Plus.
	 *
	 * @param numbers the numbers
	 * @return the long
	 */
	public static Long plus(Long... numbers) {
		BigDecimal sum = BigDecimal.ZERO;
		if (numbers != null) {
			for (final Long number : numbers) {
				if (number != null) {
					sum = sum.add(BigDecimal.valueOf(number.longValue()));
				}
			}
		}
		return sum.longValue();
	}

	/**
	 * Parses the long.
	 *
	 * @param value the value
	 * @return the long
	 */
	public static Long parseLong(String value) {
		if (value == null) {
			return null;
		}
		try {
			return Long.parseLong(value);
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Null to zero.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	public static BigDecimal nullToZero(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value.setScale(6, RoundingMode.HALF_UP);
	}

	/**
	 * Null to zero.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	public static BigDecimal nullToZero(Number value) {
		return value == null ? BigDecimal.ZERO : BigDecimal.valueOf(value.doubleValue());
	}

	/**
	 * To percent.
	 *
	 * @param value     the value
	 * @param precision the precision
	 * @return the string
	 */
	public static String toPercent(BigDecimal value, int precision) {
		if (value == null) {
			return "0%";
		}
		return value.multiply(BigDecimal.valueOf(100)).setScale(precision).toPlainString() + "%";
	}

	/**
	 * Divide.
	 *
	 * @param value   the value
	 * @param divisor the divisor
	 * @return the big decimal
	 */
	public static BigDecimal divide(BigDecimal value, BigDecimal divisor) {
		if (value == null || divisor == null || value.doubleValue() == 0 || divisor.doubleValue() == 0) {
			return BigDecimal.ZERO;
		}
		return value.divide(divisor, 6, RoundingMode.HALF_UP);
	}

	/**
	 * Checks if is rate.
	 *
	 * @param value the value
	 * @return true, if is rate
	 */
	public static boolean isRate(BigDecimal value) {
		return value != null && value.doubleValue() >= 0 && value.doubleValue() <= 1;
	}

	/**
	 * Checks if is discount.
	 *
	 * @param value the value
	 * @return true, if is discount
	 */
	public static boolean isDiscount(BigDecimal value) {
		return value != null && value.doubleValue() > 0 && value.doubleValue() <= 1;
	}

	/**
	 * Parses the.
	 *
	 * @param value the value
	 * @return the big decimal
	 */
	public static BigDecimal parse(String value) {
		if (StringUtils.isEmpty(value)) {
			return BigDecimal.ZERO;
		}
		try {
			return new BigDecimal(value);
		} catch (final Exception e) {
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Equals.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 * @return true, if successful
	 */
	public static boolean equals(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return false;
		}
		return v1.compareTo(v2) == 0;
	}

	/**
	 * To decimal.
	 *
	 * @param number the number
	 * @return the big decimal
	 */
	public static BigDecimal toDecimal(Number number) {
		if (number == null) {
			return null;
		}
		return BigDecimal.valueOf(number.doubleValue());
	}

	/**
	 * Lt.
	 *
	 * @param version the version
	 * @param latest  the latest
	 * @return true, if successful
	 */
	public static boolean lt(Long version, Long latest) {
		if (version == null || latest == null) {
			return true;
		}
		return version.longValue() < latest.longValue();
	}

	/**
	 * Parses the number.
	 *
	 * @param <T>    the generic type
	 * @param string the string
	 * @param type   the type
	 * @return the t
	 */
	public static <T extends Number> T parseNumber(String string, Class<T> type) {
		try {
			return NumberUtils.parseNumber(string, type);
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * Compare.
	 *
	 * @param o1 the o 1
	 * @param o2 the o 2
	 * @return the int
	 */
	public static int compare(BigDecimal o1, BigDecimal o2) {
		if (o1 == null || o2 == null) {
			return 0;
		}
		return o1.compareTo(o2);
	}

	/**
	 * Between.
	 *
	 * @param amount the amount
	 * @param min    the min
	 * @param max    the max
	 * @return true, if successful
	 */
	public static boolean between(BigDecimal amount, BigDecimal min, BigDecimal max) {
		if (amount == null || min == null || max == null) {
			return false;
		}
		if (lt(amount, min)) {
			return false;
		}
		if (MathUtils.isValid(max) && gte(amount, max)) {
			return false;
		}
		return true;
	}

	/**
	 * Parses the integer.
	 *
	 * @param value the value
	 * @return the integer
	 */
	public static Integer parseInteger(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		try {
			return Integer.parseInt(value.trim());
		} catch (final Exception e) {
			return null;
		}
	}

	/**
	 * To string.
	 *
	 * @param value   the value
	 * @param decimal the decimal
	 * @param suffix  the suffix
	 * @return the string
	 */
	public static String toString(BigDecimal value, Integer decimal, String suffix) {
		if (value == null) {
			return "";
		}
		if (decimal == null || decimal < 0) {
			decimal = 0;
		}
		String pattern = "0";
		if (decimal > 0) {
			pattern += ".";
			while (decimal > 0) {
				pattern += "0";
				decimal--;
			}
		}
		DecimalFormat format = new DecimalFormat(pattern);
		String result = format.format(value.doubleValue());
		return suffix != null ? result + suffix : result;
	}

	/**
	 * To string.
	 *
	 * @param value   the value
	 * @param decimal the decimal
	 * @return the string
	 */
	public static String toString(BigDecimal value, Integer decimal) {
		return toString(value, decimal, null);
	}

	/**
	 * Subtract.
	 *
	 * @param value      the value
	 * @param subtrahend the subtrahend
	 * @return the big decimal
	 */
	public static BigDecimal subtract(BigDecimal value, BigDecimal subtrahend) {
		if (value == null || subtrahend == null) {
			return BigDecimal.ZERO;
		}
		return value.subtract(subtrahend);
	}

}
