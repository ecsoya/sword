package com.github.ecsoya.sword.task.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SwordTaskExample.
 */
public class SwordTaskExample extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The name. */
	@Excel(name = "名称")
	private String name;

	/** The date. */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date date;

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
	 * Sets the 名称.
	 *
	 * @param name the new 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 名称.
	 *
	 * @return the 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 时间.
	 *
	 * @param date the new 时间
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the 时间.
	 *
	 * @return the 时间
	 */
	public Date getDate() {
		return date;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("name", getName())
				.append("date", getDate()).append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
