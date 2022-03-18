package com.github.ecsoya.sword.common.enums;

/**
 * 用户会话
 *
 * @author Jin Liu (angryred@qq.com)
 */
public enum OnlineStatus {
	/** 用户状态 */
	on_line("在线"), off_line("离线");

	private final String info;

	private OnlineStatus(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}