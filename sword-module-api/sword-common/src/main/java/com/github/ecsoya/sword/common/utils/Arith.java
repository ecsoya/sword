package com.github.ecsoya.sword.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The Class Arith.
 */
public class Arith {

	/** The Constant DEF_DIV_SCALE. */
	private static final int DEF_DIV_SCALE = 10;

	/**
	 * Instantiates a new arith.
	 */
	private Arith() {
	}

	/**
	 * Adds the.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 * @return the double
	 */
	public static double add(double v1, double v2) {
		final BigDecimal b1 = new BigDecimal(Double.toString(v1));
		final BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * Sub.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 * @return the double
	 */
	public static double sub(double v1, double v2) {
		final BigDecimal b1 = new BigDecimal(Double.toString(v1));
		final BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * Mul.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 * @return the double
	 */
	public static double mul(double v1, double v2) {
		final BigDecimal b1 = new BigDecimal(Double.toString(v1));
		final BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * Div.
	 *
	 * @param v1 the v 1
	 * @param v2 the v 2
	 * @return the double
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * Div.
	 *
	 * @param v1    the v 1
	 * @param v2    the v 2
	 * @param scale the scale
	 * @return the double
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		final BigDecimal b1 = new BigDecimal(Double.toString(v1));
		final BigDecimal b2 = new BigDecimal(Double.toString(v2));
		if (b1.compareTo(BigDecimal.ZERO) == 0) {
			return BigDecimal.ZERO.doubleValue();
		}
		return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * Round.
	 *
	 * @param v     the v
	 * @param scale the scale
	 * @return the double
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		final BigDecimal b = new BigDecimal(Double.toString(v));
		final BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
	}
}
