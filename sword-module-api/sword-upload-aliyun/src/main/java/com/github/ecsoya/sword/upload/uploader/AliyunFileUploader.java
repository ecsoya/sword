package com.github.ecsoya.sword.upload.uploader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.github.ecsoya.sword.upload.core.FileUploadException;
import com.github.ecsoya.sword.upload.core.UploadConfig;
import com.github.ecsoya.sword.upload.core.UploadData;
import com.github.ecsoya.sword.upload.uploader.AbstractFileUploader;

/**
 * 文件上传工具类
 *
 * @author AngryRED (jin.liu@soyatec.com)
 */
@Service
public class AliyunFileUploader extends AbstractFileUploader {

	private static final Logger log = LoggerFactory.getLogger(AliyunFileUploader.class);

	@Override
	protected void testUploadConfig(UploadConfig config) throws FileUploadException {
		config.testCloudValidated();
	}

	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		log.info("AliyunFileUploader, config={}", config);

		final OSS client = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKey(),
				config.getSecretKey());

		final List<String> result = new ArrayList<>();
		try {
			for (final UploadData file : files) {
				final String fileName = getFileName(file, config);
				final String contentType = file.getContentType();
				final Long length = file.getLength();
				final InputStream inputStream = file.getInputStream();
				if (inputStream != null && inputStream.available() > 0) {
					final ObjectMetadata metadata = new ObjectMetadata();
					if (length != null) {
						metadata.setContentLength(length);
					}
					if (contentType != null) {
						metadata.setContentType(contentType);
					}
					client.putObject(config.getBucket(), fileName, inputStream, metadata);
				}
				final String url = getUrl(config.getBaseUrl(), fileName);
				result.add(url);
			}
		} catch (final Exception e) {
			log.warn("AliyunFileUploader, error={}", e.getLocalizedMessage());
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