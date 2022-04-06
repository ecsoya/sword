package com.github.ecsoya.sword.upload.uploader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.file.MimeTypeUtils;
import com.github.ecsoya.sword.upload.core.FileUploadException;
import com.github.ecsoya.sword.upload.core.UploadConfig;
import com.github.ecsoya.sword.upload.core.UploadData;

/**
 * The Class AbstractFileUploader.
 */
public abstract class AbstractFileUploader implements FileUploader {

//	private UploadCloudConfig config;
//
//	public AbstractFileUploader(UploadCloudConfig config) throws FileUploadException {
//		if (config == null) {
//			throw new FileUploadException("Upload config is empty");
//		}
//		config.checkValid();
//		this.config = config;
//	}

	/**
	 * Upload.
	 *
	 * @param file   the file
	 * @param config the config
	 * @return the string
	 * @throws FileUploadException the file upload exception
	 */
	@Override
	public String upload(UploadData file, UploadConfig config) throws FileUploadException {
		if (file == null) {
			throw new FileUploadException("Upload file is empty");
		}
		return upload(Collections.singletonList(file), config).stream().findFirst().orElse(null);
	}

	/**
	 * Upload.
	 *
	 * @param files  the files
	 * @param config the config
	 * @return the list
	 * @throws FileUploadException the file upload exception
	 */
	@Override
	public List<String> upload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		if (files == null || files.isEmpty()) {
			throw new FileUploadException("Upload file is empty");
		}
		if (config == null) {
			throw new FileUploadException("Upload config is empty");
		}
		testUploadConfig(config);
		return doUpload(files, config);
	}

	/**
	 * Test upload config.
	 *
	 * @param config the config
	 * @throws FileUploadException the file upload exception
	 */
	protected abstract void testUploadConfig(UploadConfig config) throws FileUploadException;

	/**
	 * Gets the file name.
	 *
	 * @param file   the file
	 * @param config the config
	 * @return the file name
	 */
	protected String getFileName(UploadData file, UploadConfig config) {
		String fileName = file.getFileName();
		String extension = file.getExtension();
		if (StringUtils.isEmpty(fileName)) {
			fileName = Long.toString(System.nanoTime());
		}
		String name = null;
		if (config != null && config.isAddDatePath()) {
			name = datePath() + "/" + encodingFilename(fileName);
		} else {
			name = encodingFilename(fileName);
		}

		if (!name.contains(".") && StringUtils.isEmpty(extension)) {
			extension = MimeTypeUtils.IMAGE_JPEG;
		}
		if (StringUtils.isNotEmpty(extension) && !name.endsWith(extension)) {
			return name + "." + extension;
		}
		return name;
	}

	/**
	 * Date path.
	 *
	 * @return the string
	 */
	private String datePath() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}

	/**
	 * Encoding filename.
	 *
	 * @param fileName the file name
	 * @return the string
	 */
	private String encodingFilename(String fileName) {
		fileName = fileName.replace("_", " ");
		return fileName;
	}

	/**
	 * Gets the url.
	 *
	 * @param baseUrl  the base url
	 * @param fileName the file name
	 * @return the url
	 */
	protected String getUrl(String baseUrl, String fileName) {
		if (!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		if (fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		return baseUrl + fileName;
	}

	/**
	 * Do upload.
	 *
	 * @param files  the files
	 * @param config the config
	 * @return the list
	 * @throws FileUploadException the file upload exception
	 */
	protected abstract List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException;

}
