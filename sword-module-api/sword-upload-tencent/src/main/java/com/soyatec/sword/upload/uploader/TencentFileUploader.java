package com.soyatec.sword.upload.uploader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.upload.core.FileUploadException;
import com.soyatec.sword.upload.core.UploadConfig;
import com.soyatec.sword.upload.core.UploadData;

@Service
public class TencentFileUploader extends AbstractFileUploader {

	private static final Logger log = LoggerFactory.getLogger(TencentFileUploader.class);

	@Override
	protected void testUploadConfig(UploadConfig config) throws FileUploadException {
		config.testCloudValidated();
	}

	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		log.info("TencentFileUploader, config={}", config);

		final COSCredentials cred = new BasicCOSCredentials(config.getAccessKey(), config.getSecretKey());
		final String endpoint = config.getEndpoint();
		final ClientConfig clientConfig = new ClientConfig(new Region(endpoint));
		final COSClient client = new COSClient(cred, clientConfig);

		final String bucket = config.getBucket();
		final List<String> result = new ArrayList<>();
		try {
			for (final UploadData file : files) {
				final String contentType = file.getContentType();
				final Long length = file.getLength();
				final String path = getFileName(file, config);
				final InputStream inputStream = file.getInputStream();
				if (inputStream != null && inputStream.available() > 0) {
					final ObjectMetadata metadata = new ObjectMetadata();
					if (length != null) {
						metadata.setContentLength(length);
					}
					if (contentType != null) {
						metadata.setContentType(contentType);
					}
					client.putObject(bucket, path, inputStream, metadata);
				} else if (file.getDatas() != null && file.getDatas().length != 0) {
					client.putObject(bucket, path, new String(file.getDatas()));
				}
				// 加速域名
				if (StringUtils.isNotEmpty(config.getBaseUrl())) {
					result.add(config.getBaseUrl() + "/" + path);
				} else {
					result.add("https://" + bucket + ".cos." + endpoint + ".myqcloud.com/" + path);
				}
			}
		} catch (final Exception e) {
			log.warn("TencentFileUploader, error={}", e.getLocalizedMessage());
		} finally {
			client.shutdown();
		}
		return result;
	}

}
