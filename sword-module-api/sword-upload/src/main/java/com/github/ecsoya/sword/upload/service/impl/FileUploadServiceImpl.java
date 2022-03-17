package com.github.ecsoya.sword.upload.service.impl;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.upload.core.FileUploadException;
import com.github.ecsoya.sword.upload.core.UploadConfig;
import com.github.ecsoya.sword.upload.core.UploadData;
import com.github.ecsoya.sword.upload.service.IFileUploadService;
import com.github.ecsoya.sword.upload.uploader.FileUploader;

@Component
@Service
public class FileUploadServiceImpl implements IFileUploadService {

	@Autowired(required = false)
	private FileUploader uploader;

	@Autowired(required = false)
	private UploadConfig config;

	@Override
	public CommonResult<String> uploadFile(MultipartFile file) throws FileUploadException {
		if (file == null) {
			return CommonResult.fail("参数错误");
		}
		final CommonResult<List<String>> upload = upload(Collections.singletonList(file));
		if (!upload.isSuccess()) {
			return CommonResult.fail(upload.getInfo());
		}
		final List<String> data = upload.getData();
		return CommonResult.success(data == null || data.isEmpty() ? null : data.get(0));
	}

	@Override
	public CommonResult<List<String>> upload(List<MultipartFile> files) throws FileUploadException {
		if (uploader == null) {
			return CommonResult.fail("暂不支持");
		}
		if (files == null || files.isEmpty()) {
			return CommonResult.fail("参数错误");
		}
		final List<UploadData> datas = files.stream().map(file -> UploadData.build(file)).collect(Collectors.toList());
		return CommonResult.build(uploader.upload(datas, config));
	}

	@Override
	public CommonResult<String> uploadFile(String fileName, InputStream content) throws FileUploadException {
		if (uploader == null) {
			return CommonResult.fail("暂不支持");
		}
		if (content == null) {
			return CommonResult.fail("参数错误");
		}
		return CommonResult.build(uploader.upload(UploadData.build(fileName, content), config));
	}

}
