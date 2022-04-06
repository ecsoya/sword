package com.github.ecsoya.sword.user.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserLevel.
 */
public class UserLevel extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_NORMAL. */
	public static final Integer TYPE_NORMAL = 0; // 系统升级

	/** The Constant TYPE_ADMIN. */
	public static final Integer TYPE_ADMIN = 1; // 后台设置

	/** The Constant STATUS_SUCCESS. */
	public static final Integer STATUS_SUCCESS = 0; // 考核成功

	/** The Constant STATUS_FAILURE. */
	public static final Integer STATUS_FAILURE = 1; // 考核失败

	/** The user id. */
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The email. */
	private String email;

	/** The mobile. */
	private String mobile;

	/** The level id. */
	@Excel(name = "董事级别")
	private Long levelId;

	/** The type. */
	@Excel(name = "类型 0-考核 1-后台设置")
	private Integer type;

	/** The status. */
	@Excel(name = "状态")
	private Integer status;

	/** The verify time. */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "考核时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date verifyTime;

	/**
	 * Sets the 用户Id.
	 *
	 * @param userId the new 用户Id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户Id.
	 *
	 * @return the 用户Id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 董事级别.
	 *
	 * @param levelId the new 董事级别
	 */
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	/**
	 * Gets the 董事级别.
	 *
	 * @return the 董事级别
	 */
	public Long getLevelId() {
		return levelId;
	}

	/**
	 * Sets the 类型 0-考核 1-后台设置.
	 *
	 * @param type the new 类型 0-考核 1-后台设置
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型 0-考核 1-后台设置.
	 *
	 * @return the 类型 0-考核 1-后台设置
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the 状态.
	 *
	 * @param status the new 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态.
	 *
	 * @return the 状态
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the 考核时间.
	 *
	 * @param verifyTime the new 考核时间
	 */
	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	/**
	 * Gets the 考核时间.
	 *
	 * @return the 考核时间
	 */
	public Date getVerifyTime() {
		return verifyTime;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("levelId", getLevelId()).append("type", getType()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("verifyTime", getVerifyTime()).append("remark", getRemark()).toString();
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the mobile.
	 *
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * Sets the mobile.
	 *
	 * @param mobile the new mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
