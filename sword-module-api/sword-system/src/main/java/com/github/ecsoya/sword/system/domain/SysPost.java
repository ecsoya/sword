package com.github.ecsoya.sword.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysPost.
 */
public class SysPost extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The post id. */
	@Excel(name = "岗位序号", cellType = ColumnType.NUMERIC)
	private Long postId;

	/** The post code. */
	@Excel(name = "岗位编码")
	private String postCode;

	/** The post name. */
	@Excel(name = "岗位名称")
	private String postName;

	/** The post sort. */
	@Excel(name = "岗位排序", cellType = ColumnType.NUMERIC)
	private String postSort;

	/** The status. */
	@Excel(name = "状态", readConverterExp = "0=正常,1=停用")
	private String status;

	/** The flag. */
	private boolean flag = false;

	/**
	 * Gets the 岗位序号.
	 *
	 * @return the 岗位序号
	 */
	public Long getPostId() {
		return postId;
	}

	/**
	 * Sets the 岗位序号.
	 *
	 * @param postId the new 岗位序号
	 */
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	/**
	 * Gets the 岗位编码.
	 *
	 * @return the 岗位编码
	 */
	@NotBlank(message = "岗位编码不能为空")
	@Size(min = 0, max = 64, message = "岗位编码长度不能超过64个字符")
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Sets the 岗位编码.
	 *
	 * @param postCode the new 岗位编码
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Gets the 岗位名称.
	 *
	 * @return the 岗位名称
	 */
	@NotBlank(message = "岗位名称不能为空")
	@Size(min = 0, max = 50, message = "岗位名称长度不能超过50个字符")
	public String getPostName() {
		return postName;
	}

	/**
	 * Sets the 岗位名称.
	 *
	 * @param postName the new 岗位名称
	 */
	public void setPostName(String postName) {
		this.postName = postName;
	}

	/**
	 * Gets the 岗位排序.
	 *
	 * @return the 岗位排序
	 */
	@NotBlank(message = "显示顺序不能为空")
	public String getPostSort() {
		return postSort;
	}

	/**
	 * Sets the 岗位排序.
	 *
	 * @param postSort the new 岗位排序
	 */
	public void setPostSort(String postSort) {
		this.postSort = postSort;
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
	 * Checks if is 用户是否存在此岗位标识 默认不存在.
	 *
	 * @return the 用户是否存在此岗位标识 默认不存在
	 */
	public boolean isFlag() {
		return flag;
	}

	/**
	 * Sets the 用户是否存在此岗位标识 默认不存在.
	 *
	 * @param flag the new 用户是否存在此岗位标识 默认不存在
	 */
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("postId", getPostId())
				.append("postCode", getPostCode()).append("postName", getPostName()).append("postSort", getPostSort())
				.append("status", getStatus()).append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
