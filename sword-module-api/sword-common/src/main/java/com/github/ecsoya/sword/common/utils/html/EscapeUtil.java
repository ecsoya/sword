package com.github.ecsoya.sword.common.utils.html;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class EscapeUtil.
 */
public class EscapeUtil {

	/** The Constant RE_HTML_MARK. */
	public static final String RE_HTML_MARK = "(<[^<]*?>)|(<[\\s]*?/[^<]*?>)|(<[^<]*?/[\\s]*?>)";

	/** The Constant TEXT. */
	private static final char[][] TEXT = new char[64][];

	static {
		for (int i = 0; i < 64; i++) {
			TEXT[i] = new char[] { (char) i };
		}

		// special HTML characters
		TEXT['\''] = "&#039;".toCharArray(); // 单引号
		TEXT['"'] = "&#34;".toCharArray(); // 单引号
		TEXT['&'] = "&#38;".toCharArray(); // &符
		TEXT['<'] = "&#60;".toCharArray(); // 小于号
		TEXT['>'] = "&#62;".toCharArray(); // 大于号
	}

	/**
	 * Escape.
	 *
	 * @param text the text
	 * @return the string
	 */
	public static String escape(String text) {
		return encode(text);
	}

	/**
	 * Unescape.
	 *
	 * @param content the content
	 * @return the string
	 */
	public static String unescape(String content) {
		return decode(content);
	}

	/**
	 * Clean.
	 *
	 * @param content the content
	 * @return the string
	 */
	public static String clean(String content) {
		return new HTMLFilter().filter(content);
	}

	/**
	 * Encode.
	 *
	 * @param text the text
	 * @return the string
	 */
	private static String encode(String text) {
		int len;
		if ((text == null) || ((len = text.length()) == 0)) {
			return org.apache.commons.lang3.StringUtils.EMPTY;
		}
		final StringBuilder buffer = new StringBuilder(len + (len >> 2));
		char c;
		for (int i = 0; i < len; i++) {
			c = text.charAt(i);
			if (c < 64) {
				buffer.append(TEXT[c]);
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	/**
	 * Decode.
	 *
	 * @param content the content
	 * @return the string
	 */
	public static String decode(String content) {
		if (StringUtils.isEmpty(content)) {
			return content;
		}

		final StringBuilder tmp = new StringBuilder(content.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < content.length()) {
			pos = content.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (content.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(content.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(content.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else if (pos == -1) {
				tmp.append(content.substring(lastPos));
				lastPos = content.length();
			} else {
				tmp.append(content.substring(lastPos, pos));
				lastPos = pos;
			}
		}
		return tmp.toString();
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final String html = "<script>alert(1);</script>";
		// String html = "<scr<script>ipt>alert(\"XSS\")</scr<script>ipt>";
		// String html = "<123";
		// String html = "123>";
		System.out.println(EscapeUtil.clean(html));
		System.out.println(EscapeUtil.escape(html));
		System.out.println(EscapeUtil.unescape(html));
	}
}
