package com.soyatec.sword.exceptions;

public class LockableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2595303173748972881L;

	public LockableException() {
	}

	public LockableException(String arg0) {
		super(arg0);
	}

	public LockableException(Throwable arg0) {
		super(arg0);
	}

	public LockableException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LockableException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
