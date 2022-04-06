package com.github.ecsoya.sword.framework.shiro.session;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.session.mgt.SimpleSession;

import com.github.ecsoya.sword.common.enums.OnlineStatus;

/**
 * The Class OnlineSession.
 */
public class OnlineSession extends SimpleSession {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The dept name. */
	private String deptName;

	/** The avatar. */
	private String avatar;

	/** The host. */
	private String host;

	/** The browser. */
	private String browser;

	/** The os. */
	private String os;

	/** The status. */
	private OnlineStatus status = OnlineStatus.on_line;

	/** The attribute changed. */
	private transient boolean attributeChanged = false;

	/**
	 * Gets the host.
	 *
	 * @return the host
	 */
	@Override
	public String getHost() {
		return host;
	}

	/**
	 * Sets the host.
	 *
	 * @param host the new host
	 */
	@Override
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the browser.
	 *
	 * @return the browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * Sets the browser.
	 *
	 * @param browser the new browser
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	public String getOs() {
		return os;
	}

	/**
	 * Sets the os.
	 *
	 * @param os the new os
	 */
	public void setOs(String os) {
		this.os = os;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the login name.
	 *
	 * @return the login name
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the login name.
	 *
	 * @param loginName the new login name
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Gets the dept name.
	 *
	 * @return the dept name
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the dept name.
	 *
	 * @param deptName the new dept name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public OnlineStatus getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(OnlineStatus status) {
		this.status = status;
	}

	/**
	 * Mark attribute changed.
	 */
	public void markAttributeChanged() {
		this.attributeChanged = true;
	}

	/**
	 * Reset attribute changed.
	 */
	public void resetAttributeChanged() {
		this.attributeChanged = false;
	}

	/**
	 * Checks if is attribute changed.
	 *
	 * @return the attribute changed
	 */
	public boolean isAttributeChanged() {
		return attributeChanged;
	}

	/**
	 * Gets the avatar.
	 *
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * Sets the avatar.
	 *
	 * @param avatar the new avatar
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * Sets the attribute.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	@Override
	public void setAttribute(Object key, Object value) {
		super.setAttribute(key, value);
	}

	/**
	 * Removes the attribute.
	 *
	 * @param key the key
	 * @return the object
	 */
	@Override
	public Object removeAttribute(Object key) {
		return super.removeAttribute(key);
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("loginName", getLoginName()).append("deptName", getDeptName()).append("avatar", getAvatar())
				.append("host", getHost()).append("browser", getBrowser()).append("os", getOs())
				.append("status", getStatus()).append("attributeChanged", isAttributeChanged()).toString();
	}
}
