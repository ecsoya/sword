package com.github.ecsoya.sword.udun.http;

public class ResponseMessage<T> {

	public static int SUCCESS_CODE = 200;
	public static String SUCCESS_MESSAGE = "SUCCESS";
	public static int ERROR_CODE = 500;
	public static String ERROR_MESSAGE = "ERROR";

	private int code;

	private String message;

	private T data;

	public ResponseMessage(int status, String message) {
		this.code = status;
		this.message = message;
	}

	public ResponseMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return SUCCESS_CODE == code;
	}

	public static <T> ResponseMessage<T> success(int status, String message) {
		return new ResponseMessage<T>(status, message);
	}

	public static <T> ResponseMessage<T> error(int status, String message) {
		return new ResponseMessage<>(status, message);
	}

	public static <T> ResponseMessage<T> success(String message) {
		return new ResponseMessage<>(SUCCESS_CODE, message);
	}

	public static <T> ResponseMessage<T> error(String message) {
		return new ResponseMessage<>(ERROR_CODE, message);
	}

	public static <T> ResponseMessage<T> error() {
		return new ResponseMessage<>(ERROR_CODE, ERROR_MESSAGE);
	}

	public static <T> ResponseMessage<T> success() {
		return new ResponseMessage<>(SUCCESS_CODE, SUCCESS_MESSAGE);
	}
}
