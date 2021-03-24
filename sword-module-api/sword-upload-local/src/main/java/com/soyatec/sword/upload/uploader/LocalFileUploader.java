package com.soyatec.sword.upload.uploader;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.soyatec.sword.upload.core.FileUploadException;
import com.soyatec.sword.upload.core.UploadConfig;
import com.soyatec.sword.upload.core.UploadData;

@Service
public class LocalFileUploader extends AbstractFileUploader {

	private static Logger log = LoggerFactory.getLogger(LocalFileUploader.class);

	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		log.info("LocalFileUploader, config={}", config);
		if (config == null) {
			throw new FileUploadException("配置错误");
		}
		config.testLocalValidated();
		if (files == null || files.isEmpty()) {
			return Collections.emptyList();
		}
		File root = new File(config.getUploadPath());
		log.debug("LocalFileUploader, uploadTo={}", root);
		if (!root.exists()) {
			try {
				root.mkdirs();
			} catch (Exception e) {
				throw new FileUploadException("Could not init upload path");
			}
		}

		List<String> result = new ArrayList<String>();
		for (UploadData data : files) {
			String fileName = getFileName(data, config);
			log.debug("LocalFileUploader, upload={}", fileName);
			File target = new File(root, fileName);
			try {
				InputStream in = data.getInputStream();
				if (in != null && in.available() > 0) {
					Files.copy(in, target.toPath());
				} else {
					byte[] bytes = data.getDatas();
					if (bytes != null && bytes.length > 0) {
						Files.write(target.toPath(), bytes);
					}
				}
				result.add(target.getAbsolutePath());
			} catch (Exception e) {
				log.warn("LocalFileUploader, uploadFailed={}, error={}", fileName, e.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}

}
