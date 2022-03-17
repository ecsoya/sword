package com.github.ecsoya.sword.version.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * 版本对象 t_sword_version
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-22
 */
@JsonIgnoreProperties(value = { "remark", "params", "updateTime", "createBy", "searchValue", "updateBy" })
public class SwordVersion extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final String TYPE_IOS = "ios";
	public static final String TYPE_ANDROID = "android";

	/** ID */
	private Long id;

	/** 版本号 */
	@Excel(name = "版本号")
	private Long version;

	/** 类型 */
	@Excel(name = "类型")
	private String type;

	/** 下载链接 */
	@Excel(name = "下载链接")
	private String url;

	/** 描述 */
	@Excel(name = "描述")
	private String description;

	/** 状态 */
	@Excel(name = "状态")
	private Integer status;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getVersion() {
		return version;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
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
				.append("version", getVersion()).append("type", getType()).append("url", getUrl())
				.append("description", getDescription()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}
}
