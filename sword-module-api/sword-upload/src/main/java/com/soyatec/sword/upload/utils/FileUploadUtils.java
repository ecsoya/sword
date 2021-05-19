package com.soyatec.sword.upload.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.upload.service.IFileUploadService;

/**
 * 文件上传工具类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class FileUploadUtils {

	/**
	 * 根据文件路径上传
	 *
	 * @param file 上传的文件
	 *
	 * @return 文件名称
	 * @throws IOException
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
	 * 根据文件路径上传
	 *
	 * @param file 上传的文件
	 *
	 * @return 文件名称
	 * @throws IOException
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

	public static String upload(String fileName, InputStream inputStream) throws Exception {
		final IFileUploadService service = SpringUtils.getBean(IFileUploadService.class);
		final CommonResult<String> result = service.uploadFile(fileName, inputStream);
		if (result.isSuccess(true)) {
			return result.getData();
		}
		return null;
	}

}