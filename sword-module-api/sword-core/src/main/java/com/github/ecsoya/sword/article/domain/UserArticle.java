package com.github.ecsoya.sword.article.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * 用户文章对象 t_user_article
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
public class UserArticle extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer LIKE_YES = 1;
	public static final Integer LIKE_NO = 0;
	public static final Integer READ_YES = 1;
	public static final Integer READ_NO = 0;

	/** ID */
	private Long id;

	/** 文章ID */
	@Excel(name = "文章ID")
	private Long articleId;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	/** 已读 */
	@Excel(name = "已读")
	private Integer read;

	/** 点赞 */
	@Excel(name = "点赞")
	private Integer like;

	/** 回复 */
	@Excel(name = "回复")
	private String comment;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getRead() {
		return read;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public Integer getLike() {
		return like;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("articleId", getArticleId()).append("userId", getUserId()).append("read", getRead())
				.append("like", getLike()).append("comment", getComment()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
