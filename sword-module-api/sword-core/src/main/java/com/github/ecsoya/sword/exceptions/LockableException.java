package com.github.ecsoya.sword.exceptions;

/**
 * The Class LockableException.
 */
public class LockableException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2595303173748972881L;

	/**
	 * Instantiates a new lockable exception.
	 */
	public LockableException() {
	}

	/**
	 * Instantiates a new lockable exception.
	 *
	 * @param arg0 the arg 0
	 */
	public LockableException(String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new lockable exception.
	 *
	 * @param arg0 the arg 0
	 */
	public LockableException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new lockable exception.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	public LockableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Instantiates a new lockable exception.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @param arg3 the arg 3
	 */
	public LockableException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
