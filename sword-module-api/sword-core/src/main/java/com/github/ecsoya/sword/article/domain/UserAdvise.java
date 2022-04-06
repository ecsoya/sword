package com.github.ecsoya.sword.article.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserAdvise.
 */
public class UserAdvise extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_FEEDBACK. */
	public static final Integer TYPE_FEEDBACK = 0;

	/** The Constant TYPE_REPLY. */
	public static final Integer TYPE_REPLY = 1;

	/** The id. */
	private Long id;

	/** The parent id. */
	@Excel(name = "父ID")
	private Long parentId;

	/** The user id. */
	@Excel(name = "用户ID")
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The title. */
	@Excel(name = "标题")
	private String title;

	/** The description. */
	@Excel(name = "描述")
	private String description;

	/** The type. */
	@Excel(name = "类型 0-反馈 1-回复")
	private Integer type;

	/** The kind. */
	@Excel(name = "类别")
	private Integer kind;

	/** The status. */
	@Excel(name = "状态 0-正常 1-关闭")
	private Integer status;

	/** The replies. */
	private List<UserAdvise> replies;

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
	 * Sets the 父ID.
	 *
	 * @param parentId the new 父ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the 父ID.
	 *
	 * @return the 父ID
	 */
	public Long getParentId() {
		return parentId;
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
	 * Sets the 标题.
	 *
	 * @param title the new 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the 标题.
	 *
	 * @return the 标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the 描述.
	 *
	 * @param description the new 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the 描述.
	 *
	 * @return the 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the 类型 0-反馈 1-回复.
	 *
	 * @param type the new 类型 0-反馈 1-回复
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型 0-反馈 1-回复.
	 *
	 * @return the 类型 0-反馈 1-回复
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the 类别.
	 *
	 * @param kind the new 类别
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * Gets the 类别.
	 *
	 * @return the 类别
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * Sets the 状态 0-正常 1-关闭.
	 *
	 * @param status the new 状态 0-正常 1-关闭
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态 0-正常 1-关闭.
	 *
	 * @return the 状态 0-正常 1-关闭
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
				.append("parentId", getParentId()).append("userId", getUserId()).append("title", getTitle())
				.append("description", getDescription()).append("type", getType()).append("kind", getKind())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).toString();
	}

	/**
	 * Gets the replies.
	 *
	 * @return the replies
	 */
	public List<UserAdvise> getReplies() {
		return replies;
	}

	/**
	 * Sets the replies.
	 *
	 * @param replies the new replies
	 */
	public void setReplies(List<UserAdvise> replies) {
		this.replies = replies;
	}

	/**
	 * Gets the login name.
	 *
	 * @return the login name
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the login name.
	 *
	 * @param loginName the new login name
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
