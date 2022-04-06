package com.github.ecsoya.sword.common.enums;

/**
 * The Enum OnlineStatus.
 */
public enum OnlineStatus {

	/** The on line. */
	on_line("在线"),

	/** The off line. */
	off_line("离线");

	/** The info. */
	private final String info;

	/**
	 * Instantiates a new online status.
	 *
	 * @param info the info
	 */
	private OnlineStatus(String info) {
		this.info = info;
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
