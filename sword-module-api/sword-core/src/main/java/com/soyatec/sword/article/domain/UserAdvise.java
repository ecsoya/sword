package com.soyatec.sword.article.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 用户反馈对象 t_user_advise
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserAdvise extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer TYPE_FEEDBACK = 0;
	public static final Integer TYPE_REPLY = 1;

	/** ID */
	private Long id;

	/** 父ID */
	@Excel(name = "父ID")
	private Long parentId;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	private String loginName;

	/** 标题 */
	@Excel(name = "标题")
	private String title;

	/** 描述 */
	@Excel(name = "描述")
	private String description;

	/** 类型 0-反馈 1-回复 */
	@Excel(name = "类型 0-反馈 1-回复")
	private Integer type;

	/** 类别 */
	@Excel(name = "类别")
	private Integer kind;

	/** 状态 0-正常 1-关闭 */
	@Excel(name = "状态 0-正常 1-关闭")
	private Integer status;

	private List<UserAdvise> replies;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getKind() {
		return kind;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("parentId", getParentId()).append("userId", getUserId()).append("title", getTitle())
				.append("description", getDescription()).append("type", getType()).append("kind", getKind())
				.append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).toString();
	}

	public List<UserAdvise> getReplies() {
		return replies;
	}

	public void setReplies(List<UserAdvise> replies) {
		this.replies = replies;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
