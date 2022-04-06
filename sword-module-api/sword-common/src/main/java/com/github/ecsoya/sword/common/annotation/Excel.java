package com.github.ecsoya.sword.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * The Interface Excel.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {

	/**
	 * Sort.
	 *
	 * @return the int
	 */
	public int sort() default Integer.MAX_VALUE;

	/**
	 * Name.
	 *
	 * @return the string
	 */
	public String name() default "";

	/**
	 * Date format.
	 *
	 * @return the string
	 */
	public String dateFormat() default "";

	/**
	 * Dict type.
	 *
	 * @return the string
	 */
	public String dictType() default "";

	/**
	 * Read converter exp.
	 *
	 * @return the string
	 */
	public String readConverterExp() default "";

	/**
	 * Separator.
	 *
	 * @return the string
	 */
	public String separator() default ",";

	/**
	 * Scale.
	 *
	 * @return the int
	 */
	public int scale() default -1;

	/**
	 * Rounding mode.
	 *
	 * @return the int
	 */
	public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

	/**
	 * Cell type.
	 *
	 * @return the column type
	 */
	public ColumnType cellType() default ColumnType.STRING;

	/**
	 * Height.
	 *
	 * @return the double
	 */
	public double height() default 14;

	/**
	 * Width.
	 *
	 * @return the double
	 */
	public double width() default 16;

	/**
	 * Suffix.
	 *
	 * @return the string
	 */
	public String suffix() default "";

	/**
	 * Default value.
	 *
	 * @return the string
	 */
	public String defaultValue() default "";

	/**
	 * Prompt.
	 *
	 * @return the string
	 */
	public String prompt() default "";

	/**
	 * Combo.
	 *
	 * @return the string[]
	 */
	public String[] combo() default {};

	/**
	 * Checks if is export.
	 *
	 * @return true, if is export
	 */
	public boolean isExport() default true;

	/**
	 * Target attr.
	 *
	 * @return the string
	 */
	public String targetAttr() default "";

	/**
	 * Checks if is statistics.
	 *
	 * @return true, if is statistics
	 */
	public boolean isStatistics() default false;

	/**
	 * Align.
	 *
	 * @return the align
	 */
	Align align() default Align.AUTO;

	/**
	 * The Enum Align.
	 */
	public enum Align {

		/** The auto. */
		AUTO(0),

		/** The left. */
		LEFT(1),

		/** The center. */
		CENTER(2),

		/** The right. */
		RIGHT(3);

		/** The value. */
		private final int value;

		/**
		 * Instantiates a new align.
		 *
		 * @param value the value
		 */
		Align(int value) {
			this.value = value;
		}

		/**
		 * Value.
		 *
		 * @return the int
		 */
		public int value() {
			return this.value;
		}
	}

	/**
	 * Type.
	 *
	 * @return the type
	 */
	Type type() default Type.ALL;

	/**
	 * The Enum Type.
	 */
	public enum Type {

		/** The all. */
		ALL(0),

		/** The export. */
		EXPORT(1),

		/** The import. */
		IMPORT(2);

		/** The value. */
		private final int value;

		/**
		 * Instantiates a new type.
		 *
		 * @param value the value
		 */
		Type(int value) {
			this.value = value;
		}

		/**
		 * Value.
		 *
		 * @return the int
		 */
		public int value() {
			return this.value;
		}
	}

	/**
	 * The Enum ColumnType.
	 */
	public enum ColumnType {

		/** The numeric. */
		NUMERIC(0),

		/** The string. */
		STRING(1),

		/** The image. */
		IMAGE(2);

		/** The value. */
		private final int value;

		/**
		 * Instantiates a new column type.
		 *
		 * @param value the value
		 */
		ColumnType(int value) {
			this.value = value;
		}

		/**
		 * Value.
		 *
		 * @return the int
		 */
		public int value() {
			return this.value;
		}
	}
}