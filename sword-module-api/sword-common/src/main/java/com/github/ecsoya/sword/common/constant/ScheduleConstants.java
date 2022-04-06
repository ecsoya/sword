package com.github.ecsoya.sword.common.constant;

/**
 * The Class ScheduleConstants.
 */
public class ScheduleConstants {

	/** The Constant TASK_CLASS_NAME. */
	public static final String TASK_CLASS_NAME = "TASK_CLASS_NAME";

	/** The Constant TASK_PROPERTIES. */
	public static final String TASK_PROPERTIES = "TASK_PROPERTIES";

	/** The Constant MISFIRE_DEFAULT. */
	public static final String MISFIRE_DEFAULT = "0";

	/** The Constant MISFIRE_IGNORE_MISFIRES. */
	public static final String MISFIRE_IGNORE_MISFIRES = "1";

	/** The Constant MISFIRE_FIRE_AND_PROCEED. */
	public static final String MISFIRE_FIRE_AND_PROCEED = "2";

	/** The Constant MISFIRE_DO_NOTHING. */
	public static final String MISFIRE_DO_NOTHING = "3";

	/**
	 * The Enum Status.
	 */
	public enum Status {

		/** The normal. */
		NORMAL("0"),

		/** The pause. */
		PAUSE("1");

		/** The value. */
		private final String value;

		/**
		 * Instantiates a new status.
		 *
		 * @param value the value
		 */
		private Status(String value) {
			this.value = value;
		}

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		public String getValue() {
			return value;
		}
	}
}
