package com.soyatec.sword.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 聊天成员对象 t_chat_member
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
public class ChatMember extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** $column.columnComment */
	private Long id;

	/** $column.columnComment */
	private Long chatId;

	/** $column.columnComment */
	private Long userId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setChatId(Long chatId) {
		this.chatId = chatId;
	}

	public Long getChatId() {
		return chatId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("chatId", getChatId()).append("userId", getUserId()).toString();
	}
}
