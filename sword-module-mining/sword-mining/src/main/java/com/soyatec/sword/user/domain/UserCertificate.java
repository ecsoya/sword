package com.soyatec.sword.user.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 用户实名对象 t_user_certificate
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-23
 */
public class UserCertificate extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/* 0-关闭，1-钱包，2-提币，3=钱包和提币，4=购买，5=钱包和购买，6=提币和购买，7=钱包、提币和购买 */
	public static final Integer KIND_WALLET = 1; // 1 - 钱包账户
	public static final Integer KIND_WITHDRAWAL = KIND_WALLET << 1; // 2 - 提币
	public static final Integer KIND_BUY_FILECOIN = KIND_WITHDRAWAL << 1; // 3 - 购买矿机

	public static final Integer STATUS_NONE = 0; // 提交
	public static final Integer STATUS_FAILURE = 1; // 失败
	public static final Integer STATUS_SUCCESS = 2; // 通过

	public static final Integer TYPE_CN_ID_CARD = 0;// 身份证
	public static final Integer TYPE_PASSPORT = 1;// 护照

	/** 用户ID */
	private Long userId;

	private String loginName;

	private String mobile;

	private String email;

	private Integer type;

	private Country country;

	/** 实名 */
	@Excel(name = "实名")
	private String realName;

	/** 号码 */
	@Excel(name = "号码")
	private String identityNumber;

	/** 正面照片 */
	@Excel(name = "正面照片")
	private String frontImageUrl;

	/** 背面照片 */
	@Excel(name = "背面照片")
	private String backImageUrl;

	/** 手持照片 */
	@Excel(name = "手持照片")
	private String holdingImageUrl;

	/** 状态 */
	@Excel(name = "状态")
	private Integer status;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return realName;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setFrontImageUrl(String frontImageUrl) {
		this.frontImageUrl = frontImageUrl;
	}

	public String getFrontImageUrl() {
		return frontImageUrl;
	}

	public void setBackImageUrl(String backImageUrl) {
		this.backImageUrl = backImageUrl;
	}

	public String getBackImageUrl() {
		return backImageUrl;
	}

	public void setHoldingImageUrl(String holdingImageUrl) {
		this.holdingImageUrl = holdingImageUrl;
	}

	public String getHoldingImageUrl() {
		return holdingImageUrl;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("realName", getRealName()).append("identityNumber", getIdentityNumber())
				.append("frontImageUrl", getFrontImageUrl()).append("backImageUrl", getBackImageUrl())
				.append("holdingImageUrl", getHoldingImageUrl()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public static boolean checkKind(int ucKind, int kind) {
		return (ucKind & kind) == kind;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
