package com.github.ecsoya.sword.message.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SwordMessage.
 */
public class SwordMessage extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_MESSAGE. */
	public static final Integer TYPE_MESSAGE = 0;

	/** The Constant TYPE_REPLY. */
	public static final Integer TYPE_REPLY = 1;

	/** The Constant STATUS_NONE. */
	public static final Integer STATUS_NONE = 0;

	/** The Constant STATUS_PUBLISHED. */
	public static final Integer STATUS_PUBLISHED = 1;

	/** The id. */
	private Long id;

	/** The user id. */
	private Long userId;

	/** The type. */
	private Integer type;

	/** The title. */
	private String title;

	/** The content. */
	private String content;

	/** The status. */
	private Integer status;

	/** The publish. */
	private Integer publish;

	/** The read. */
	private Integer read;

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
	 * Sets the 创建人.
	 *
	 * @param userId the new 创建人
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 创建人.
	 *
	 * @return the 创建人
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
	 * Sets the 正文.
	 *
	 * @param content the new 正文
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the 正文.
	 *
	 * @return the 正文
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the 状态（0正常 1关闭）.
	 *
	 * @param status the new 状态（0正常 1关闭）
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态（0正常 1关闭）.
	 *
	 * @return the 状态（0正常 1关闭）
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
				.append("userId", getUserId()).append("type", getType()).append("content", getContent())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	/**
	 * Gets the 发布人数.
	 *
	 * @return the 发布人数
	 */
	public Integer getPublish() {
		return publish;
	}

	/**
	 * Sets the 发布人数.
	 *
	 * @param publish the new 发布人数
	 */
	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	/**
	 * Gets the 阅读人数.
	 *
	 * @return the 阅读人数
	 */
	public Integer getRead() {
		return read;
	}

	/**
	 * Sets the 阅读人数.
	 *
	 * @param read the new 阅读人数
	 */
	public void setRead(Integer read) {
		this.read = read;
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
