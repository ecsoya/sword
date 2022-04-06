package com.github.ecsoya.sword.common.utils.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class HTMLFilter.
 */
public final class HTMLFilter {

	/** The Constant REGEX_FLAGS_SI. */
	private static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;

	/** The Constant P_COMMENTS. */
	private static final Pattern P_COMMENTS = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);

	/** The Constant P_COMMENT. */
	private static final Pattern P_COMMENT = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);

	/** The Constant P_TAGS. */
	private static final Pattern P_TAGS = Pattern.compile("<(.*?)>", Pattern.DOTALL);

	/** The Constant P_END_TAG. */
	private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);

	/** The Constant P_START_TAG. */
	private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);

	/** The Constant P_QUOTED_ATTRIBUTES. */
	private static final Pattern P_QUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);

	/** The Constant P_UNQUOTED_ATTRIBUTES. */
	private static final Pattern P_UNQUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);

	/** The Constant P_PROTOCOL. */
	private static final Pattern P_PROTOCOL = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);

	/** The Constant P_ENTITY. */
	private static final Pattern P_ENTITY = Pattern.compile("&#(\\d+);?");

	/** The Constant P_ENTITY_UNICODE. */
	private static final Pattern P_ENTITY_UNICODE = Pattern.compile("&#x([0-9a-f]+);?");

	/** The Constant P_ENCODE. */
	private static final Pattern P_ENCODE = Pattern.compile("%([0-9a-f]{2});?");

	/** The Constant P_VALID_ENTITIES. */
	private static final Pattern P_VALID_ENTITIES = Pattern.compile("&([^&;]*)(?=(;|&|$))");

	/** The Constant P_VALID_QUOTES. */
	private static final Pattern P_VALID_QUOTES = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);

	/** The Constant P_END_ARROW. */
	private static final Pattern P_END_ARROW = Pattern.compile("^>");

	/** The Constant P_BODY_TO_END. */
	private static final Pattern P_BODY_TO_END = Pattern.compile("<([^>]*?)(?=<|$)");

	/** The Constant P_XML_CONTENT. */
	private static final Pattern P_XML_CONTENT = Pattern.compile("(^|>)([^<]*?)(?=>)");

	/** The Constant P_STRAY_LEFT_ARROW. */
	private static final Pattern P_STRAY_LEFT_ARROW = Pattern.compile("<([^>]*?)(?=<|$)");

	/** The Constant P_STRAY_RIGHT_ARROW. */
	private static final Pattern P_STRAY_RIGHT_ARROW = Pattern.compile("(^|>)([^<]*?)(?=>)");

	/** The Constant P_AMP. */
	private static final Pattern P_AMP = Pattern.compile("&");

	/** The Constant P_QUOTE. */
	private static final Pattern P_QUOTE = Pattern.compile("\"");

	/** The Constant P_LEFT_ARROW. */
	private static final Pattern P_LEFT_ARROW = Pattern.compile("<");

	/** The Constant P_RIGHT_ARROW. */
	private static final Pattern P_RIGHT_ARROW = Pattern.compile(">");

	/** The Constant P_BOTH_ARROWS. */
	private static final Pattern P_BOTH_ARROWS = Pattern.compile("<>");

	/** The Constant P_REMOVE_PAIR_BLANKS. */
	// @xxx could grow large... maybe use sesat's ReferenceMap
	private static final ConcurrentMap<String, Pattern> P_REMOVE_PAIR_BLANKS = new ConcurrentHashMap<>();

	/** The Constant P_REMOVE_SELF_BLANKS. */
	private static final ConcurrentMap<String, Pattern> P_REMOVE_SELF_BLANKS = new ConcurrentHashMap<>();

	/** The v allowed. */
	private final Map<String, List<String>> vAllowed;

	/** The v tag counts. */
	private final Map<String, Integer> vTagCounts = new HashMap<>();

	/** The v self closing tags. */
	private final String[] vSelfClosingTags;

	/** The v need closing tags. */
	private final String[] vNeedClosingTags;

	/** The v disallowed. */
	private final String[] vDisallowed;

	/** The v protocol atts. */
	private final String[] vProtocolAtts;

	/** The v allowed protocols. */
	private final String[] vAllowedProtocols;

	/** The v remove blanks. */
	private final String[] vRemoveBlanks;

	/** The v allowed entities. */
	private final String[] vAllowedEntities;

	/** The strip comment. */
	private final boolean stripComment;

	/** The encode quotes. */
	private final boolean encodeQuotes;

	/** The always make tags. */
	private final boolean alwaysMakeTags;

	/**
	 * Instantiates a new HTML filter.
	 */
	public HTMLFilter() {
		vAllowed = new HashMap<>();

		final ArrayList<String> a_atts = new ArrayList<>();
		a_atts.add("href");
		a_atts.add("target");
		vAllowed.put("a", a_atts);

		final ArrayList<String> img_atts = new ArrayList<>();
		img_atts.add("src");
		img_atts.add("width");
		img_atts.add("height");
		img_atts.add("alt");
		vAllowed.put("img", img_atts);

		final ArrayList<String> no_atts = new ArrayList<>();
		vAllowed.put("b", no_atts);
		vAllowed.put("strong", no_atts);
		vAllowed.put("i", no_atts);
		vAllowed.put("em", no_atts);

		vSelfClosingTags = new String[] { "img" };
		vNeedClosingTags = new String[] { "a", "b", "strong", "i", "em" };
		vDisallowed = new String[] {};
		vAllowedProtocols = new String[] { "http", "mailto", "https" }; // no ftp.
		vProtocolAtts = new String[] { "src", "href" };
		vRemoveBlanks = new String[] { "a", "b", "strong", "i", "em" };
		vAllowedEntities = new String[] { "amp", "gt", "lt", "quot" };
		stripComment = true;
		encodeQuotes = true;
		alwaysMakeTags = false;
	}

	/**
	 * Instantiates a new HTML filter.
	 *
	 * @param conf the conf
	 */
	@SuppressWarnings("unchecked")
	public HTMLFilter(final Map<String, Object> conf) {

		assert conf.containsKey("vAllowed") : "configuration requires vAllowed";
		assert conf.containsKey("vSelfClosingTags") : "configuration requires vSelfClosingTags";
		assert conf.containsKey("vNeedClosingTags") : "configuration requires vNeedClosingTags";
		assert conf.containsKey("vDisallowed") : "configuration requires vDisallowed";
		assert conf.containsKey("vAllowedProtocols") : "configuration requires vAllowedProtocols";
		assert conf.containsKey("vProtocolAtts") : "configuration requires vProtocolAtts";
		assert conf.containsKey("vRemoveBlanks") : "configuration requires vRemoveBlanks";
		assert conf.containsKey("vAllowedEntities") : "configuration requires vAllowedEntities";

		vAllowed = Collections.unmodifiableMap((HashMap<String, List<String>>) conf.get("vAllowed"));
		vSelfClosingTags = (String[]) conf.get("vSelfClosingTags");
		vNeedClosingTags = (String[]) conf.get("vNeedClosingTags");
		vDisallowed = (String[]) conf.get("vDisallowed");
		vAllowedProtocols = (String[]) conf.get("vAllowedProtocols");
		vProtocolAtts = (String[]) conf.get("vProtocolAtts");
		vRemoveBlanks = (String[]) conf.get("vRemoveBlanks");
		vAllowedEntities = (String[]) conf.get("vAllowedEntities");
		stripComment = conf.containsKey("stripComment") ? (Boolean) conf.get("stripComment") : true;
		encodeQuotes = conf.containsKey("encodeQuotes") ? (Boolean) conf.get("encodeQuotes") : true;
		alwaysMakeTags = conf.containsKey("alwaysMakeTags") ? (Boolean) conf.get("alwaysMakeTags") : true;
	}

	/**
	 * Reset.
	 */
	private void reset() {
		vTagCounts.clear();
	}

	// ---------------------------------------------------------------
	/**
	 * Chr.
	 *
	 * @param decimal the decimal
	 * @return the string
	 */
	// my versions of some PHP library functions
	public static String chr(final int decimal) {
		return String.valueOf((char) decimal);
	}

	/**
	 * Html special chars.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String htmlSpecialChars(final String s) {
		String result = s;
		result = regexReplace(P_AMP, "&amp;", result);
		result = regexReplace(P_QUOTE, "&quot;", result);
		result = regexReplace(P_LEFT_ARROW, "&lt;", result);
		result = regexReplace(P_RIGHT_ARROW, "&gt;", result);
		return result;
	}

	// ---------------------------------------------------------------

	/**
	 * Filter.
	 *
	 * @param input the input
	 * @return the string
	 */
	public String filter(final String input) {
		reset();
		String s = input;

		s = escapeComments(s);

		s = balanceHTML(s);

		s = checkTags(s);

		s = processRemoveBlanks(s);

		// s = validateEntities(s);

		return s;
	}

	/**
	 * Checks if is flag determining whether to try to make tags when presented with
	 * "unbalanced" angle brackets (e.
	 *
	 * @return the flag determining whether to try to make tags when presented with
	 *         "unbalanced" angle brackets (e
	 */
	public boolean isAlwaysMakeTags() {
		return alwaysMakeTags;
	}

	/**
	 * Checks if is strip comments.
	 *
	 * @return true, if is strip comments
	 */
	public boolean isStripComments() {
		return stripComment;
	}

	/**
	 * Escape comments.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String escapeComments(final String s) {
		final Matcher m = P_COMMENTS.matcher(s);
		final StringBuffer buf = new StringBuffer();
		if (m.find()) {
			final String match = m.group(1); // (.*?)
			m.appendReplacement(buf, Matcher.quoteReplacement("<!--" + htmlSpecialChars(match) + "-->"));
		}
		m.appendTail(buf);

		return buf.toString();
	}

	/**
	 * Balance HTML.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String balanceHTML(String s) {
		if (alwaysMakeTags) {
			//
			// try and form html
			//
			s = regexReplace(P_END_ARROW, "", s);
			// 不追加结束标签
			s = regexReplace(P_BODY_TO_END, "<$1>", s);
			s = regexReplace(P_XML_CONTENT, "$1<$2", s);

		} else {
			//
			// escape stray brackets
			//
			s = regexReplace(P_STRAY_LEFT_ARROW, "&lt;$1", s);
			s = regexReplace(P_STRAY_RIGHT_ARROW, "$1$2&gt;<", s);

			//
			// the last regexp causes '<>' entities to appear
			// (we need to do a lookahead assertion so that the last bracket can
			// be used in the next pass of the regexp)
			//
			s = regexReplace(P_BOTH_ARROWS, "", s);
		}

		return s;
	}

	/**
	 * Check tags.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String checkTags(String s) {
		final Matcher m = P_TAGS.matcher(s);

		final StringBuffer buf = new StringBuffer();
		while (m.find()) {
			String replaceStr = m.group(1);
			replaceStr = processTag(replaceStr);
			m.appendReplacement(buf, Matcher.quoteReplacement(replaceStr));
		}
		m.appendTail(buf);

		// these get tallied in processTag
		// (remember to reset before subsequent calls to filter method)
		final StringBuilder sBuilder = new StringBuilder(buf.toString());
		for (final String key : vTagCounts.keySet()) {
			for (int ii = 0; ii < vTagCounts.get(key); ii++) {
				sBuilder.append("</").append(key).append(">");
			}
		}
		s = sBuilder.toString();

		return s;
	}

	/**
	 * Process remove blanks.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String processRemoveBlanks(final String s) {
		String result = s;
		for (final String tag : vRemoveBlanks) {
			if (!P_REMOVE_PAIR_BLANKS.containsKey(tag)) {
				P_REMOVE_PAIR_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?></" + tag + ">"));
			}
			result = regexReplace(P_REMOVE_PAIR_BLANKS.get(tag), "", result);
			if (!P_REMOVE_SELF_BLANKS.containsKey(tag)) {
				P_REMOVE_SELF_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?/>"));
			}
			result = regexReplace(P_REMOVE_SELF_BLANKS.get(tag), "", result);
		}

		return result;
	}

	/**
	 * Regex replace.
	 *
	 * @param regex_pattern the regex pattern
	 * @param replacement   the replacement
	 * @param s             the s
	 * @return the string
	 */
	private static String regexReplace(final Pattern regex_pattern, final String replacement, final String s) {
		final Matcher m = regex_pattern.matcher(s);
		return m.replaceAll(replacement);
	}

	/**
	 * Process tag.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String processTag(final String s) {
		// ending tags
		Matcher m = P_END_TAG.matcher(s);
		if (m.find()) {
			final String name = m.group(1).toLowerCase();
			if (allowed(name)) {
				if (false == inArray(name, vSelfClosingTags)) {
					if (vTagCounts.containsKey(name)) {
						vTagCounts.put(name, vTagCounts.get(name) - 1);
						return "</" + name + ">";
					}
				}
			}
		}

		// starting tags
		m = P_START_TAG.matcher(s);
		if (m.find()) {
			final String name = m.group(1).toLowerCase();
			final String body = m.group(2);
			String ending = m.group(3);

			// debug( "in a starting tag, name='" + name + "'; body='" + body + "';
			// ending='" + ending + "'" );
			if (allowed(name)) {
				final StringBuilder params = new StringBuilder();

				final Matcher m2 = P_QUOTED_ATTRIBUTES.matcher(body);
				final Matcher m3 = P_UNQUOTED_ATTRIBUTES.matcher(body);
				final List<String> paramNames = new ArrayList<>();
				final List<String> paramValues = new ArrayList<>();
				while (m2.find()) {
					paramNames.add(m2.group(1)); // ([a-z0-9]+)
					paramValues.add(m2.group(3)); // (.*?)
				}
				while (m3.find()) {
					paramNames.add(m3.group(1)); // ([a-z0-9]+)
					paramValues.add(m3.group(3)); // ([^\"\\s']+)
				}

				String paramName, paramValue;
				for (int ii = 0; ii < paramNames.size(); ii++) {
					paramName = paramNames.get(ii).toLowerCase();
					paramValue = paramValues.get(ii);

					// debug( "paramName='" + paramName + "'" );
					// debug( "paramValue='" + paramValue + "'" );
					// debug( "allowed? " + vAllowed.get( name ).contains( paramName ) );

					if (allowedAttribute(name, paramName)) {
						if (inArray(paramName, vProtocolAtts)) {
							paramValue = processParamProtocol(paramValue);
						}
						params.append(' ').append(paramName).append("=\"").append(paramValue).append("\"");
					}
				}

				if (inArray(name, vSelfClosingTags)) {
					ending = " /";
				}

				if (inArray(name, vNeedClosingTags)) {
					ending = "";
				}

				if (ending == null || ending.length() < 1) {
					if (vTagCounts.containsKey(name)) {
						vTagCounts.put(name, vTagCounts.get(name) + 1);
					} else {
						vTagCounts.put(name, 1);
					}
				} else {
					ending = " /";
				}
				return "<" + name + params + ending + ">";
			} else {
				return "";
			}
		}

		// comments
		m = P_COMMENT.matcher(s);
		if (!stripComment && m.find()) {
			return "<" + m.group() + ">";
		}

		return "";
	}

	/**
	 * Process param protocol.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String processParamProtocol(String s) {
		s = decodeEntities(s);
		final Matcher m = P_PROTOCOL.matcher(s);
		if (m.find()) {
			final String protocol = m.group(1);
			if (!inArray(protocol, vAllowedProtocols)) {
				// bad protocol, turn into local anchor link instead
				s = "#" + s.substring(protocol.length() + 1);
				if (s.startsWith("#//")) {
					s = "#" + s.substring(3);
				}
			}
		}

		return s;
	}

	/**
	 * Decode entities.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String decodeEntities(String s) {
		StringBuffer buf = new StringBuffer();

		Matcher m = P_ENTITY.matcher(s);
		while (m.find()) {
			final String match = m.group(1);
			final int decimal = Integer.decode(match).intValue();
			m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
		}
		m.appendTail(buf);
		s = buf.toString();

		buf = new StringBuffer();
		m = P_ENTITY_UNICODE.matcher(s);
		while (m.find()) {
			final String match = m.group(1);
			final int decimal = Integer.valueOf(match, 16).intValue();
			m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
		}
		m.appendTail(buf);
		s = buf.toString();

		buf = new StringBuffer();
		m = P_ENCODE.matcher(s);
		while (m.find()) {
			final String match = m.group(1);
			final int decimal = Integer.valueOf(match, 16).intValue();
			m.appendReplacement(buf, Matcher.quoteReplacement(chr(decimal)));
		}
		m.appendTail(buf);
		s = buf.toString();

		s = validateEntities(s);
		return s;
	}

	/**
	 * Validate entities.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String validateEntities(final String s) {
		final StringBuffer buf = new StringBuffer();

		// validate entities throughout the string
		final Matcher m = P_VALID_ENTITIES.matcher(s);
		while (m.find()) {
			final String one = m.group(1); // ([^&;]*)
			final String two = m.group(2); // (?=(;|&|$))
			m.appendReplacement(buf, Matcher.quoteReplacement(checkEntity(one, two)));
		}
		m.appendTail(buf);

		return encodeQuotes(buf.toString());
	}

	/**
	 * Encode quotes.
	 *
	 * @param s the s
	 * @return the string
	 */
	private String encodeQuotes(final String s) {
		if (encodeQuotes) {
			final StringBuffer buf = new StringBuffer();
			final Matcher m = P_VALID_QUOTES.matcher(s);
			while (m.find()) {
				final String one = m.group(1); // (>|^)
				final String two = m.group(2); // ([^<]+?)
				final String three = m.group(3); // (<|$)
				// 不替换双引号为&quot;，防止json格式无效 regexReplace(P_QUOTE, "&quot;", two)
				m.appendReplacement(buf, Matcher.quoteReplacement(one + two + three));
			}
			m.appendTail(buf);
			return buf.toString();
		} else {
			return s;
		}
	}

	/**
	 * Check entity.
	 *
	 * @param preamble the preamble
	 * @param term     the term
	 * @return the string
	 */
	private String checkEntity(final String preamble, final String term) {

		return ";".equals(term) && isValidEntity(preamble) ? '&' + preamble : "&amp;" + preamble;
	}

	/**
	 * Checks if is valid entity.
	 *
	 * @param entity the entity
	 * @return true, if is valid entity
	 */
	private boolean isValidEntity(final String entity) {
		return inArray(entity, vAllowedEntities);
	}

	/**
	 * In array.
	 *
	 * @param s     the s
	 * @param array the array
	 * @return true, if successful
	 */
	private static boolean inArray(final String s, final String[] array) {
		for (final String item : array) {
			if (item != null && item.equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Allowed.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	private boolean allowed(final String name) {
		return (vAllowed.isEmpty() || vAllowed.containsKey(name)) && !inArray(name, vDisallowed);
	}

	/**
	 * Allowed attribute.
	 *
	 * @param name      the name
	 * @param paramName the param name
	 * @return true, if successful
	 */
	private boolean allowedAttribute(final String name, final String paramName) {
		return allowed(name) && (vAllowed.isEmpty() || vAllowed.get(name).contains(paramName));
	}
}