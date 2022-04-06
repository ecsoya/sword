package com.github.ecsoya.sword.upload.core;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class UploadConfig.
 */
@Configuration
@ConfigurationProperties("sword.upload")
public class UploadConfig {

	/** The base url. */
	private String baseUrl;

	/** The endpoint. */
	private String endpoint;

	/** The access key. */
	private String accessKey;

	/** The secret key. */
	private String secretKey;

	/** The bucket. */
	private String bucket;

	/** The add date path. */
	private boolean addDatePath = true;

	/**
	 * Gets the 对象存储，文件上传之后的域名前缀.
	 *
	 * @return the 对象存储，文件上传之后的域名前缀
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * Sets the base url.
	 *
	 * @param baseUrl the base url
	 * @return the upload config
	 */
	public UploadConfig setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	/**
	 * Gets the 对象存储，地域.
	 *
	 * @return the 对象存储，地域
	 */
	public String getEndpoint() {
		return endpoint;
	}

	/**
	 * Sets the endpoint.
	 *
	 * @param endpoint the endpoint
	 * @return the upload config
	 */
	public UploadConfig setEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	/**
	 * Gets the 对象存储，AccessKey.
	 *
	 * @return the 对象存储，AccessKey
	 */
	public String getAccessKey() {
		return accessKey;
	}

	/**
	 * Sets the access key.
	 *
	 * @param accessKey the access key
	 * @return the upload config
	 */
	public UploadConfig setAccessKey(String accessKey) {
		this.accessKey = accessKey;
		return this;
	}

	/**
	 * Gets the 对象存储，SecretKey.
	 *
	 * @return the 对象存储，SecretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}

	/**
	 * Sets the secret key.
	 *
	 * @param secretKey the secret key
	 * @return the upload config
	 */
	public UploadConfig setSecretKey(String secretKey) {
		this.secretKey = secretKey;
		return this;
	}

	/**
	 * Gets the 对象存储，存储桶.
	 *
	 * @return the 对象存储，存储桶
	 */
	public String getBucket() {
		return bucket;
	}

	/**
	 * Sets the bucket.
	 *
	 * @param bucket the bucket
	 * @return the upload config
	 */
	public UploadConfig setBucket(String bucket) {
		this.bucket = bucket;
		return this;
	}

	/**
	 * Checks if is 添加时间路径.
	 *
	 * @return the 添加时间路径
	 */
	public boolean isAddDatePath() {
		return addDatePath;
	}

	/**
	 * Sets the add date path.
	 *
	 * @param addDatePath the add date path
	 * @return the upload config
	 */
	public UploadConfig setAddDatePath(boolean addDatePath) {
		this.addDatePath = addDatePath;
		return this;
	}

	/**
	 * Test cloud validated.
	 *
	 * @throws FileUploadException the file upload exception
	 */
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

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "[baseUrl=" + baseUrl + ", endpoint=" + endpoint + ", accessKey=" + accessKey + ", bucket=" + bucket
				+ ", addDatePath=" + addDatePath + "]";
	}

	/**
	 * Builds the.
	 *
	 * @return the upload config
	 */
	public static UploadConfig build() {
		return new UploadConfig();
	}

}
