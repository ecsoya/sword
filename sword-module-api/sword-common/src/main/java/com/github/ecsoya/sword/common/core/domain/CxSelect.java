package com.github.ecsoya.sword.common.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * The Class CxSelect.
 */
public class CxSelect implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The v. */
	private String v;

	/** The n. */
	private String n;

	/** The s. */
	private List<CxSelect> s;

	/**
	 * Instantiates a new cx select.
	 */
	public CxSelect() {
	}

	/**
	 * Instantiates a new cx select.
	 *
	 * @param v 数据值字段名称.
	 * @param n 数据标题字段名称.
	 */
	public CxSelect(String v, String n) {
		this.v = v;
		this.n = n;
	}

	/**
	 * Gets the 子集数据字段名称.
	 *
	 * @return the 子集数据字段名称
	 */
	public List<CxSelect> getS() {
		return s;
	}

	/**
	 * Sets the 数据标题字段名称.
	 *
	 * @param n the new 数据标题字段名称
	 */
	public void setN(String n) {
		this.n = n;
	}

	/**
	 * Gets the 数据标题字段名称.
	 *
	 * @return the 数据标题字段名称
	 */
	public String getN() {
		return n;
	}

	/**
	 * Sets the 子集数据字段名称.
	 *
	 * @param s the new 子集数据字段名称
	 */
	public void setS(List<CxSelect> s) {
		this.s = s;
	}

	/**
	 * Gets the 数据值字段名称.
	 *
	 * @return the 数据值字段名称
	 */
	public String getV() {
		return v;
	}

	/**
	 * Sets the 数据值字段名称.
	 *
	 * @param v the new 数据值字段名称
	 */
	public void setV(String v) {
		this.v = v;
	}
}
