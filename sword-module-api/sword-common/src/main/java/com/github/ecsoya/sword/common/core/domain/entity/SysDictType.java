package com.github.ecsoya.sword.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysDictType.
 */
public class SysDictType extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dict id. */
	@Excel(name = "字典主键", cellType = ColumnType.NUMERIC)
	private Long dictId;

	/** The dict name. */
	@Excel(name = "字典名称")
	private String dictName;

	/** The dict type. */
	@Excel(name = "字典类型")
	private String dictType;

	/** The status. */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/**
	 * Gets the 字典主键.
	 *
	 * @return the 字典主键
	 */
	public Long getDictId() {
		return dictId;
	}

	/**
	 * Sets the 字典主键.
	 *
	 * @param dictId the new 字典主键
	 */
	public void setDictId(Long dictId) {
		this.dictId = dictId;
	}

	/**
	 * Gets the 字典名称.
	 *
	 * @return the 字典名称
	 */
	@NotBlank(message = "字典名称不能为空")
	@Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
	public String getDictName() {
		return dictName;
	}

	/**
	 * Sets the 字典名称.
	 *
	 * @param dictName the new 字典名称
	 */
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	/**
	 * Gets the 字典类型.
	 *
	 * @return the 字典类型
	 */
	@NotBlank(message = "字典类型不能为空")
	@Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
	public String getDictType() {
		return dictType;
	}

	/**
	 * Sets the 字典类型.
	 *
	 * @param dictType the new 字典类型
	 */
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	/**
	 * Gets the 状态（0正常 1停用）.
	 *
	 * @return the 状态（0正常 1停用）
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 状态（0正常 1停用）.
	 *
	 * @param status the new 状态（0正常 1停用）
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("dictId", getDictId())
				.append("dictName", getDictName()).append("dictType", getDictType()).append("status", getStatus())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
