package com.github.ecsoya.sword.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.common.enums.OnlineStatus;

/**
 * The Class SysUserOnline.
 */
public class SysUserOnline extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The session id. */
	private String sessionId;

	/** The dept name. */
	private String deptName;

	/** The login name. */
	private String loginName;

	/** The ipaddr. */
	private String ipaddr;

	/** The login location. */
	private String loginLocation;

	/** The browser. */
	private String browser;

	/** The os. */
	private String os;

	/** The start timestamp. */
	private Date startTimestamp;

	/** The last access time. */
	private Date lastAccessTime;

	/** The expire time. */
	private Long expireTime;

	/** The status. */
	private OnlineStatus status = OnlineStatus.on_line;

	/**
	 * Gets the 用户会话id.
	 *
	 * @return the 用户会话id
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Sets the 用户会话id.
	 *
	 * @param sessionId the new 用户会话id
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Gets the 部门名称.
	 *
	 * @return the 部门名称
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the 部门名称.
	 *
	 * @param deptName the new 部门名称
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the 登录名称.
	 *
	 * @return the 登录名称
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the 登录名称.
	 *
	 * @param loginName the new 登录名称
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Gets the 登录IP地址.
	 *
	 * @return the 登录IP地址
	 */
	public String getIpaddr() {
		return ipaddr;
	}

	/**
	 * Sets the 登录IP地址.
	 *
	 * @param ipaddr the new 登录IP地址
	 */
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	/**
	 * Gets the 登录地址.
	 *
	 * @return the 登录地址
	 */
	public String getLoginLocation() {
		return loginLocation;
	}

	/**
	 * Sets the 登录地址.
	 *
	 * @param loginLocation the new 登录地址
	 */
	public void setLoginLocation(String loginLocation) {
		this.loginLocation = loginLocation;
	}

	/**
	 * Gets the 浏览器类型.
	 *
	 * @return the 浏览器类型
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * Sets the 浏览器类型.
	 *
	 * @param browser the new 浏览器类型
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * Gets the 操作系统.
	 *
	 * @return the 操作系统
	 */
	public String getOs() {
		return os;
	}

	/**
	 * Sets the 操作系统.
	 *
	 * @param os the new 操作系统
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * Gets the session创建时间.
	 *
	 * @return the session创建时间
	 */
	public Date getStartTimestamp() {
		return startTimestamp;
	}

	/**
	 * Sets the session创建时间.
	 *
	 * @param startTimestamp the new session创建时间
	 */
	public void setStartTimestamp(Date startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	/**
	 * Gets the session最后访问时间.
	 *
	 * @return the session最后访问时间
	 */
	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	/**
	 * Sets the session最后访问时间.
	 *
	 * @param lastAccessTime the new session最后访问时间
	 */
	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	/**
	 * Gets the 超时时间，单位为分钟.
	 *
	 * @return the 超时时间，单位为分钟
	 */
	public Long getExpireTime() {
		return expireTime;
	}

	/**
	 * Sets the 超时时间，单位为分钟.
	 *
	 * @param expireTime the new 超时时间，单位为分钟
	 */
	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * Gets the 在线状态.
	 *
	 * @return the 在线状态
	 */
	public OnlineStatus getStatus() {
		return status;
	}

	/**
	 * Sets the 在线状态.
	 *
	 * @param status the new 在线状态
	 */
	public void setStatus(OnlineStatus status) {
		this.status = status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("sessionId", getSessionId())
				.append("loginName", getLoginName()).append("deptName", getDeptName()).append("ipaddr", getIpaddr())
				.append("loginLocation", getLoginLocation()).append("browser", getBrowser()).append("os", getOs())
				.append("status", getStatus()).append("startTimestamp", getStartTimestamp())
				.append("lastAccessTime", getLastAccessTime()).append("expireTime", getExpireTime()).toString();
	}
}
