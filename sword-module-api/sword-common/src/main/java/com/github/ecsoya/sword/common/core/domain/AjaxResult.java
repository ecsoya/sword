package com.github.ecsoya.sword.common.core.domain;

import java.util.HashMap;
import java.util.Objects;

import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class AjaxResult.
 */
public class AjaxResult extends HashMap<String, Object> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant CODE_TAG. */
	public static final String CODE_TAG = "code";

	/** The Constant MSG_TAG. */
	public static final String MSG_TAG = "msg";

	/** The Constant DATA_TAG. */
	public static final String DATA_TAG = "data";

	/**
	 * The Enum Type.
	 */
	public enum Type {

		/** The success. */
		SUCCESS(0),

		/** The warn. */
		WARN(301),

		/** The error. */
		ERROR(500);

		/** The value. */
		private final int value;

		/**
		 * Instantiates a new type.
		 *
		 * @param value the value
		 */
		Type(int value) {
			this.value = value;
		}

		/**
		 * Value.
		 *
		 * @return the int
		 */
		public int value() {
			return this.value;
		}
	}

	/**
	 * Instantiates a new ajax result.
	 */
	public AjaxResult() {
	}

	/**
	 * Instantiates a new ajax result.
	 *
	 * @param type the type
	 * @param msg  the msg
	 */
	public AjaxResult(Type type, String msg) {
		super.put(CODE_TAG, type.value);
		super.put(MSG_TAG, msg);
	}

	/**
	 * Instantiates a new ajax result.
	 *
	 * @param type the type
	 * @param msg  the msg
	 * @param data the data
	 */
	public AjaxResult(Type type, String msg, Object data) {
		super.put(CODE_TAG, type.value);
		super.put(MSG_TAG, msg);
		if (StringUtils.isNotNull(data)) {
			super.put(DATA_TAG, data);
		}
	}

	/**
	 * Put.
	 *
	 * @param key   the key
	 * @param value the value
	 * @return the ajax result
	 */
	@Override
	public AjaxResult put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return (int) get(CODE_TAG);
	}

	/**
	 * Checks if is ok.
	 *
	 * @return true, if is ok
	 */
	public boolean isOK() {
		return Objects.equals(0, get(CODE_TAG));
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Object getData() {
		return get(DATA_TAG);
	}

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return (String) get(MSG_TAG);
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(Object data) {
		put(DATA_TAG, data);
	}

	/**
	 * Success.
	 *
	 * @return the ajax result
	 */
	public static AjaxResult success() {
		return AjaxResult.success("操作成功");
	}

	/**
	 * Success.
	 *
	 * @param data the data
	 * @return the ajax result
	 */
	public static AjaxResult success(Object data) {
		return AjaxResult.success("操作成功", data);
	}

	/**
	 * Success.
	 *
	 * @param msg the msg
	 * @return the ajax result
	 */
	public static AjaxResult success(String msg) {
		return AjaxResult.success(msg, null);
	}

	/**
	 * Success.
	 *
	 * @param msg  the msg
	 * @param data the data
	 * @return the ajax result
	 */
	public static AjaxResult success(String msg, Object data) {
		return new AjaxResult(Type.SUCCESS, msg, data);
	}

	/**
	 * Warn.
	 *
	 * @param msg the msg
	 * @return the ajax result
	 */
	public static AjaxResult warn(String msg) {
		return AjaxResult.warn(msg, null);
	}

	/**
	 * Warn.
	 *
	 * @param msg  the msg
	 * @param data the data
	 * @return the ajax result
	 */
	public static AjaxResult warn(String msg, Object data) {
		return new AjaxResult(Type.WARN, msg, data);
	}

	/**
	 * Error.
	 *
	 * @return the ajax result
	 */
	public static AjaxResult error() {
		return AjaxResult.error("操作失败");
	}

	/**
	 * Error.
	 *
	 * @param msg the msg
	 * @return the ajax result
	 */
	public static AjaxResult error(String msg) {
		return AjaxResult.error(msg, null);
	}

	/**
	 * Error.
	 *
	 * @param msg  the msg
	 * @param data the data
	 * @return the ajax result
	 */
	public static AjaxResult error(String msg, Object data) {
		return new AjaxResult(Type.ERROR, msg, data);
	}
}
