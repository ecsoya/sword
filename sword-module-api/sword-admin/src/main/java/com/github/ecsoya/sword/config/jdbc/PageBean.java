package com.github.ecsoya.sword.config.jdbc;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * The Class PageBean.
 *
 * @param <T> the generic type
 */
public class PageBean<T> implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8656597559014685635L;

	/** The total. */
	private long total; // 总记录数

	/** The list. */
	private List<T> list; // 结果集

	/** The page num. */
	private int pageNum; // 第几页

	/** The page size. */
	private int pageSize; // 每页记录数

	/** The pages. */
	private int pages; // 总页数

	/** The size. */
	private int size; // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性

	/**
	 * Instantiates a new page bean.
	 */
	public PageBean() {
	}

	/**
	 * Instantiates a new page bean.
	 *
	 * @param list the list
	 */
	public PageBean(List<T> list) {
		if (list instanceof Page) {
			final Page<T> page = (Page<T>) list;
			this.pageNum = page.getPageNum();
			this.pageSize = page.getPageSize();
			this.total = page.getTotal();
			this.pages = page.getPages();
			this.list = page;
			this.size = page.size();
		}
	}

	/**
	 * Gets the total.
	 *
	 * @return the total
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list the new list
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * Gets the page num.
	 *
	 * @return the page num
	 */
	public int getPageNum() {
		return pageNum;
	}

	/**
	 * Sets the page num.
	 *
	 * @param pageNum the new page num
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * Gets the page size.
	 *
	 * @return the page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * Sets the page size.
	 *
	 * @param pageSize the new page size
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Gets the pages.
	 *
	 * @return the pages
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * Sets the pages.
	 *
	 * @param pages the new pages
	 */
	public void setPages(int pages) {
		this.pages = pages;
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	public void setSize(int size) {
		this.size = size;
	}

}