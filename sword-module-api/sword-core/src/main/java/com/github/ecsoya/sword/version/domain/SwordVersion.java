package com.github.ecsoya.sword.version.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SwordVersion.
 */
@JsonIgnoreProperties(value = { "remark", "params", "updateTime", "createBy", "searchValue", "updateBy" })
public class SwordVersion extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_IOS. */
	public static final String TYPE_IOS = "ios";

	/** The Constant TYPE_ANDROID. */
	public static final String TYPE_ANDROID = "android";

	/** The id. */
	private Long id;

	/** The version. */
	@Excel(name = "版本号")
	private Long version;

	/** The type. */
	@Excel(name = "类型")
	private String type;

	/** The url. */
	@Excel(name = "下载链接")
	private String url;

	/** The description. */
	@Excel(name = "描述")
	private String description;

	/** The status. */
	@Excel(name = "状态")
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
	 * Sets the 版本号.
	 *
	 * @param version the new 版本号
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	/**
	 * Gets the 版本号.
	 *
	 * @return the 版本号
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Sets the 类型.
	 *
	 * @param type the new 类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the 类型.
	 *
	 * @return the 类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the 下载链接.
	 *
	 * @param url the new 下载链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the 下载链接.
	 *
	 * @return the 下载链接
	 */
	public String getUrl() {
		return url;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("version", getVersion()).append("type", getType()).append("url", getUrl())
				.append("description", getDescription()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
