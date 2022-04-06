package com.github.ecsoya.sword.common.core.page;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class PageDomain.
 */
public class PageDomain {

	/** The page num. */
	private Integer pageNum;

	/** The page size. */
	private Integer pageSize;

	/** The order by column. */
	private String orderByColumn;

	/** The is asc. */
	private String isAsc = "asc";

	/**
	 * Gets the order by.
	 *
	 * @return the order by
	 */
	public String getOrderBy() {
		if (StringUtils.isEmpty(orderByColumn)) {
			return "";
		}
		return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
	}

	/**
	 * Gets the 当前记录起始索引.
	 *
	 * @return the 当前记录起始索引
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	/**
	 * Sets the 当前记录起始索引.
	 *
	 * @param pageNum the new 当前记录起始索引
	 */
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * Gets the 每页显示记录数.
	 *
	 * @return the 每页显示记录数
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the 每页显示记录数.
	 *
	 * @param pageSize the new 每页显示记录数
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Gets the 排序列.
	 *
	 * @return the 排序列
	 */
	public String getOrderByColumn() {
		return orderByColumn;
	}

	/**
	 * Sets the 排序列.
	 *
	 * @param orderByColumn the new 排序列
	 */
	public void setOrderByColumn(String orderByColumn) {
		this.orderByColumn = orderByColumn;
	}

	/**
	 * Gets the 排序的方向desc或者asc.
	 *
	 * @return the 排序的方向desc或者asc
	 */
	public String getIsAsc() {
		return isAsc;
	}

	/**
	 * Sets the 排序的方向desc或者asc.
	 *
	 * @param isAsc the new 排序的方向desc或者asc
	 */
	public void setIsAsc(String isAsc) {
		this.isAsc = isAsc;
	}
}
