package com.github.ecsoya.sword.common.utils.file;

/**
 * The Class MimeTypeUtils.
 */
public class MimeTypeUtils {

	/** The Constant IMAGE_PNG. */
	public static final String IMAGE_PNG = "image/png";

	/** The Constant IMAGE_JPG. */
	public static final String IMAGE_JPG = "image/jpg";

	/** The Constant IMAGE_JPEG. */
	public static final String IMAGE_JPEG = "image/jpeg";

	/** The Constant IMAGE_BMP. */
	public static final String IMAGE_BMP = "image/bmp";

	/** The Constant IMAGE_GIF. */
	public static final String IMAGE_GIF = "image/gif";

	/** The Constant IMAGE_EXTENSION. */
	public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

	/** The Constant FLASH_EXTENSION. */
	public static final String[] FLASH_EXTENSION = { "swf", "flv" };

	/** The Constant MEDIA_EXTENSION. */
	public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
			"asf", "rm", "rmvb" };

	/** The Constant VIDEO_EXTENSION. */
	public static final String[] VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };

	/** The Constant DEFAULT_ALLOWED_EXTENSION. */
	public static final String[] DEFAULT_ALLOWED_EXTENSION = {
			// 图片
			"bmp", "gif", "jpg", "jpeg", "png",
			// word excel powerpoint
			"doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
			// 压缩文件
			"rar", "zip", "gz", "bz2",
			// 视频格式
			"mp4", "avi", "rmvb",
			// pdf
			"pdf" };

	/**
	 * Gets the extension.
	 *
	 * @param prefix the prefix
	 * @return the extension
	 */
	public static String getExtension(String prefix) {
		switch (prefix) {
		case IMAGE_PNG:
			return "png";
		case IMAGE_JPG:
			return "jpg";
		case IMAGE_JPEG:
			return "jpeg";
		case IMAGE_BMP:
			return "bmp";
		case IMAGE_GIF:
			return "gif";
		default:
			return "";
		}
	}
}
