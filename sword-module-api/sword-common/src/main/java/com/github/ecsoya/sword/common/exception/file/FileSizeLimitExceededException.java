package com.github.ecsoya.sword.common.exception.file;

/**
 * The Class FileSizeLimitExceededException.
 */
public class FileSizeLimitExceededException extends FileException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new file size limit exceeded exception.
	 *
	 * @param defaultMaxSize the default max size
	 */
	public FileSizeLimitExceededException(long defaultMaxSize) {
		super("upload.exceed.maxSize", new Object[] { defaultMaxSize });
	}
}
