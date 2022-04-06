package com.github.ecsoya.sword.common.core.page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class TableDataInfo.
 */
public class TableDataInfo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The total. */
	private long total;

	/** The rows. */
	private List<?> rows;

	/** The code. */
	private int code;

	/** The msg. */
	private String msg;

	/** The params. */
	private Map<String, Object> params;

	/**
	 * Instantiates a new table data info.
	 */
	public TableDataInfo() {
	}

	/**
	 * Instantiates a new table data info.
	 *
	 * @param list  the list
	 * @param total the total
	 */
	public TableDataInfo(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	/**
	 * Gets the 总记录数.
	 *
	 * @return the 总记录数
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * Sets the 总记录数.
	 *
	 * @param total the new 总记录数
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Gets the 列表数据.
	 *
	 * @return the 列表数据
	 */
	public List<?> getRows() {
		return rows;
	}

	/**
	 * Sets the 列表数据.
	 *
	 * @param rows the new 列表数据
	 */
	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	/**
	 * Gets the 消息状态码.
	 *
	 * @return the 消息状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the 消息状态码.
	 *
	 * @param code the new 消息状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Gets the 消息内容.
	 *
	 * @return the 消息内容
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets the 消息内容.
	 *
	 * @param msg the new 消息内容
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<>();
		}
		return params;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the params
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Adds the param.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public void addParam(String key, Object value) {
		getParams().put(key, value);
	}
}