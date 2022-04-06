package com.github.ecsoya.sword.common.core.text;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class CharsetKit.
 */
public class CharsetKit {

	/** The Constant ISO_8859_1. */
	public static final String ISO_8859_1 = "ISO-8859-1";

	/** The Constant UTF_8. */
	public static final String UTF_8 = "UTF-8";

	/** The Constant GBK. */
	public static final String GBK = "GBK";

	/** The Constant CHARSET_ISO_8859_1. */
	public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);

	/** The Constant CHARSET_UTF_8. */
	public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);

	/** The Constant CHARSET_GBK. */
	public static final Charset CHARSET_GBK = Charset.forName(GBK);

	/**
	 * Charset.
	 *
	 * @param charset the charset
	 * @return the charset
	 */
	public static Charset charset(String charset) {
		return StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset);
	}

	/**
	 * Convert.
	 *
	 * @param source      the source
	 * @param srcCharset  the src charset
	 * @param destCharset the dest charset
	 * @return the string
	 */
	public static String convert(String source, String srcCharset, String destCharset) {
		return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
	}

	/**
	 * Convert.
	 *
	 * @param source      the source
	 * @param srcCharset  the src charset
	 * @param destCharset the dest charset
	 * @return the string
	 */
	public static String convert(String source, Charset srcCharset, Charset destCharset) {
		if (null == srcCharset) {
			srcCharset = StandardCharsets.ISO_8859_1;
		}

		if (null == destCharset) {
			destCharset = StandardCharsets.UTF_8;
		}

		if (StringUtils.isEmpty(source) || srcCharset.equals(destCharset)) {
			return source;
		}
		return new String(source.getBytes(srcCharset), destCharset);
	}

	/**
	 * System charset.
	 *
	 * @return the string
	 */
	public static String systemCharset() {
		return Charset.defaultCharset().name();
	}
}
