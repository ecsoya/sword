package com.soyatec.sword.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 敏感操作通知对象 sys_oper_notify
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-04-06
 */
public class SysOperNotify extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer TYPE_EMAIL = 0;
	public static final Integer TYPE_MOBILE = 1;

	/** ID */
	private Long id;

	/** 类型：0-邮件 1-短信 */
	@Excel(name = "类型：0-邮件 1-短信")
	private Integer type;

	/** URL */
	@Excel(name = "URL")
	private String operateUrl;

	/** 主题 */
	@Excel(name = "主题")
	private String subject;

	/** 模板 */
	@Excel(name = "模板")
	private String template;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setOperateUrl(String operateUrl) {
		this.operateUrl = operateUrl;
	}

	public String getOperateUrl() {
		return operateUrl;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("type", getType())
				.append("operateUrl", getOperateUrl()).append("subject", getSubject()).append("template", getTemplate())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
