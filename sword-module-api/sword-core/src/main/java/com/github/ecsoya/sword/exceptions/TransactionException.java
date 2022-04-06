package com.github.ecsoya.sword.exceptions;

/**
 * The Class TransactionException.
 */
public class TransactionException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2446756072177623738L;

	/**
	 * Instantiates a new transaction exception.
	 */
	public TransactionException() {
		super();
	}

	/**
	 * Instantiates a new transaction exception.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @param arg2 the arg 2
	 * @param arg3 the arg 3
	 */
	public TransactionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	/**
	 * Instantiates a new transaction exception.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	public TransactionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Instantiates a new transaction exception.
	 *
	 * @param arg0 the arg 0
	 */
	public TransactionException(String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new transaction exception.
	 *
	 * @param arg0 the arg 0
	 */
	public TransactionException(Throwable arg0) {
		super(arg0);
	}

}
