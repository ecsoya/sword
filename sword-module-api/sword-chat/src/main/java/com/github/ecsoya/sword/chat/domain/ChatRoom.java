package com.github.ecsoya.sword.chat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class ChatRoom.
 */
public class ChatRoom extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The uuid. */
	private String uuid;

	/** The user id. */
	private Long userId;

	/** The name. */
	private String name;

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
	 * Sets the 标识.
	 *
	 * @param uuid the new 标识
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * Gets the 标识.
	 *
	 * @return the 标识
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * Sets the 创建人ID.
	 *
	 * @param userId the new 创建人ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 创建人ID.
	 *
	 * @return the 创建人ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 名称.
	 *
	 * @param name the new 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 名称.
	 *
	 * @return the 名称
	 */
	public String getName() {
		return name;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("uuid", getUuid())
				.append("userId", getUserId()).append("name", getName()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
