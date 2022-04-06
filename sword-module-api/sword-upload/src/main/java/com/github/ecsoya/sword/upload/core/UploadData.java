package com.github.ecsoya.sword.upload.core;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * The Class UploadData.
 */
public class UploadData {

	/** The extension. */
	private String extension;

	/** The input stream. */
	private InputStream inputStream;

	/** The datas. */
	private byte[] datas;

	/** The content type. */
	private String contentType;

	/** The length. */
	private Long length;

	/** The file name. */
	private String fileName;

	/**
	 * Builds the.
	 *
	 * @param fileName    the file name
	 * @param inputStream the input stream
	 * @return the upload data
	 */
	public static UploadData build(String fileName, InputStream inputStream) {
		final UploadData uploadData = new UploadData();
		uploadData.setFileName(fileName);
		uploadData.setInputStream(inputStream);
		return uploadData;
	}

	/**
	 * Builds the.
	 *
	 * @param file the file
	 * @return the upload data
	 * @throws FileUploadException the file upload exception
	 */
	public static UploadData build(MultipartFile file) throws FileUploadException {
		if (file == null) {
			throw new RuntimeException("Upload file is empty");
		}

		UploadData uploadData;
		try {
			final InputStream inputStream = file.getInputStream();
			final byte[] datas = file.getBytes();
			uploadData = new UploadData();

			final String name = file.getOriginalFilename();
			if (name != null && name.indexOf(".") != -1) {
				uploadData.setExtension(name.substring(name.lastIndexOf(".")));
			}
			uploadData.setFileName(name);
			uploadData.setContentType(file.getContentType());
			uploadData.setLength(file.getSize());
			uploadData.setInputStream(inputStream);
			uploadData.setDatas(datas);
			return uploadData;
		} catch (final IOException e) {
			throw new FileUploadException(e.getLocalizedMessage());
		}
	}

	/**
	 * Gets the extension.
	 *
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Sets the extension.
	 *
	 * @param extension the new extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Gets the 输入流.
	 *
	 * @return the 输入流
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * Sets the 输入流.
	 *
	 * @param inputStream the new 输入流
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * Gets the 数据.
	 *
	 * @return the 数据
	 */
	public byte[] getDatas() {
		return datas;
	}

	/**
	 * Sets the 数据.
	 *
	 * @param datas the new 数据
	 */
	public void setDatas(byte[] datas) {
		this.datas = datas;
	}

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public Long getLength() {
		return length;
	}

	/**
	 * Sets the length.
	 *
	 * @param length the new length
	 */
	public void setLength(Long length) {
		this.length = length;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
