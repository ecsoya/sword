package com.github.ecsoya.sword.article.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SwordArticle.
 */
public class SwordArticle extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant STATUS_NONE. */
	public static final Integer STATUS_NONE = 0;

	/** The Constant STATUS_PUBLISH. */
	public static final Integer STATUS_PUBLISH = 1;

	/** The id. */
	private Long id;

	/** The title. */
	@Excel(name = "标题")
	private String title;

	/** The category. */
	private String category;

	/** The description. */
	@Excel(name = "简介")
	private String description;

	/** The image url. */
	@Excel(name = "图片")
	private String imageUrl;

	/** The content. */
	@Excel(name = "正文")
	private String content;

	/** The status. */
	@Excel(name = "状态", readConverterExp = "0=正常,1=关闭")
	private Integer status;

	/** The reads. */
	private Integer reads;

	/** The likes. */
	private Integer likes;

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
	 * Sets the 简介.
	 *
	 * @param description the new 简介
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the 简介.
	 *
	 * @return the 简介
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the 图片.
	 *
	 * @param imageUrl the new 图片
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Gets the 图片.
	 *
	 * @return the 图片
	 */
	public String getImageUrl() {
		return imageUrl;
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
				.append("title", getTitle()).append("description", getDescription()).append("imageUrl", getImageUrl())
				.append("content", getContent()).append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	/**
	 * Gets the reads.
	 *
	 * @return the reads
	 */
	public Integer getReads() {
		return reads;
	}

	/**
	 * Sets the reads.
	 *
	 * @param reads the new reads
	 */
	public void setReads(Integer reads) {
		this.reads = reads;
	}

	/**
	 * Gets the likes.
	 *
	 * @return the likes
	 */
	public Integer getLikes() {
		return likes;
	}

	/**
	 * Sets the likes.
	 *
	 * @param likes the new likes
	 */
	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	/**
	 * Gets the category.
	 *
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Sets the category.
	 *
	 * @param category the new category
	 */
	public void setCategory(String category) {
		this.category = category;
	}
}
