package com.github.ecsoya.sword.common.core.domain.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysDictData.
 */
public class SysDictData extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dict code. */
	@Excel(name = "字典编码", cellType = ColumnType.NUMERIC)
	private Long dictCode;

	/** The dict sort. */
	@Excel(name = "字典排序", cellType = ColumnType.NUMERIC)
	private Long dictSort;

	/** The dict label. */
	@Excel(name = "字典标签")
	private String dictLabel;

	/** The dict value. */
	@Excel(name = "字典键值")
	private String dictValue;

	/** The dict type. */
	@Excel(name = "字典类型")
	private String dictType;

	/** The css class. */
	@Excel(name = "字典样式")
	private String cssClass;

	/** The list class. */
	private String listClass;

	/** The is default. */
	@Excel(name = "是否默认", readConverterExp = "Y=是,N=否")
	private String isDefault;

	/** The status. */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/**
	 * Gets the 字典编码.
	 *
	 * @return the 字典编码
	 */
	public Long getDictCode() {
		return dictCode;
	}

	/**
	 * Sets the 字典编码.
	 *
	 * @param dictCode the new 字典编码
	 */
	public void setDictCode(Long dictCode) {
		this.dictCode = dictCode;
	}

	/**
	 * Gets the 字典排序.
	 *
	 * @return the 字典排序
	 */
	public Long getDictSort() {
		return dictSort;
	}

	/**
	 * Sets the 字典排序.
	 *
	 * @param dictSort the new 字典排序
	 */
	public void setDictSort(Long dictSort) {
		this.dictSort = dictSort;
	}

	/**
	 * Gets the 字典标签.
	 *
	 * @return the 字典标签
	 */
	@NotBlank(message = "字典标签不能为空")
	@Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
	public String getDictLabel() {
		return dictLabel;
	}

	/**
	 * Sets the 字典标签.
	 *
	 * @param dictLabel the new 字典标签
	 */
	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}

	/**
	 * Gets the 字典键值.
	 *
	 * @return the 字典键值
	 */
	@NotBlank(message = "字典键值不能为空")
	@Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
	public String getDictValue() {
		return dictValue;
	}

	/**
	 * Sets the 字典键值.
	 *
	 * @param dictValue the new 字典键值
	 */
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	/**
	 * Gets the 字典类型.
	 *
	 * @return the 字典类型
	 */
	@NotBlank(message = "字典类型不能为空")
	@Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
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
	 * Gets the 样式属性（其他样式扩展）.
	 *
	 * @return the 样式属性（其他样式扩展）
	 */
	@Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
	public String getCssClass() {
		return cssClass;
	}

	/**
	 * Sets the 样式属性（其他样式扩展）.
	 *
	 * @param cssClass the new 样式属性（其他样式扩展）
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * Gets the 表格字典样式.
	 *
	 * @return the 表格字典样式
	 */
	public String getListClass() {
		return listClass;
	}

	/**
	 * Sets the 表格字典样式.
	 *
	 * @param listClass the new 表格字典样式
	 */
	public void setListClass(String listClass) {
		this.listClass = listClass;
	}

	/**
	 * Gets the default.
	 *
	 * @return the default
	 */
	public boolean getDefault() {
		return UserConstants.YES.equals(this.isDefault) ? true : false;
	}

	/**
	 * Gets the 是否默认（Y是 N否）.
	 *
	 * @return the 是否默认（Y是 N否）
	 */
	public String getIsDefault() {
		return isDefault;
	}

	/**
	 * Sets the 是否默认（Y是 N否）.
	 *
	 * @param isDefault the new 是否默认（Y是 N否）
	 */
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("dictCode", getDictCode())
				.append("dictSort", getDictSort()).append("dictLabel", getDictLabel())
				.append("dictValue", getDictValue()).append("dictType", getDictType()).append("cssClass", getCssClass())
				.append("listClass", getListClass()).append("isDefault", getIsDefault()).append("status", getStatus())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
