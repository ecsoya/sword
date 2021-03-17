package com.soyatec.sword.article.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 文章对象 t_sword_article
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-02-04
 */
public class SwordArticle extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_NONE = 0;
	public static final Integer STATUS_PUBLISH = 1;

	/** ID */
	private Long id;

	/** 标题 */
	@Excel(name = "标题")
	private String title;

	private String category;

	/** 简介 */
	@Excel(name = "简介")
	private String description;

	/** 图片 */
	@Excel(name = "图片")
	private String imageUrl;

	/** 正文 */
	@Excel(name = "正文")
	private String content;

	/** 状态（0正常 1关闭） */
	@Excel(name = "状态", readConverterExp = "0=正常,1=关闭")
	private Integer status;

	private Integer reads;

	private Integer likes;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
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
				.append("title", getTitle()).append("description", getDescription()).append("imageUrl", getImageUrl())
				.append("content", getContent()).append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	public Integer getReads() {
		return reads;
	}

	public void setReads(Integer reads) {
		this.reads = reads;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
