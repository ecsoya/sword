package com.soyatec.sword.upload.utils;

import java.io.IOException;
import java.io.InputStream;

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
		IFileUploadService service = SpringUtils.getBean(IFileUploadService.class);
		CommonResult<String> result = service.uploadFile(file);
		if (result.isSuccess(true)) {
			return result.getData();
		}
		return null;
	}

	public static String upload(String fileName, InputStream inputStream) throws Exception {
		IFileUploadService service = SpringUtils.getBean(IFileUploadService.class);
		CommonResult<String> result = service.uploadFile(fileName, inputStream);
		if (result.isSuccess(true)) {
			return result.getData();
		}
		return null;
	}

}