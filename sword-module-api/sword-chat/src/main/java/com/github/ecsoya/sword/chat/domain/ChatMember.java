package com.github.ecsoya.sword.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class ChatMember.
 */
public class ChatMember extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The chat id. */
	private Long chatId;

	/** The user id. */
	private Long userId;

	/**
	 * Sets the $column.
	 *
	 * @param id the new $column
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the $column.
	 *
	 * @param chatId the new $column
	 */
	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public Long getChatId() {
		return chatId;
	}

	/**
	 * Sets the $column.
	 *
	 * @param userId the new $column
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("chatId", getChatId()).append("userId", getUserId()).toString();
	}
}
