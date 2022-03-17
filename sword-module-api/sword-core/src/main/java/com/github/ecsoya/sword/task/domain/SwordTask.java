package com.github.ecsoya.sword.task.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.constants.IConstants;

/**
 * 定时任务执行结果对象 t_sword_task
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class SwordTask extends BaseEntity implements IConstants {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date date;

	/** 状态 */
	@Excel(name = "状态")
	private Integer status;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId()).append("name", getName())
				.append("date", getDate()).append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	public boolean scheduled() {
		return STATUS_FINISHED.equals(status);
	}
}
