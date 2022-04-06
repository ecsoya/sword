package com.github.ecsoya.sword.upload.uploader;

import java.util.List;

import com.github.ecsoya.sword.upload.core.FileUploadException;
import com.github.ecsoya.sword.upload.core.UploadConfig;
import com.github.ecsoya.sword.upload.core.UploadData;

/**
 * The Interface FileUploader.
 */
public interface FileUploader {

	/**
	 * Upload.
	 *
	 * @param file   the file
	 * @param config the config
	 * @return the string
	 * @throws FileUploadException the file upload exception
	 */
	String upload(UploadData file, UploadConfig config) throws FileUploadException;

	/**
	 * Upload.
	 *
	 * @param files  the files
	 * @param config the config
	 * @return the list
	 * @throws FileUploadException the file upload exception
	 */
	List<String> upload(List<UploadData> files, UploadConfig config) throws FileUploadException;
}
