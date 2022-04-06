package com.github.ecsoya.sword.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class UserReferrer.
 */
@JsonIgnoreProperties(allowSetters = true, ignoreUnknown = true, value = { "referralId", "referralCode", "createTime",
		"updateTime", "params", "createBy", "updateBy", "searchValue", "remark", "btree" })
public class UserReferrer extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant LEFT. */
	public static final String LEFT = "L";

	/** The Constant RIGHT. */
	public static final String RIGHT = "R";

	/** The Constant BTREE_UNFINISHED. */
	public static final Integer BTREE_UNFINISHED = 0;

	/** The Constant ENABLED. */
	public static final Integer ENABLED = 0;

	/** The Constant DISABLED. */
	public static final Integer DISABLED = 1;

	/** The user id. */
	private Long userId;

	/** The referral id. */
	@Excel(name = "推荐人ID")
	private Long referralId;

	/** The referral code. */
	@Excel(name = "推荐码")
	private String referralCode;

	/** The left code. */
	@Excel(name = "左推荐码")
	private String leftCode;

	/** The left code url. */
	@Excel(name = "左推荐码URL")
	private String leftCodeUrl;

	/** The left code enabled. */
	@Excel(name = "左推荐码可用")
	private Integer leftCodeEnabled;

	/** The btree. */
	@Excel(name = "B数")
	private Integer btree;

	/** The right code. */
	@Excel(name = "右推荐码")
	private String rightCode;

	/** The right code url. */
	@Excel(name = "右推荐码URL")
	private String rightCodeUrl;

	/** The right code enabled. */
	@Excel(name = "右推荐码可用")
	private Integer rightCodeEnabled;

	/** The base url. */
	@Excel(name = "URL")
	private String baseUrl;

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
	 * Sets the 推荐人ID.
	 *
	 * @param referralId the new 推荐人ID
	 */
	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	/**
	 * Gets the 推荐人ID.
	 *
	 * @return the 推荐人ID
	 */
	public Long getReferralId() {
		return referralId;
	}

	/**
	 * Sets the 推荐码.
	 *
	 * @param referralCode the new 推荐码
	 */
	public void setReferralCode(String referralCode) {
		this.referralCode = referralCode;
	}

	/**
	 * Gets the 推荐码.
	 *
	 * @return the 推荐码
	 */
	public String getReferralCode() {
		return referralCode;
	}

	/**
	 * Sets the 左推荐码.
	 *
	 * @param leftCode the new 左推荐码
	 */
	public void setLeftCode(String leftCode) {
		this.leftCode = leftCode;
	}

	/**
	 * Gets the 左推荐码.
	 *
	 * @return the 左推荐码
	 */
	public String getLeftCode() {
		return leftCode;
	}

	/**
	 * Sets the 左推荐码URL.
	 *
	 * @param leftCodeUrl the new 左推荐码URL
	 */
	public void setLeftCodeUrl(String leftCodeUrl) {
		this.leftCodeUrl = leftCodeUrl;
	}

	/**
	 * Gets the 左推荐码URL.
	 *
	 * @return the 左推荐码URL
	 */
	public String getLeftCodeUrl() {
		return leftCodeUrl;
	}

	/**
	 * Sets the 左推荐码可用.
	 *
	 * @param leftCodeEnabled the new 左推荐码可用
	 */
	public void setLeftCodeEnabled(Integer leftCodeEnabled) {
		this.leftCodeEnabled = leftCodeEnabled;
	}

	/**
	 * Gets the 左推荐码可用.
	 *
	 * @return the 左推荐码可用
	 */
	public Integer getLeftCodeEnabled() {
		return leftCodeEnabled;
	}

	/**
	 * Sets the b数.
	 *
	 * @param btree the new b数
	 */
	public void setBtree(Integer btree) {
		this.btree = btree;
	}

	/**
	 * Gets the b数.
	 *
	 * @return the b数
	 */
	public Integer getBtree() {
		return btree;
	}

	/**
	 * Sets the 右推荐码.
	 *
	 * @param rightCode the new 右推荐码
	 */
	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	/**
	 * Gets the 右推荐码.
	 *
	 * @return the 右推荐码
	 */
	public String getRightCode() {
		return rightCode;
	}

	/**
	 * Sets the 右推荐码URL.
	 *
	 * @param rightCodeUrl the new 右推荐码URL
	 */
	public void setRightCodeUrl(String rightCodeUrl) {
		this.rightCodeUrl = rightCodeUrl;
	}

	/**
	 * Gets the 右推荐码URL.
	 *
	 * @return the 右推荐码URL
	 */
	public String getRightCodeUrl() {
		return rightCodeUrl;
	}

	/**
	 * Sets the 右推荐码可用.
	 *
	 * @param rightCodeEnabled the new 右推荐码可用
	 */
	public void setRightCodeEnabled(Integer rightCodeEnabled) {
		this.rightCodeEnabled = rightCodeEnabled;
	}

	/**
	 * Gets the 右推荐码可用.
	 *
	 * @return the 右推荐码可用
	 */
	public Integer getRightCodeEnabled() {
		return rightCodeEnabled;
	}

	/**
	 * Sets the uRL.
	 *
	 * @param baseUrl the new uRL
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Gets the uRL.
	 *
	 * @return the uRL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
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
	 * Checks if is in left zone.
	 *
	 * @return true, if is in left zone
	 */
	@JsonIgnore
	public boolean isInLeftZone() {
		return isLeftCode(referralCode);
	}

	/**
	 * Generate left code.
	 *
	 * @return the string
	 */
	public String generateLeftCode() {
		final String leftCode = UserReferrer.LEFT + StringUtils.randomStr(7);
		setLeftCode(leftCode);
		return leftCode;
	}

	/**
	 * Generate right code.
	 *
	 * @return the string
	 */
	public String generateRightCode() {
		final String rightCode = UserReferrer.RIGHT + StringUtils.randomStr(7);
		setRightCode(rightCode);
		return rightCode;
	}

	/**
	 * Checks if is right code.
	 *
	 * @param referrerCode the referrer code
	 * @return true, if is right code
	 */
	public static boolean isRightCode(String referrerCode) {
		return StringUtils.isNotEmpty(referrerCode) && referrerCode.startsWith(RIGHT);
	}

	/**
	 * Checks if is left code.
	 *
	 * @param referrerCode the referrer code
	 * @return true, if is left code
	 */
	public static boolean isLeftCode(String referrerCode) {
		return StringUtils.isNotEmpty(referrerCode) && referrerCode.startsWith(LEFT);
	}

}
