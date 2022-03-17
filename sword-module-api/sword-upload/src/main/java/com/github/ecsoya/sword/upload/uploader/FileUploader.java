package com.github.ecsoya.sword.upload.uploader;

import java.util.List;

import com.github.ecsoya.sword.upload.core.FileUploadException;
import com.github.ecsoya.sword.upload.core.UploadConfig;
import com.github.ecsoya.sword.upload.core.UploadData;

public interface FileUploader {

	String upload(UploadData file, UploadConfig config) throws FileUploadException;

	List<String> upload(List<UploadData> files, UploadConfig config) throws FileUploadException;
}
