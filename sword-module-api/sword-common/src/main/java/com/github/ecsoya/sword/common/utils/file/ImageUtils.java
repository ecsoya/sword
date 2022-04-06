package com.github.ecsoya.sword.common.utils.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class ImageUtils.
 */
public class ImageUtils {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

	/**
	 * Gets the image.
	 *
	 * @param imagePath the image path
	 * @return the image
	 */
	public static byte[] getImage(String imagePath) {
		final InputStream is = getFile(imagePath);
		try {
			return IOUtils.toByteArray(is);
		} catch (final Exception e) {
			log.error("图片加载异常 {}", e);
			return null;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * Gets the file.
	 *
	 * @param imagePath the image path
	 * @return the file
	 */
	public static InputStream getFile(String imagePath) {
		try {
			byte[] result = readFile(imagePath);
			result = Arrays.copyOf(result, result.length);
			return new ByteArrayInputStream(result);
		} catch (final Exception e) {
			log.error("获取图片异常 {}", e);
		}
		return null;
	}

	/**
	 * Read file.
	 *
	 * @param url the url
	 * @return the byte[]
	 */
	public static byte[] readFile(String url) {
		InputStream in = null;
		final ByteArrayOutputStream baos = null;
		try {
			if (url.startsWith("http")) {
				// 网络地址
				final URL urlObj = new URL(url);
				final URLConnection urlConnection = urlObj.openConnection();
				urlConnection.setConnectTimeout(30 * 1000);
				urlConnection.setReadTimeout(60 * 1000);
				urlConnection.setDoInput(true);
				in = urlConnection.getInputStream();
			} else {
				// 本机地址
				final String localPath = GlobalConfig.getProfile();
				final String downloadPath = localPath
						+ org.apache.commons.lang3.StringUtils.substringAfter(url, Constants.RESOURCE_PREFIX);
				in = new FileInputStream(downloadPath);
			}
			return IOUtils.toByteArray(in);
		} catch (final Exception e) {
			log.error("获取文件路径异常 {}", e);
			return null;
		} finally {
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(baos);
		}
	}

	/**
	 * Compress image.
	 *
	 * @param in    the in
	 * @param width the width
	 * @return the input stream
	 */
	public static InputStream compressImage(InputStream in, int width) {
		if (width <= 0) {
			return null;
		}
		try {
			BufferedImage image = ImageIO.read(in);
			int w = image.getWidth();
			int h = image.getHeight();
			if (width >= w) {
				return null;
			}

			int height = (int) (h * (width * 1.0 / w));
			BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			newImage.getGraphics().drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			return toInputStream(newImage, "jpg");
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * To input stream.
	 *
	 * @param image  the image
	 * @param format the format
	 * @return the input stream
	 */
	public static InputStream toInputStream(BufferedImage image, String format) {
		if (image == null) {
			return null;
		}
		if (StringUtils.isEmpty(format)) {
			format = "jpg";
		}
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();
			ImageIO.write(image, format, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (final Exception e) {
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (final IOException e) {
				}
			}
		}
		return null;
	}
}
