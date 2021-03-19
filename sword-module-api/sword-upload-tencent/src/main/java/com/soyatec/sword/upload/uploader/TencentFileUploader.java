package com.soyatec.sword.upload.uploader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		COSCredentials cred = new BasicCOSCredentials(config.getAccessKey(), config.getSecretKey());
		String endpoint = config.getEndpoint();
		ClientConfig clientConfig = new ClientConfig(new Region(endpoint));
		// 3 生成cos客户端
		COSClient client = new COSClient(cred, clientConfig);

		String bucket = config.getBucket();
		List<String> result = new ArrayList<>();
		for (UploadData file : files) {
			String contentType = file.getContentType();
			Long length = file.getLength();
			String path = getFileName(file, config);
			try {
				InputStream inputStream = file.getInputStream();
				if (inputStream != null && inputStream.available() > 0) {
					ObjectMetadata metadata = new ObjectMetadata();
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
			} catch (Exception e) {
				return null;
			}
		}

		client.shutdown();

		return result;
	}

}
