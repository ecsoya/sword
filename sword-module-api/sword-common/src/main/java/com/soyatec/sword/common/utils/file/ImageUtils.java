package com.soyatec.sword.common.utils.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.constant.Constants;

/**
 * 图片处理工具类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class ImageUtils {
	private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

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
	 * 读取文件为字节数据
	 *
	 * @param key 地址
	 * @return 字节数据
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
			IOUtils.closeQuietly(baos);
		}
	}
}
