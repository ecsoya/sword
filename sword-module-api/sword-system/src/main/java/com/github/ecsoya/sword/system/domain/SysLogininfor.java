package com.github.ecsoya.sword.system.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysLogininfor.
 */
public class SysLogininfor extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The info id. */
	@Excel(name = "序号", cellType = ColumnType.NUMERIC)
	private Long infoId;

	/** The login name. */
	@Excel(name = "用户账号")
	private String loginName;

	/** The status. */
	@Excel(name = "登录状态", readConverterExp = "0=成功,1=失败")
	private String status;

	/** The ipaddr. */
	@Excel(name = "登录地址")
	private String ipaddr;

	/** The login location. */
	@Excel(name = "登录地点")
	private String loginLocation;

	/** The browser. */
	@Excel(name = "浏览器")
	private String browser;

	/** The os. */
	@Excel(name = "操作系统")
	private String os;

	/** The msg. */
	@Excel(name = "提示消息")
	private String msg;

	/** The login time. */
	@Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
	private Date loginTime;

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public Long getInfoId() {
		return infoId;
	}

	/**
	 * Sets the iD.
	 *
	 * @param infoId the new iD
	 */
	public void setInfoId(Long infoId) {
		this.infoId = infoId;
	}

	/**
	 * Gets the 用户账号.
	 *
	 * @return the 用户账号
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Sets the 用户账号.
	 *
	 * @param loginName the new 用户账号
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * Gets the 登录状态 0成功 1失败.
	 *
	 * @return the 登录状态 0成功 1失败
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 登录状态 0成功 1失败.
	 *
	 * @param status the new 登录状态 0成功 1失败
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * Gets the 登录地点.
	 *
	 * @return the 登录地点
	 */
	public String getLoginLocation() {
		return loginLocation;
	}

	/**
	 * Sets the 登录地点.
	 *
	 * @param loginLocation the new 登录地点
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
	 * Gets the 提示消息.
	 *
	 * @return the 提示消息
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Sets the 提示消息.
	 *
	 * @param msg the new 提示消息
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * Gets the 访问时间.
	 *
	 * @return the 访问时间
	 */
	public Date getLoginTime() {
		return loginTime;
	}

	/**
	 * Sets the 访问时间.
	 *
	 * @param loginTime the new 访问时间
	 */
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("infoId", getInfoId())
				.append("loginName", getLoginName()).append("ipaddr", getIpaddr())
				.append("loginLocation", getLoginLocation()).append("browser", getBrowser()).append("os", getOs())
				.append("status", getStatus()).append("msg", getMsg()).append("loginTime", getLoginTime()).toString();
	}
}