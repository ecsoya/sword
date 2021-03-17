package com.soyatec.sword.article.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soyatec.sword.common.core.domain.BaseEntity;

@JsonIgnoreProperties(value = { "remark", "params", "searchValue", "createBy", "updateBy" })
public class UserArticleComment extends BaseEntity {

	private static final long serialVersionUID = -7321314159312773091L;

	private String avatar;

	private String loginName;

	private Long articleId;

	private Long userId;

	private String comment;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
