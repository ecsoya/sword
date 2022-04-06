package com.github.ecsoya.sword.common.core.domain;

import com.alibaba.fastjson.JSON;

/**
 * The Class CommonResult.
 *
 * @param <T> the generic type
 */
public class CommonResult<T> {

	/** The Constant SUCCESS_CODE. */
	private static final int SUCCESS_CODE = 200;// 200

	/** The Constant FAILURE_CODE. */
	private static final int FAILURE_CODE = 500;// 500

	/** The code. */
	private int code;

	/** The info. */
	private String info;

	/** The data. */
	private T data;

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code the new code
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * Sets the info.
	 *
	 * @param info the new info
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Creates the.
	 *
	 * @param <T>  the generic type
	 * @param code the code
	 * @param info the info
	 * @param data the data
	 * @return the common result
	 */
	public static <T> CommonResult<T> create(int code, String info, T data) {
		final CommonResult<T> r = new CommonResult<T>();
		r.setCode(code);
		r.setInfo(info);
		r.setData(data);
		return r;
	}

	/**
	 * Fail.
	 *
	 * @param <T> the generic type
	 * @return the common result
	 */
	public static <T> CommonResult<T> fail() {
		return fail("Failure");
	}

	/**
	 * Fail.
	 *
	 * @param <T>  the generic type
	 * @param info the info
	 * @return the common result
	 */
	public static <T> CommonResult<T> fail(String info) {
		return fail(FAILURE_CODE, info);
	}

	/**
	 * Fail.
	 *
	 * @param <T>  the generic type
	 * @param code the code
	 * @param info the info
	 * @return the common result
	 */
	public static <T> CommonResult<T> fail(int code, String info) {
		return create(code, info, null);
	}

	/**
	 * Success.
	 *
	 * @param <T>  the generic type
	 * @param data the data
	 * @return the common result
	 */
	public static <T> CommonResult<T> success(T data) {
		return create(SUCCESS_CODE, "Success", data);
	}

	/**
	 * Success.
	 *
	 * @return the common result
	 */
	public static CommonResult<?> success() {
		return create(SUCCESS_CODE, "Success", 1);
	}

	/**
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return isSuccess(false);
	}

	/**
	 * Checks if is success.
	 *
	 * @param checkData the check data
	 * @return true, if is success
	 */
	public boolean isSuccess(boolean checkData) {
		if (SUCCESS_CODE != code) {
			return false;
		} else if (checkData) {
			return data != null;
		}
		return true;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * Builds the.
	 *
	 * @param <T>  the generic type
	 * @param data the data
	 * @return the common result
	 */
	public static <T> CommonResult<T> build(T data) {
		if (data == null) {
			return fail("Error");
		}
		return success(data);
	}

	/**
	 * Ajax.
	 *
	 * @param rows the rows
	 * @return the common result
	 */
	public static CommonResult<?> ajax(int rows) {
		if (rows > 0) {
			return success(rows);
		}
		return fail();
	}

	/**
	 * Ajax.
	 *
	 * @param ajax the ajax
	 * @return the common result
	 */
	public static CommonResult<?> ajax(AjaxResult ajax) {
		if (ajax.isOK()) {
			return success(ajax.getData());
		}
		return fail(ajax.getMsg());
	}

}
