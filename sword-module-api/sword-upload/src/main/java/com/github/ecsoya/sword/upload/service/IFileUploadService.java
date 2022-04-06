package com.github.ecsoya.sword.upload.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.upload.core.FileUploadException;

/**
 * The Interface IFileUploadService.
 */
public interface IFileUploadService {

	/**
	 * Upload file.
	 *
	 * @param file the file
	 * @return the common result
	 * @throws FileUploadException the file upload exception
	 */
	CommonResult<String> uploadFile(MultipartFile file) throws FileUploadException;

	/**
	 * Upload.
	 *
	 * @param files the files
	 * @return the common result
	 * @throws FileUploadException the file upload exception
	 */
	CommonResult<List<String>> upload(List<MultipartFile> files) throws FileUploadException;

	/**
	 * Upload file.
	 *
	 * @param fileName the file name
	 * @param content  the content
	 * @return the common result
	 * @throws FileUploadException the file upload exception
	 */
	CommonResult<String> uploadFile(String fileName, InputStream content) throws FileUploadException;

}
