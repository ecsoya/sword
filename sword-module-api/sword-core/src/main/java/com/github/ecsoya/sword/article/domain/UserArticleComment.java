package com.github.ecsoya.sword.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserArticleComment.
 */
@JsonIgnoreProperties(value = { "remark", "params", "searchValue", "createBy", "updateBy" })
public class UserArticleComment extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7321314159312773091L;

	/** The avatar. */
	private String avatar;

	/** The login name. */
	private String loginName;

	/** The article id. */
	private Long articleId;

	/** The user id. */
	private Long userId;

	/** The comment. */
	private String comment;

	/**
	 * Gets the avatar.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Sets the avatar.
	 *
	 * @param avatar the new avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
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

	/**
	 * Gets the article id.
	 *
	 * @return the article id
	 */
	public Long getArticleId() {
		return articleId;
	}

	/**
	 * Sets the article id.
	 *
	 * @param articleId the new article id
	 */
	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	/**
	 * Gets the comment.
	 *
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 *
	 * @param comment the new comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
