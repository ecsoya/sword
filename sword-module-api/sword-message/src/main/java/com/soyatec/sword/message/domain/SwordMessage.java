package com.soyatec.sword.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 消息对象 t_sword_message
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-05-20
 */
public class SwordMessage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer TYPE_MESSAGE = 0;
	public static final Integer TYPE_REPLY = 1;

	public static final Integer STATUS_NONE = 0;
	public static final Integer STATUS_PUBLISHED = 1;

	/** ID */
	private Long id;

	/** 创建人 */
	private Long userId;

	/** 类型 */
	private Integer type;

	/** 正文 */
	private String content;

	/** 状态（0正常 1关闭） */
	private Integer status;

	/** 发布人数 */
	private Integer publish;

	/** 阅读人数 */
	private Integer read;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("type", getType()).append("content", getContent())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}
}
