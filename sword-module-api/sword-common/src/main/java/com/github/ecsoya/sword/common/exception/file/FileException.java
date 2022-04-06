package com.github.ecsoya.sword.common.exception.file;

import com.github.ecsoya.sword.common.exception.base.BaseException;

/**
 * The Class FileException.
 */
public class FileException extends BaseException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new file exception.
	 *
	 * @param code the code
	 * @param args the args
	 */
	public FileException(String code, Object[] args) {
		super("file", code, args, null);
	}

}
