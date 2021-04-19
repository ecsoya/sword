package com.soyatec.sword.code.config;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.soyatec.sword.common.utils.StringUtils;

public class CodeProperties {

	public static final String DEFAULT_TEMPLATE = "亲爱的用户，您的验证码为：{}，请勿告诉他人，5分钟内有效！";

	/**
	 * 默认模板
	 */
	private String template = DEFAULT_TEMPLATE;

	/**
	 * 多语言支持模板
	 * 
	 * Locale.language = templates
	 */
	private Map<String, String> templates = new HashMap<>(0);

	/**
	 * 默认主题
	 */
	private String subject;

	/**
	 * 多语言支持主题
	 * 
	 * Locale.language = subject
	 */
	private Map<String, String> subjects = new HashMap<>(0);

	private int priority;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Map<String, String> getTemplates() {
		return templates;
	}

	public void setTemplates(Map<String, String> templates) {
		this.templates = templates;
	}

	public String getTemplate(Locale locale) {
		if (locale != null && templates != null && !templates.isEmpty()) {
			String result = templates.get(locale.getLanguage());
			if (StringUtils.isNotEmpty(result)) {
				return result;
			}
		}
		return template;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, String> getSubjects() {
		return subjects;
	}

	public void setSubjects(Map<String, String> subjects) {
		this.subjects = subjects;
	}

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
