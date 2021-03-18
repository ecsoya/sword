package com.soyatec.sword.upload.uploader;

import java.util.List;

import com.soyatec.sword.upload.core.FileUploadException;
import com.soyatec.sword.upload.core.UploadConfig;
import com.soyatec.sword.upload.core.UploadData;

public interface FileUploader {

	String upload(UploadData file, UploadConfig config) throws FileUploadException;

	List<String> upload(List<UploadData> files, UploadConfig config) throws FileUploadException;
}
