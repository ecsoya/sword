package com.github.ecsoya.sword.upload.utils;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.upload.service.IFileUploadService;

/**
 * The Class FileUploadUtils.
 */
public class FileUploadUtils {

	/**
	 * Upload.
	 *
	 * @param file the file
	 * @return the string
	 * @throws Exception the exception
	 */
	public static final String upload(MultipartFile file) throws Exception {
		final IFileUploadService service = SpringUtils.getBean(IFileUploadService.class);
		final CommonResult<String> result = service.uploadFile(file);
		if (result.isSuccess(true)) {
			return result.getData();
		}
		return null;
	}

	/**
	 * Upload.
	 *
	 * @param files the files
	 * @return the list
	 * @throws Exception the exception
	 */
	public static final List<String> upload(MultipartFile... files) throws Exception {
		if (files == null || files.length == 0) {
			return Collections.emptyList();
		}
		final IFileUploadService service = SpringUtils.getBean(IFileUploadService.class);
		final CommonResult<List<String>> result = service.upload(Arrays.asList(files));
		if (result.isSuccess(true)) {
			return result.getData();
		}
		return null;
	}

	/**
	 * Upload.
	 *
	 * @param fileName    the file name
	 * @param inputStream the input stream
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String upload(String fileName, InputStream inputStream) throws Exception {
		final IFileUploadService service = SpringUtils.getBean(IFileUploadService.class);
		final CommonResult<String> result = service.uploadFile(fileName, inputStream);
		if (result.isSuccess(true)) {
			return result.getData();
		}
		return null;
	}

}