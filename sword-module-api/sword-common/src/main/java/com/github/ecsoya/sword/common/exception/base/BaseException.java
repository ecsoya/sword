package com.github.ecsoya.sword.common.exception.base;

import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class BaseException.
 */
public class BaseException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The module. */
	private final String module;

	/** The code. */
	private final String code;

	/** The args. */
	private final Object[] args;

	/** The default message. */
	private final String defaultMessage;

	/**
	 * Instantiates a new base exception.
	 *
	 * @param module         所属模块.
	 * @param code           错误码.
	 * @param args           错误码对应的参数.
	 * @param defaultMessage 错误消息.
	 */
	public BaseException(String module, String code, Object[] args, String defaultMessage) {
		this.module = module;
		this.code = code;
		this.args = args;
		this.defaultMessage = defaultMessage;
	}

	/**
	 * Instantiates a new base exception.
	 *
	 * @param module 所属模块.
	 * @param code   错误码.
	 * @param args   错误码对应的参数.
	 */
	public BaseException(String module, String code, Object[] args) {
		this(module, code, args, null);
	}

	/**
	 * Instantiates a new base exception.
	 *
	 * @param module         所属模块.
	 * @param defaultMessage 错误消息.
	 */
	public BaseException(String module, String defaultMessage) {
		this(module, null, null, defaultMessage);
	}

	/**
	 * Instantiates a new base exception.
	 *
	 * @param code 错误码.
	 * @param args 错误码对应的参数.
	 */
	public BaseException(String code, Object[] args) {
		this(null, code, args, null);
	}

	/**
	 * Instantiates a new base exception.
	 *
	 * @param defaultMessage 错误消息.
	 */
	public BaseException(String defaultMessage) {
		this(null, null, null, defaultMessage);
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	@Override
	public String getMessage() {
		String message = null;
		if (!StringUtils.isEmpty(code)) {
			message = MessageUtils.message(code, args);
		}
		if (message == null) {
			message = defaultMessage;
		}
		return message;
	}

	/**
	 * Gets the 所属模块.
	 *
	 * @return the 所属模块
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Gets the 错误码.
	 *
	 * @return the 错误码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets the 错误码对应的参数.
	 *
	 * @return the 错误码对应的参数
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * Gets the 错误消息.
	 *
	 * @return the 错误消息
	 */
	public String getDefaultMessage() {
		return defaultMessage;
	}
}
