package com.github.ecsoya.sword.common.core.domain;

/**
 * The Class TreeEntity.
 */
public class TreeEntity extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The parent name. */
	private String parentName;

	/** The parent id. */
	private Long parentId;

	/** The order num. */
	private Integer orderNum;

	/** The ancestors. */
	private String ancestors;

	/**
	 * Gets the 父菜单名称.
	 *
	 * @return the 父菜单名称
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * Sets the 父菜单名称.
	 *
	 * @param parentName the new 父菜单名称
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * Gets the 父菜单ID.
	 *
	 * @return the 父菜单ID
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the 父菜单ID.
	 *
	 * @param parentId the new 父菜单ID
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * Gets the 显示顺序.
	 *
	 * @return the 显示顺序
	 */
	public Integer getOrderNum() {
		return orderNum;
	}

	/**
	 * Sets the 显示顺序.
	 *
	 * @param orderNum the new 显示顺序
	 */
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
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
}