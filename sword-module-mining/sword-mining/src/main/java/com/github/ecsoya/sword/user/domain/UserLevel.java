package com.github.ecsoya.sword.user.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * 用户级别对象 t_user_level
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserLevel extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer TYPE_NORMAL = 0; // 系统升级
	public static final Integer TYPE_ADMIN = 1; // 后台设置

	public static final Integer STATUS_SUCCESS = 0; // 考核成功
	public static final Integer STATUS_FAILURE = 1; // 考核失败

	/** 用户Id */
	private Long userId;

	private String loginName;

	private String email;

	private String mobile;

	/** 董事级别 */
	@Excel(name = "董事级别")
	private Long levelId;

	/** 类型 0-考核 1-后台设置 */
	@Excel(name = "类型 0-考核 1-后台设置")
	private Integer type;

	/** 状态 */
	@Excel(name = "状态")
	private Integer status;

	/** 考核时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "考核时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date verifyTime;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setVerifyTime(Date verifyTime) {
		this.verifyTime = verifyTime;
	}

	public Date getVerifyTime() {
		return verifyTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("levelId", getLevelId()).append("type", getType()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("verifyTime", getVerifyTime()).append("remark", getRemark()).toString();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
