package com.github.ecsoya.sword.common.core.domain;

import com.alibaba.fastjson.JSON;

public class CommonResult<T> {

	private static final int SUCCESS_CODE = 200;// 200
	private static final int FAILURE_CODE = 500;// 500

	private int code;

	private String info;

	private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> CommonResult<T> create(int code, String info, T data) {
		final CommonResult<T> r = new CommonResult<T>();
		r.setCode(code);
		r.setInfo(info);
		r.setData(data);
		return r;
	}

	public static <T> CommonResult<T> fail() {
		return fail("Failure");
	}

	public static <T> CommonResult<T> fail(String info) {
		return fail(FAILURE_CODE, info);
	}

	public static <T> CommonResult<T> fail(int code, String info) {
		return create(code, info, null);
	}

	public static <T> CommonResult<T> success(T data) {
		return create(SUCCESS_CODE, "Success", data);
	}

	public static CommonResult<?> success() {
		return create(SUCCESS_CODE, "Success", 1);
	}

	public boolean isSuccess() {
		return isSuccess(false);
	}

	public boolean isSuccess(boolean checkData) {
		if (SUCCESS_CODE != code) {
			return false;
		} else if (checkData) {
			return data != null;
		}
		return true;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public static <T> CommonResult<T> build(T data) {
		if (data == null) {
			return fail("Error");
		}
		return success(data);
	}

	public static CommonResult<?> ajax(int rows) {
		if (rows > 0) {
			return success(rows);
		}
		return fail();
	}

	public static CommonResult<?> ajax(AjaxResult ajax) {
		if (ajax.isOK()) {
			return success(ajax.getData());
		}
		return fail(ajax.getMsg());
	}

}
