package com.github.ecsoya.sword.user.domain;

import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserReferrerInfo.
 */
public class UserReferrerInfo extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1611746763866825941L;

	/** The user id. */
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The mobile. */
	private String mobile;

	/** The email. */
	private String email;

	/** The referral count. */
	private Integer referralCount;

	/** The referral id. */
	private Long referralId;

	/** The referral mobile. */
	private String referralMobile;

	/** The referral email. */
	private String referralEmail;

	/** The referral name. */
	private String referralName;

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
	 * Gets the referral count.
	 *
	 * @return the referral count
	 */
	public Integer getReferralCount() {
		return referralCount;
	}

	/**
	 * Sets the referral count.
	 *
	 * @param referralCount the new referral count
	 */
	public void setReferralCount(Integer referralCount) {
		this.referralCount = referralCount;
	}

	/**
	 * Gets the referral id.
	 *
	 * @return the referral id
	 */
	public Long getReferralId() {
		return referralId;
	}

	/**
	 * Sets the referral id.
	 *
	 * @param referralId the new referral id
	 */
	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	/**
	 * Gets the referral mobile.
	 *
	 * @return the referral mobile
	 */
	public String getReferralMobile() {
		return referralMobile;
	}

	/**
	 * Sets the referral mobile.
	 *
	 * @param referralMobile the new referral mobile
	 */
	public void setReferralMobile(String referralMobile) {
		this.referralMobile = referralMobile;
	}

	/**
	 * Gets the referral email.
	 *
	 * @return the referral email
	 */
	public String getReferralEmail() {
		return referralEmail;
	}

	/**
	 * Sets the referral email.
	 *
	 * @param referralEmail the new referral email
	 */
	public void setReferralEmail(String referralEmail) {
		this.referralEmail = referralEmail;
	}

	/**
	 * Gets the referral name.
	 *
	 * @return the referral name
	 */
	public String getReferralName() {
		return referralName;
	}

	/**
	 * Sets the referral name.
	 *
	 * @param referralName the new referral name
	 */
	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}

}
