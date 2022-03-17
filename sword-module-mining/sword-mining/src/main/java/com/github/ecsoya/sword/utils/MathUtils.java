package com.github.ecsoya.sword.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.springframework.util.NumberUtils;

import com.github.ecsoya.sword.common.utils.StringUtils;

public class MathUtils {

	private MathUtils() {
	}

	public static boolean isPositive(BigDecimal value) {
		return value != null && value.compareTo(BigDecimal.ZERO) > 0;
	}

	public static boolean isValid(BigDecimal value) {
		return value != null && value.compareTo(BigDecimal.ZERO) >= 0;
	}

	public static boolean isInvalid(BigDecimal value) {
		return value == null || value.doubleValue() < 0;
	}

	public static boolean isEmpty(BigDecimal value) {
		return !isPositive(value);
	}

	public static boolean gt(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) > 0;
	}

	public static boolean gte(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) >= 0;
	}

	public static boolean lt(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) < 0;
	}

	public static boolean lte(BigDecimal left, BigDecimal right) {
		return left != null && right != null && left.compareTo(right) <= 0;
	}

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

	public static BigDecimal nullToZero(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value.setScale(6, RoundingMode.HALF_UP);
	}

	public static BigDecimal nullToZero(Number value) {
		return value == null ? BigDecimal.ZERO : BigDecimal.valueOf(value.doubleValue());
	}

	public static String toPercent(BigDecimal value, int precision) {
		if (value == null) {
			return "0%";
		}
		return value.multiply(BigDecimal.valueOf(100)).setScale(precision).toPlainString() + "%";
	}

	public static BigDecimal divide(BigDecimal value, BigDecimal divisor) {
		if (value == null || divisor == null || value.doubleValue() == 0 || divisor.doubleValue() == 0) {
			return BigDecimal.ZERO;
		}
		return value.divide(divisor, 6, RoundingMode.HALF_UP);
	}

	public static boolean isRate(BigDecimal value) {
		return value != null && value.doubleValue() >= 0 && value.doubleValue() <= 1;
	}

	public static boolean isDiscount(BigDecimal value) {
		return value != null && value.doubleValue() > 0 && value.doubleValue() <= 1;
	}

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

	public static boolean equals(BigDecimal v1, BigDecimal v2) {
		if (v1 == null || v2 == null) {
			return false;
		}
		return v1.compareTo(v2) == 0;
	}

	public static BigDecimal toDecimal(Number number) {
		if (number == null) {
			return null;
		}
		return BigDecimal.valueOf(number.doubleValue());
	}

	public static boolean lt(Long version, Long latest) {
		if (version == null || latest == null) {
			return true;
		}
		return version.longValue() < latest.longValue();
	}

	public static <T extends Number> T parseNumber(String string, Class<T> type) {
		try {
			return NumberUtils.parseNumber(string, type);
		} catch (final Exception e) {
			return null;
		}
	}

	public static int compare(BigDecimal o1, BigDecimal o2) {
		if (o1 == null || o2 == null) {
			return 0;
		}
		return o1.compareTo(o2);
	}

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

	public static String toString(BigDecimal value, Integer decimal) {
		return toString(value, decimal, null);
	}

	public static BigDecimal subtract(BigDecimal value, BigDecimal subtrahend) {
		if (value == null || subtrahend == null) {
			return BigDecimal.ZERO;
		}
		return value.subtract(subtrahend);
	}

}
