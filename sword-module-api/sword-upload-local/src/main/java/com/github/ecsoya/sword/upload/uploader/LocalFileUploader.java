package com.github.ecsoya.sword.upload.uploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.upload.core.FileUploadException;
import com.github.ecsoya.sword.upload.core.UploadConfig;
import com.github.ecsoya.sword.upload.core.UploadData;

/**
 * The Class LocalFileUploader.
 */
@Component
@Service
public class LocalFileUploader extends AbstractFileUploader {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(LocalFileUploader.class);

	/** The context path. */
	@Value("${server.servlet.context-path:/}")
	private String contextPath;

	/**
	 * Test upload config.
	 *
	 * @param config the config
	 * @throws FileUploadException the file upload exception
	 */
	@Override
	protected void testUploadConfig(UploadConfig config) throws FileUploadException {
	}

	/**
	 * Do upload.
	 *
	 * @param files  the files
	 * @param config the config
	 * @return the list
	 * @throws FileUploadException the file upload exception
	 */
	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		log.info("LocalFileUploader, config={}", config);
		if (config == null) {
			throw new FileUploadException("配置错误");
		}
		if (files == null || files.isEmpty()) {
			return Collections.emptyList();
		}
		String uploadPath = GlobalConfig.getUploadPath();
		final File root = new File(uploadPath);
		log.debug("LocalFileUploader, uploadTo={}", root);
		if (!root.exists()) {
			try {
				root.mkdirs();
			} catch (final Exception e) {
				throw new FileUploadException("Could not init upload path");
			}
		}

		final List<String> result = new ArrayList<String>();
		for (final UploadData data : files) {
			final String fileName = getFileName(data, config);
			log.debug("LocalFileUploader, upload={}", fileName);
			final File target = new File(root, fileName);
			try {
				if (!target.getParentFile().exists()) {
					target.getParentFile().mkdirs();
				}
				final InputStream in = data.getInputStream();
				if (in != null && in.available() > 0) {
					Files.copy(in, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} else {
					final byte[] bytes = data.getDatas();
					if (bytes != null && bytes.length > 0) {
						Files.write(target.toPath(), bytes);
					}
				}
				result.add(getURL(config, getPathFileName(uploadPath, fileName)));
			} catch (final Exception e) {
				log.warn("LocalFileUploader, uploadFailed={}, error={}", fileName, e.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}

	/**
	 * Gets the url.
	 *
	 * @param config   the config
	 * @param filename the filename
	 * @return the url
	 */
	private String getURL(UploadConfig config, String filename) {
		return contextPath + filename;
	}

	/**
	 * Gets the path file name.
	 *
	 * @param uploadDir the upload dir
	 * @param fileName  the file name
	 * @return the path file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private final String getPathFileName(String uploadDir, String fileName) throws IOException {
		int dirLastIndex = GlobalConfig.getProfile().length() + 1;
		String currentDir = StringUtils.substring(uploadDir, dirLastIndex);
		String pathFileName = Constants.RESOURCE_PREFIX + "/" + currentDir + "/" + fileName;
		return pathFileName;
	}

}
