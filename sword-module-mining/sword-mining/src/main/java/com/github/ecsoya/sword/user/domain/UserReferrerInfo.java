package com.github.ecsoya.sword.user.domain;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

public class UserReferrerInfo extends BaseEntity {

	/**
	 *
	 */
	private static final long serialVersionUID = 1611746763866825941L;

	private Long userId;

	private String loginName;

	private String mobile;

	private String email;

	private Integer referralCount;

	private Long referralId;

	private String referralMobile;

	private String referralEmail;

	private String referralName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Integer getReferralCount() {
		return referralCount;
	}

	public void setReferralCount(Integer referralCount) {
		this.referralCount = referralCount;
	}

	public Long getReferralId() {
		return referralId;
	}

	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	public String getReferralMobile() {
		return referralMobile;
	}

	public void setReferralMobile(String referralMobile) {
		this.referralMobile = referralMobile;
	}

	public String getReferralEmail() {
		return referralEmail;
	}

	public void setReferralEmail(String referralEmail) {
		this.referralEmail = referralEmail;
	}

	public String getReferralName() {
		return referralName;
	}

	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}

}
