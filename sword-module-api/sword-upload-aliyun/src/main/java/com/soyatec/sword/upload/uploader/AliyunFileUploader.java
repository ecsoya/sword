package com.soyatec.sword.upload.uploader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.soyatec.sword.upload.core.FileUploadException;
import com.soyatec.sword.upload.core.UploadConfig;
import com.soyatec.sword.upload.core.UploadData;

/**
 * 文件上传工具类
 * 
 * @author AngryRED (jin.liu@soyatec.com)
 */
@Service
public class AliyunFileUploader extends AbstractFileUploader {

	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		OSS client = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKey(), config.getSecretKey());

		List<String> result = new ArrayList<>();
		try {
			for (UploadData file : files) {
				String fileName = getFileName(file, config);
				String contentType = file.getContentType();
				Long length = file.getLength();
				InputStream inputStream = file.getInputStream();
				if (inputStream != null && inputStream.available() > 0) {
					ObjectMetadata metadata = new ObjectMetadata();
					if (length != null) {
						metadata.setContentLength(length);
					}
					if (contentType != null) {
						metadata.setContentType(contentType);
					}
					client.putObject(config.getBucket(), fileName, inputStream, metadata);
				}
				String url = getUrl(config.getBaseUrl(), fileName);
				result.add(url);
			}
		} catch (Exception oe) {
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all allocated
			 * resources.
			 */
			client.shutdown();
		}

		return result;
	}

}