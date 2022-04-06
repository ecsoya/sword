package com.github.ecsoya.sword.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserCertificate.
 */
public class UserCertificate extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant KIND_WALLET. */
	/* 0-关闭，1-钱包，2-提币，3=钱包和提币，4=购买，5=钱包和购买，6=提币和购买，7=钱包、提币和购买 */
	public static final Integer KIND_WALLET = 1; // 1 - 钱包账户

	/** The Constant KIND_WITHDRAWAL. */
	public static final Integer KIND_WITHDRAWAL = KIND_WALLET << 1; // 2 - 提币

	/** The Constant KIND_BUY_FILECOIN. */
	public static final Integer KIND_BUY_FILECOIN = KIND_WITHDRAWAL << 1; // 3 - 购买矿机

	/** The Constant STATUS_NONE. */
	public static final Integer STATUS_NONE = 0; // 提交

	/** The Constant STATUS_FAILURE. */
	public static final Integer STATUS_FAILURE = 1; // 失败

	/** The Constant STATUS_SUCCESS. */
	public static final Integer STATUS_SUCCESS = 2; // 通过

	/** The Constant TYPE_CN_ID_CARD. */
	public static final Integer TYPE_CN_ID_CARD = 0;// 身份证

	/** The Constant TYPE_PASSPORT. */
	public static final Integer TYPE_PASSPORT = 1;// 护照

	/** The user id. */
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The mobile. */
	private String mobile;

	/** The email. */
	private String email;

	/** The type. */
	private Integer type;

	/** The country. */
	private Country country;

	/** The real name. */
	@Excel(name = "实名")
	private String realName;

	/** The identity number. */
	@Excel(name = "号码")
	private String identityNumber;

	/** The front image url. */
	@Excel(name = "正面照片")
	private String frontImageUrl;

	/** The back image url. */
	@Excel(name = "背面照片")
	private String backImageUrl;

	/** The holding image url. */
	@Excel(name = "手持照片")
	private String holdingImageUrl;

	/** The status. */
	@Excel(name = "状态")
	private Integer status;

	/**
	 * Sets the 用户ID.
	 *
	 * @param userId the new 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户ID.
	 *
	 * @return the 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 实名.
	 *
	 * @param realName the new 实名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Gets the 实名.
	 *
	 * @return the 实名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Sets the 号码.
	 *
	 * @param identityNumber the new 号码
	 */
	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	/**
	 * Gets the 号码.
	 *
	 * @return the 号码
	 */
	public String getIdentityNumber() {
		return identityNumber;
	}

	/**
	 * Sets the 正面照片.
	 *
	 * @param frontImageUrl the new 正面照片
	 */
	public void setFrontImageUrl(String frontImageUrl) {
		this.frontImageUrl = frontImageUrl;
	}

	/**
	 * Gets the 正面照片.
	 *
	 * @return the 正面照片
	 */
	public String getFrontImageUrl() {
		return frontImageUrl;
	}

	/**
	 * Sets the 背面照片.
	 *
	 * @param backImageUrl the new 背面照片
	 */
	public void setBackImageUrl(String backImageUrl) {
		this.backImageUrl = backImageUrl;
	}

	/**
	 * Gets the 背面照片.
	 *
	 * @return the 背面照片
	 */
	public String getBackImageUrl() {
		return backImageUrl;
	}

	/**
	 * Sets the 手持照片.
	 *
	 * @param holdingImageUrl the new 手持照片
	 */
	public void setHoldingImageUrl(String holdingImageUrl) {
		this.holdingImageUrl = holdingImageUrl;
	}

	/**
	 * Gets the 手持照片.
	 *
	 * @return the 手持照片
	 */
	public String getHoldingImageUrl() {
		return holdingImageUrl;
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("realName", getRealName()).append("identityNumber", getIdentityNumber())
				.append("frontImageUrl", getFrontImageUrl()).append("backImageUrl", getBackImageUrl())
				.append("holdingImageUrl", getHoldingImageUrl()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Check kind.
	 *
	 * @param ucKind the uc kind
	 * @param kind   the kind
	 * @return true, if successful
	 */
	public static boolean checkKind(int ucKind, int kind) {
		return (ucKind & kind) == kind;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

}
