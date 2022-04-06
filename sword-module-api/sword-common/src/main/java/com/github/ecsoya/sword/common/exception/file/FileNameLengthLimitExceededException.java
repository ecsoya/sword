package com.github.ecsoya.sword.common.exception.file;

/**
 * The Class FileNameLengthLimitExceededException.
 */
public class FileNameLengthLimitExceededException extends FileException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new file name length limit exceeded exception.
	 *
	 * @param defaultFileNameLength the default file name length
	 */
	public FileNameLengthLimitExceededException(int defaultFileNameLength) {
		super("upload.filename.exceed.length", new Object[] { defaultFileNameLength });
	}
}
