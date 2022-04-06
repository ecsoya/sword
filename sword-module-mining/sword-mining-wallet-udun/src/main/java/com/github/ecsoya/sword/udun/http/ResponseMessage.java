package com.github.ecsoya.sword.udun.http;

/**
 * The Class ResponseMessage.
 *
 * @param <T> the generic type
 */
public class ResponseMessage<T> {

	/** The success code. */
	public static int SUCCESS_CODE = 200;

	/** The success message. */
	public static String SUCCESS_MESSAGE = "SUCCESS";

	/** The error code. */
	public static int ERROR_CODE = 500;

	/** The error message. */
	public static String ERROR_MESSAGE = "ERROR";

	/** The code. */
	private int code;

	/** The message. */
	private String message;

	/** The data. */
	private T data;

	/**
	 * Instantiates a new response message.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public ResponseMessage(int status, String message) {
		this.code = status;
		this.message = message;
	}

	/**
	 * Instantiates a new response message.
	 *
	 * @param message the message
	 */
	public ResponseMessage(String message) {
		this.message = message;
	}

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
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
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
	 * Checks if is success.
	 *
	 * @return true, if is success
	 */
	public boolean isSuccess() {
		return SUCCESS_CODE == code;
	}

	/**
	 * Success.
	 *
	 * @param <T>     the generic type
	 * @param status  the status
	 * @param message the message
	 * @return the response message
	 */
	public static <T> ResponseMessage<T> success(int status, String message) {
		return new ResponseMessage<T>(status, message);
	}

	/**
	 * Error.
	 *
	 * @param <T>     the generic type
	 * @param status  the status
	 * @param message the message
	 * @return the response message
	 */
	public static <T> ResponseMessage<T> error(int status, String message) {
		return new ResponseMessage<>(status, message);
	}

	/**
	 * Success.
	 *
	 * @param <T>     the generic type
	 * @param message the message
	 * @return the response message
	 */
	public static <T> ResponseMessage<T> success(String message) {
		return new ResponseMessage<>(SUCCESS_CODE, message);
	}

	/**
	 * Error.
	 *
	 * @param <T>     the generic type
	 * @param message the message
	 * @return the response message
	 */
	public static <T> ResponseMessage<T> error(String message) {
		return new ResponseMessage<>(ERROR_CODE, message);
	}

	/**
	 * Error.
	 *
	 * @param <T> the generic type
	 * @return the response message
	 */
	public static <T> ResponseMessage<T> error() {
		return new ResponseMessage<>(ERROR_CODE, ERROR_MESSAGE);
	}

	/**
	 * Success.
	 *
	 * @param <T> the generic type
	 * @return the response message
	 */
	public static <T> ResponseMessage<T> success() {
		return new ResponseMessage<>(SUCCESS_CODE, SUCCESS_MESSAGE);
	}
}
