package com.github.ecsoya.sword.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class ChatHistory.
 */
public class ChatHistory extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The user id. */
	private Long userId;

	/** The chat id. */
	private Long chatId;

	/** The message. */
	private String message;

	/** The status. */
	private Integer status;

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
	 * Sets the 聊天ID.
	 *
	 * @param chatId the new 聊天ID
	 */
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	/**
	 * Gets the 聊天ID.
	 *
	 * @return the 聊天ID
	 */
	public Long getChatId() {
		return chatId;
	}

	/**
	 * Sets the 消息.
	 *
	 * @param message the new 消息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the 消息.
	 *
	 * @return the 消息
	 */
	public String getMessage() {
		return message;
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
				.append("userId", getUserId()).append("chatId", getChatId()).append("message", getMessage())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
