package com.github.ecsoya.sword.upload.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.utils.StringUtils;

@Configuration
@ConfigurationProperties("sword.upload")
public class UploadConfig {

	/**
	 * 对象存储，文件上传之后的域名前缀
	 */
	private String baseUrl;

	/**
	 * 对象存储，地域
	 */
	private String endpoint;

	/**
	 * 对象存储，AccessKey
	 */
	private String accessKey;

	/**
	 * 对象存储，SecretKey
	 */
	private String secretKey;

	/**
	 * 对象存储，存储桶
	 */
	private String bucket;

	/**
	 * 添加时间路径
	 */
	private boolean addDatePath = true;

	public String getBaseUrl() {
		return baseUrl;
	}

	public UploadConfig setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public UploadConfig setEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public UploadConfig setAccessKey(String accessKey) {
		this.accessKey = accessKey;
		return this;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public UploadConfig setSecretKey(String secretKey) {
		this.secretKey = secretKey;
		return this;
	}

	public String getBucket() {
		return bucket;
	}

	public UploadConfig setBucket(String bucket) {
		this.bucket = bucket;
		return this;
	}

	public boolean isAddDatePath() {
		return addDatePath;
	}

	public UploadConfig setAddDatePath(boolean addDatePath) {
		this.addDatePath = addDatePath;
		return this;
	}

	public void testCloudValidated() throws FileUploadException {
		if (StringUtils.isEmpty(baseUrl)) {
			throw new FileUploadException("Base URL is empty");
		}
		if (StringUtils.isEmpty(bucket)) {
			throw new FileUploadException("Bucket is empty");
		}
		if (StringUtils.isEmpty(accessKey)) {
			throw new FileUploadException("AccessKey is empty");
		}
		if (StringUtils.isEmpty(secretKey)) {
			throw new FileUploadException("SecretKey is empty");
		}
		if (StringUtils.isEmpty(endpoint)) {
			throw new FileUploadException("Endpoint or Region is empty");
		}
	}

	@Override
	public String toString() {
		return "[baseUrl=" + baseUrl + ", endpoint=" + endpoint + ", accessKey=" + accessKey + ", bucket=" + bucket
				+ ", addDatePath=" + addDatePath + "]";
	}

	public static UploadConfig build() {
		return new UploadConfig();
	}

}
