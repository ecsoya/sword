package com.soyatec.sword.code.config;

public class CodeProperties {

	public static final String DEFAULT_TEMPLATE = "亲爱的用户，您的验证码为：{}，请勿告诉他人，5分钟内有效！";

	private String template = DEFAULT_TEMPLATE;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
