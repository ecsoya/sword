package com.github.ecsoya.sword.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * 用户直推对象 t_user_referrer
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@JsonIgnoreProperties(allowSetters = true, ignoreUnknown = true, value = { "referralId", "referralCode", "createTime",
		"updateTime", "params", "createBy", "updateBy", "searchValue", "remark", "btree" })
public class UserReferrer extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final String LEFT = "L";
	public static final String RIGHT = "R";
	public static final Integer BTREE_UNFINISHED = 0;

	public static final Integer ENABLED = 0;
	public static final Integer DISABLED = 1;

	/** 用户Id */
	private Long userId;

	/** 推荐人ID */
	@Excel(name = "推荐人ID")
	private Long referralId;

	/** 推荐码 */
	@Excel(name = "推荐码")
	private String referralCode;

	/** 左推荐码 */
	@Excel(name = "左推荐码")
	private String leftCode;

	/** 左推荐码URL */
	@Excel(name = "左推荐码URL")
	private String leftCodeUrl;

	/** 左推荐码可用 */
	@Excel(name = "左推荐码可用")
	private Integer leftCodeEnabled;

	/** B数 */
	@Excel(name = "B数")
	private Integer btree;

	/** 右推荐码 */
	@Excel(name = "右推荐码")
	private String rightCode;

	/** 右推荐码URL */
	@Excel(name = "右推荐码URL")
	private String rightCodeUrl;

	/** 右推荐码可用 */
	@Excel(name = "右推荐码可用")
	private Integer rightCodeEnabled;

	/** URL */
	@Excel(name = "URL")
	private String baseUrl;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	public Long getReferralId() {
		return referralId;
	}

	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	public String getReferralCode() {
		return referralCode;
	}

	public void setLeftCode(String leftCode) {
		this.leftCode = leftCode;
	}

	public String getLeftCode() {
		return leftCode;
	}

	public void setLeftCodeUrl(String leftCodeUrl) {
		this.leftCodeUrl = leftCodeUrl;
	}

	public String getLeftCodeUrl() {
		return leftCodeUrl;
	}

	public void setLeftCodeEnabled(Integer leftCodeEnabled) {
		this.leftCodeEnabled = leftCodeEnabled;
	}

	public Integer getLeftCodeEnabled() {
		return leftCodeEnabled;
	}

	public void setBtree(Integer btree) {
		this.btree = btree;
	}

	public Integer getBtree() {
		return btree;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCodeUrl(String rightCodeUrl) {
		this.rightCodeUrl = rightCodeUrl;
	}

	public String getRightCodeUrl() {
		return rightCodeUrl;
	}

	public void setRightCodeEnabled(Integer rightCodeEnabled) {
		this.rightCodeEnabled = rightCodeEnabled;
	}

	public Integer getRightCodeEnabled() {
		return rightCodeEnabled;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("referralId", getReferralId()).append("referralCode", getReferralCode())
				.append("leftCode", getLeftCode()).append("leftCodeUrl", getLeftCodeUrl())
				.append("createTime", getCreateTime()).append("leftCodeEnabled", getLeftCodeEnabled())
				.append("btree", getBtree()).append("updateTime", getUpdateTime()).append("rightCode", getRightCode())
				.append("remark", getRemark()).append("rightCodeUrl", getRightCodeUrl())
				.append("rightCodeEnabled", getRightCodeEnabled()).append("baseUrl", getBaseUrl()).toString();
	}

	/**
	 * 是否在左区
	 */
	@JsonIgnore
	public boolean isInLeftZone() {
		return isLeftCode(referralCode);
	}

	public String generateLeftCode() {
		final String leftCode = UserReferrer.LEFT + StringUtils.randomStr(7);
		setLeftCode(leftCode);
		return leftCode;
	}

	public String generateRightCode() {
		final String rightCode = UserReferrer.RIGHT + StringUtils.randomStr(7);
		setRightCode(rightCode);
		return rightCode;
	}

	public static boolean isRightCode(String referrerCode) {
		return StringUtils.isNotEmpty(referrerCode) && referrerCode.startsWith(RIGHT);
	}

	public static boolean isLeftCode(String referrerCode) {
		return StringUtils.isNotEmpty(referrerCode) && referrerCode.startsWith(LEFT);
	}

}
