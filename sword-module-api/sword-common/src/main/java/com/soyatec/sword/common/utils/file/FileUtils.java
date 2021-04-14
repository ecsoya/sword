package com.soyatec.sword.common.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;

import com.soyatec.sword.common.utils.StringUtils;

/**
 * 文件处理工具类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

	/**
	 * 输出指定文件的byte数组
	 *
	 * @param filePath 文件路径
	 * @param os       输出流
	 * @return
	 */
	public static void writeBytes(String filePath, OutputStream os) throws IOException {
		FileInputStream fis = null;
		try {
			final File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException(filePath);
			}
			fis = new FileInputStream(file);
			final byte[] b = new byte[1024];
			int length;
			while ((length = fis.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件
	 *
	 * @param filePath 文件
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		final File file = new File(filePath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 文件名称验证
	 *
	 * @param filename 文件名称
	 * @return true 正常 false 非法
	 */
	public static boolean isValidFilename(String filename) {
		return filename.matches(FILENAME_PATTERN);
	}

	/**
	 * 检查文件是否可下载
	 *
	 * @param resource 需要下载的文件
	 * @return true 正常 false 非法
	 */
	public static boolean checkAllowDownload(String resource) {
		// 禁止目录上跳级别
		if (org.apache.commons.lang3.StringUtils.contains(resource, "..")) {
			return false;
		}

		// 检查允许下载的文件规则
		if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource))) {
			return true;
		}

		// 不在允许下载的文件规则
		return false;
	}

	/**
	 * 下载文件名重新编码
	 *
	 * @param request  请求对象
	 * @param fileName 文件名
	 * @return 编码后的文件名
	 */
	public static String setFileDownloadHeader(HttpServletRequest request, String fileName)
			throws UnsupportedEncodingException {
		final String agent = request.getHeader("USER-AGENT");
		String filename = fileName;
		if (agent.contains("MSIE")) {
			// IE浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐浏览器
			filename = new String(fileName.getBytes(), "ISO8859-1");
		} else if (agent.contains("Chrome")) {
			// google浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			// 其它浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

	/**
	 * 下载文件名重新编码
	 *
	 * @param response     响应对象
	 * @param realFileName 真实文件名
	 * @return
	 */
	public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName)
			throws UnsupportedEncodingException {
		final String percentEncodedFileName = percentEncode(realFileName);

		final StringBuilder contentDispositionValue = new StringBuilder();
		contentDispositionValue.append("attachment; filename=").append(percentEncodedFileName).append(";")
				.append("filename*=").append("utf-8''").append(percentEncodedFileName);

		response.setHeader("Content-disposition", contentDispositionValue.toString());
	}

	/**
	 * 百分号编码工具方法
	 *
	 * @param s 需要百分号编码的字符串
	 * @return 百分号编码后的字符串
	 */
	public static String percentEncode(String s) throws UnsupportedEncodingException {
		final String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
		return encode.replaceAll("\\+", "%20");
	}

	public static String downloadUrlAsString(String detailsUrl) {
		if (StringUtils.isEmpty(detailsUrl)) {
			return null;
		}
		try {
			final URL url = new URL(detailsUrl);
			final URLConnection conn = url.openConnection();
			final InputStream in = conn.getInputStream();
			final StringBuilder stringBuilder = new StringBuilder();
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			boolean firstLine = true;
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				if (!firstLine) {
					stringBuilder.append(System.getProperty("line.separator"));
				} else {
					firstLine = false;
				}
				stringBuilder.append(line);
			}
			return stringBuilder.toString();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
