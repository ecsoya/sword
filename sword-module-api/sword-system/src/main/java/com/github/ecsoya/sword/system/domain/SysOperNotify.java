package com.github.ecsoya.sword.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysOperNotify.
 */
public class SysOperNotify extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_EMAIL. */
	public static final Integer TYPE_EMAIL = 0;

	/** The Constant TYPE_MOBILE. */
	public static final Integer TYPE_MOBILE = 1;

	/** The id. */
	private Long id;

	/** The type. */
	@Excel(name = "类型：0-邮件 1-短信")
	private Integer type;

	/** The operate url. */
	@Excel(name = "URL")
	private String operateUrl;

	/** The subject. */
	@Excel(name = "主题")
	private String subject;

	/** The template. */
	@Excel(name = "模板")
	private String template;

	/**
	 * Sets the iD.
	 *
	 * @param id the new iD
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the 类型：0-邮件 1-短信.
	 *
	 * @param type the new 类型：0-邮件 1-短信
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型：0-邮件 1-短信.
	 *
	 * @return the 类型：0-邮件 1-短信
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the uRL.
	 *
	 * @param operateUrl the new uRL
	 */
	public void setOperateUrl(String operateUrl) {
		this.operateUrl = operateUrl;
	}

	/**
	 * Gets the uRL.
	 *
	 * @return the uRL
	 */
	public String getOperateUrl() {
		return operateUrl;
	}

	/**
	 * Sets the 主题.
	 *
	 * @param subject the new 主题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Gets the 主题.
	 *
	 * @return the 主题
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the 模板.
	 *
	 * @param template the new 模板
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * Gets the 模板.
	 *
	 * @return the 模板
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("type", getType())
				.append("operateUrl", getOperateUrl()).append("subject", getSubject()).append("template", getTemplate())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
