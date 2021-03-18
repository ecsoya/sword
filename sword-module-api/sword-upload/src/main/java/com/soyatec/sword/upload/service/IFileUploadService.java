package com.soyatec.sword.upload.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.upload.core.FileUploadException;

public interface IFileUploadService {

	CommonResult<String> uploadFile(MultipartFile file) throws FileUploadException;

	CommonResult<List<String>> upload(List<MultipartFile> files) throws FileUploadException;

	CommonResult<String> uploadFile(String fileName, InputStream content) throws FileUploadException;

}
