package com.github.ecsoya.sword.article.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserArticle.
 */
public class UserArticle extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LIKE_YES. */
	public static final Integer LIKE_YES = 1;

	/** The Constant LIKE_NO. */
	public static final Integer LIKE_NO = 0;

	/** The Constant READ_YES. */
	public static final Integer READ_YES = 1;

	/** The Constant READ_NO. */
	public static final Integer READ_NO = 0;

	/** The id. */
	private Long id;

	/** The article id. */
	@Excel(name = "文章ID")
	private Long articleId;

	/** The user id. */
	@Excel(name = "用户ID")
	private Long userId;

	/** The read. */
	@Excel(name = "已读")
	private Integer read;

	/** The like. */
	@Excel(name = "点赞")
	private Integer like;

	/** The comment. */
	@Excel(name = "回复")
	private String comment;

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
	 * Sets the 文章ID.
	 *
	 * @param articleId the new 文章ID
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * Gets the 文章ID.
	 *
	 * @return the 文章ID
	 */
	public Long getArticleId() {
		return articleId;
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
	 * Sets the 已读.
	 *
	 * @param read the new 已读
	 */
	public void setRead(Integer read) {
		this.read = read;
	}

	/**
	 * Gets the 已读.
	 *
	 * @return the 已读
	 */
	public Integer getRead() {
		return read;
	}

	/**
	 * Sets the 点赞.
	 *
	 * @param like the new 点赞
	 */
	public void setLike(Integer like) {
		this.like = like;
	}

	/**
	 * Gets the 点赞.
	 *
	 * @return the 点赞
	 */
	public Integer getLike() {
		return like;
	}

	/**
	 * Sets the 回复.
	 *
	 * @param comment the new 回复
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the 回复.
	 *
	 * @return the 回复
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("articleId", getArticleId()).append("userId", getUserId()).append("read", getRead())
				.append("like", getLike()).append("comment", getComment()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
