package com.github.ecsoya.sword.code.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class CodeProperties.
 */
public class CodeProperties {

	/** The Constant DEFAULT_TEMPLATE. */
	public static final String DEFAULT_TEMPLATE = "亲爱的用户，您的验证码为：{}，请勿告诉他人，5分钟内有效！";

	/** The template. */
	private String template = DEFAULT_TEMPLATE;

	/** The templates. */
	private Map<String, String> templates = new HashMap<>(0);

	/** The subject. */
	private String subject;

	/** The subjects. */
	private Map<String, String> subjects = new HashMap<>(0);

	/** The priority. */
	private int priority;

	/**
	 * Gets the 默认模板.
	 *
	 * @return the 默认模板
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * Sets the 默认模板.
	 *
	 * @param template the new 默认模板
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * Gets the 多语言支持模板 Locale.
	 *
	 * @return the 多语言支持模板 Locale
	 */
	public Map<String, String> getTemplates() {
		return templates;
	}

	/**
	 * Sets the 多语言支持模板 Locale.
	 *
	 * @param templates the new 多语言支持模板 Locale
	 */
	public void setTemplates(Map<String, String> templates) {
		this.templates = templates;
	}

	/**
	 * Gets the template.
	 *
	 * @param locale the locale
	 * @return the template
	 */
	public String getTemplate(Locale locale) {
		if (locale != null && templates != null && !templates.isEmpty()) {
			String result = templates.get(locale.getLanguage());
			if (StringUtils.isNotEmpty(result)) {
				return result;
			}
		}
		return template;
	}

	/**
	 * Gets the 默认主题.
	 *
	 * @return the 默认主题
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the 默认主题.
	 *
	 * @param subject the new 默认主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the 多语言支持主题 Locale.
	 *
	 * @return the 多语言支持主题 Locale
	 */
	public Map<String, String> getSubjects() {
		return subjects;
	}

	/**
	 * Sets the 多语言支持主题 Locale.
	 *
	 * @param subjects the new 多语言支持主题 Locale
	 */
	public void setSubjects(Map<String, String> subjects) {
		this.subjects = subjects;
	}

	/**
	 * Gets the subject.
	 *
	 * @param locale the locale
	 * @return the subject
	 */
	public String getSubject(Locale locale) {
		if (locale != null && subjects != null && !subjects.isEmpty()) {
			String result = subjects.get(locale.getLanguage());
			if (StringUtils.isNotEmpty(result)) {
				return result;
			}
		}
		return subject;
	}
}
