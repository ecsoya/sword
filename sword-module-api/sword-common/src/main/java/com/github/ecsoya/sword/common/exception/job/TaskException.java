package com.github.ecsoya.sword.common.exception.job;

/**
 * The Class TaskException.
 */
public class TaskException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
	private final Code code;

	/**
	 * Instantiates a new task exception.
	 *
	 * @param msg  the msg
	 * @param code the code
	 */
	public TaskException(String msg, Code code) {
		this(msg, code, null);
	}

	/**
	 * Instantiates a new task exception.
	 *
	 * @param msg      the msg
	 * @param code     the code
	 * @param nestedEx the nested ex
	 */
	public TaskException(String msg, Code code, Exception nestedEx) {
		super(msg, nestedEx);
		this.code = code;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public Code getCode() {
		return code;
	}

	/**
	 * The Enum Code.
	 */
	public enum Code {

		/** The task exists. */
		TASK_EXISTS,

		/** The no task exists. */
		NO_TASK_EXISTS,

		/** The task already started. */
		TASK_ALREADY_STARTED,

		/** The unknown. */
		UNKNOWN,

		/** The config error. */
		CONFIG_ERROR,

		/** The task node not available. */
		TASK_NODE_NOT_AVAILABLE
	}
}