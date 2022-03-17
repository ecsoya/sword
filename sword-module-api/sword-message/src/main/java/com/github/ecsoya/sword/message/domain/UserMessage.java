package com.github.ecsoya.sword.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.message.IMessageType;

/**
 * 用户消息对象 t_user_message
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-05-20
 */
public class UserMessage extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer TYPE_MESSAGE = IMessageType.TYPE_MESSAGE;
	public static final Integer TYPE_NOTICE = IMessageType.TYPE_NOTICE;
	public static final Integer TYPE_INFORM = IMessageType.TYPE_INFORM;
	public static final Integer TYPE_CHAT = IMessageType.TYPE_CHAT;

	public static final Integer STATUS_NONE = 0;
	public static final Integer STATUS_READ = 1;

	/** ID */
	private Long id;

	/** 用户ID */
	private Long userId;

	/** 类型 */
	private Integer type;

	/** $column.columnComment */
	private Long messageId;

	private String content;

	private String title;

	/** 状态 */
	private Integer status;

	private Integer delFlag;

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

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public Long getMessageId() {
		return messageId;
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
				.append("userId", getUserId()).append("type", getType()).append("messageId", getMessageId())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
