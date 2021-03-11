package com.soyatec.sword.common.utils.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.web.multipart.MultipartFile;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.region.Region;
import com.soyatec.sword.common.utils.StringUtils;

import io.github.ecsoya.uploader.TencentFileUploader;
import io.github.ecsoya.uploader.core.FileUploadException;
import io.github.ecsoya.uploader.core.UploadConfig;
import io.github.ecsoya.uploader.core.UploadData;

/**
 * 文件上传工具类
 * 
 * @author Jin Liu (angryred@qq.com)
 */
public class FileUploadUtils {
	private static UploadConfig config;

	private static final String accelerateUrl = "https://beecluster-1301683227.file.myqcloud.com";
	private static final String normalUrl = "https://beecluster-1301683227.cos.ap-chongqing.myqcloud.com";

	/**
	 * 根据文件路径上传
	 * 
	 * @param file 上传的文件
	 *
	 * @return 文件名称
	 * @throws IOException
	 */
	public static final String upload(MultipartFile file) throws Exception {
		String url = new TencentFileUploader(getUploadConfig()).upload(UploadData.build(file));
		return url.replaceAll(normalUrl, accelerateUrl);
	}

	private static final UploadConfig getUploadConfig() {
		if (config == null) {
			config = UploadConfig.build().setBaseUrl("https://beecluster-1301683227.file.myqcloud.com")
					.setAccessKey("AKIDTY0IKIkDj01IMAeJbeRqyRjkVEHn41kD").setBucket("beecluster-1301683227")
					.setEndpoint("ap-chongqing").setSecretKey("M3TeZDbjPVKuBCwwf9NLnyvUnujIf3GK");
		}
		return config;
	}

	public static String upload(String fileName, InputStream inputStream) throws Exception {
		String url = new TencentFileUploader(getUploadConfig()).upload(UploadData.build(fileName, inputStream));
		return url.replaceAll(normalUrl, accelerateUrl);
	}

	protected static String getFileName(UploadData file) {
		String fileName = file.getFileName();
		String extension = file.getExtension();
		if (StringUtils.isEmpty(fileName)) {
			fileName = Long.toString(System.nanoTime());
		}
		String name = null;
		if (config.isAddDatePath()) {
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
	 * 编码文件名
	 */
	private static String encodingFilename(String fileName) {
		fileName = fileName.replace("_", " ");
		return fileName;
	}

	private static String datePath() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}

	private static String doUpload(UploadData file, UploadConfig config, String encoding) throws FileUploadException {
		COSCredentials cred = new BasicCOSCredentials(config.getAccessKey(), config.getSecretKey());
		String endpoint = config.getEndpoint();
		ClientConfig clientConfig = new ClientConfig(new Region(endpoint));
		// 3 生成cos客户端
		COSClient client = new COSClient(cred, clientConfig);

		String bucket = config.getBucket();
		String contentType = file.getContentType();
		Long length = file.getLength();
		String path = getFileName(file);
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
				if (encoding != null) {
					metadata.setContentEncoding(encoding);
				}
				client.putObject(bucket, path, inputStream, metadata);
			} else if (file.getDatas() != null && file.getDatas().length != 0) {
				client.putObject(bucket, path, new String(file.getDatas()));
			}
			return "https://" + bucket + ".cos." + endpoint + ".myqcloud.com/" + path;
		} catch (Exception e) {
			return null;
		} finally {
			client.shutdown();
		}
	}

	public static String uploadHtml(String fileName, String html) throws Exception {
		if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(html)) {
			return null;
		}
		UploadData data = new UploadData();
		byte[] bytes = html.getBytes(Charset.forName("utf-8"));
		data.setDatas(bytes);
		data.setFileName(fileName);
		data.setLength(Integer.valueOf(bytes.length).longValue());
		data.setContentType("text/html; charset=utf-8");
		return new TencentFileUploader(getUploadConfig()).upload(data);
	}

	public static String replace(String url, String code) {
		return url;
	}
}