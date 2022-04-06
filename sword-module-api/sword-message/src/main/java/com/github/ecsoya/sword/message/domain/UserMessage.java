package com.github.ecsoya.sword.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.message.IMessageType;

/**
 * The Class UserMessage.
 */
public class UserMessage extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_MESSAGE. */
	public static final Integer TYPE_MESSAGE = IMessageType.TYPE_MESSAGE;

	/** The Constant TYPE_NOTICE. */
	public static final Integer TYPE_NOTICE = IMessageType.TYPE_NOTICE;

	/** The Constant TYPE_INFORM. */
	public static final Integer TYPE_INFORM = IMessageType.TYPE_INFORM;

	/** The Constant TYPE_CHAT. */
	public static final Integer TYPE_CHAT = IMessageType.TYPE_CHAT;

	/** The Constant STATUS_NONE. */
	public static final Integer STATUS_NONE = 0;

	/** The Constant STATUS_READ. */
	public static final Integer STATUS_READ = 1;

	/** The id. */
	private Long id;

	/** The user id. */
	private Long userId;

	/** The type. */
	private Integer type;

	/** The message id. */
	private Long messageId;

	/** The content. */
	private String content;

	/** The title. */
	private String title;

	/** The status. */
	private Integer status;

	/** The del flag. */
	private Integer delFlag;

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
	 * Sets the 用户ID.
	 *
	 * @param userId the new 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户ID.
	 *
	 * @return the 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 类型.
	 *
	 * @param type the new 类型
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型.
	 *
	 * @return the 类型
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the $column.
	 *
	 * @param messageId the new $column
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * Sets the 状态.
	 *
	 * @param status the new 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("type", getType()).append("messageId", getMessageId())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the del flag.
	 *
	 * @return the del flag
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	/**
	 * Sets the del flag.
	 *
	 * @param delFlag the new del flag
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
