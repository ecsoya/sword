package com.github.ecsoya.sword.common.enums;

/**
 * The Enum UserStatus.
 */
public enum UserStatus {

	/** The ok. */
	OK("0", "正常"),

	/** The disable. */
	DISABLE("1", "停用"),

	/** The deleted. */
	DELETED("2", "删除");

	/** The code. */
	private final String code;

	/** The info. */
	private final String info;

	/**
	 * Instantiates a new user status.
	 *
	 * @param code the code
	 * @param info the info
	 */
	UserStatus(String code, String info) {
		this.code = code;
		this.info = info;
	}

	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Gets the info.
	 *
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}
}
