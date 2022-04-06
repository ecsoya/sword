package com.github.ecsoya.sword.common.core.domain.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysDept.
 */
public class SysDept extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dept id. */
	private Long deptId;

	/** The parent id. */
	private Long parentId;

	/** The ancestors. */
	private String ancestors;

	/** The dept name. */
	private String deptName;

	/** The order num. */
	private String orderNum;

	/** The leader. */
	private String leader;

	/** The phone. */
	private String phone;

	/** The email. */
	private String email;

	/** The status. */
	private String status;

	/** The del flag. */
	private String delFlag;

	/** The parent name. */
	private String parentName;

	/**
	 * Gets the 部门ID.
	 *
	 * @return the 部门ID
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * Sets the 部门ID.
	 *
	 * @param deptId the new 部门ID
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * Gets the 父部门ID.
	 *
	 * @return the 父部门ID
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the 父部门ID.
	 *
	 * @param parentId the new 父部门ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the 祖级列表.
	 *
	 * @return the 祖级列表
	 */
	public String getAncestors() {
		return ancestors;
	}

	/**
	 * Sets the 祖级列表.
	 *
	 * @param ancestors the new 祖级列表
	 */
	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}

	/**
	 * Gets the 部门名称.
	 *
	 * @return the 部门名称
	 */
	@NotBlank(message = "部门名称不能为空")
	@Size(min = 0, max = 30, message = "部门名称长度不能超过30个字符")
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the 部门名称.
	 *
	 * @param deptName the new 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the 显示顺序.
	 *
	 * @return the 显示顺序
	 */
	@NotBlank(message = "显示顺序不能为空")
	public String getOrderNum() {
		return orderNum;
	}

	/**
	 * Sets the 显示顺序.
	 *
	 * @param orderNum the new 显示顺序
	 */
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * Gets the 负责人.
	 *
	 * @return the 负责人
	 */
	public String getLeader() {
		return leader;
	}

	/**
	 * Sets the 负责人.
	 *
	 * @param leader the new 负责人
	 */
	public void setLeader(String leader) {
		this.leader = leader;
	}

	/**
	 * Gets the 联系电话.
	 *
	 * @return the 联系电话
	 */
	@Size(min = 0, max = 11, message = "联系电话长度不能超过11个字符")
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the 联系电话.
	 *
	 * @param phone the new 联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the 邮箱.
	 *
	 * @return the 邮箱
	 */
	@Email(message = "邮箱格式不正确")
	@Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the 邮箱.
	 *
	 * @param email the new 邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the 部门状态:0正常,1停用.
	 *
	 * @return the 部门状态:0正常,1停用
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 部门状态:0正常,1停用.
	 *
	 * @param status the new 部门状态:0正常,1停用
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 删除标志（0代表存在 2代表删除）.
	 *
	 * @return the 删除标志（0代表存在 2代表删除）
	 */
	public String getDelFlag() {
		return delFlag;
	}

	/**
	 * Sets the 删除标志（0代表存在 2代表删除）.
	 *
	 * @param delFlag the new 删除标志（0代表存在 2代表删除）
	 */
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * Gets the 父部门名称.
	 *
	 * @return the 父部门名称
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * Sets the 父部门名称.
	 *
	 * @param parentName the new 父部门名称
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("deptId", getDeptId())
				.append("parentId", getParentId()).append("ancestors", getAncestors()).append("deptName", getDeptName())
				.append("orderNum", getOrderNum()).append("leader", getLeader()).append("phone", getPhone())
				.append("email", getEmail()).append("status", getStatus()).append("delFlag", getDelFlag())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).toString();
	}
}
