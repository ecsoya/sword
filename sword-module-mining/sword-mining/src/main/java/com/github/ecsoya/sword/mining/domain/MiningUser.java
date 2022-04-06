package com.github.ecsoya.sword.mining.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.wallet.domain.UserWalletAccount;

/**
 * The Class MiningUser.
 */
public class MiningUser {

	/** The parent id. */
	private Long parentId;

	/** The user id. */
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The email. */
	private String email;

	/** The mobile. */
	private String mobile;

	/** The status. */
	private Integer status;

	/** The create time. */
	@JsonFormat(pattern = "yyyy/MM/dd hh:mm:ss")
	private Date createTime;

	/** The level id. */
	private Long levelId;

	/** The level name. */
	private String levelName;

	/** The referral id. */
	private Long referralId;

	/** The referral name. */
	private String referralName;

	/** The referral email. */
	private String referralEmail;

	/** The referral mobile. */
	private String referralMobile;

	/** The accounts. */
	private List<UserWalletAccount> accounts;

	/** The child count. */
	private Integer childCount;

	/** The params. */
	private Map<String, Object> params;

	/**
	 * Gets the parent id.
	 *
	 * @return the parent id
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * Sets the parent id.
	 *
	 * @param parentId the new parent id
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the creates the time.
	 *
	 * @return the creates the time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * Sets the creates the time.
	 *
	 * @param createTime the new creates the time
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Gets the level id.
	 *
	 * @return the level id
	 */
	public Long getLevelId() {
		return levelId;
	}

	/**
	 * Sets the level id.
	 *
	 * @param levelId the new level id
	 */
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	/**
	 * Gets the level name.
	 *
	 * @return the level name
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * Sets the level name.
	 *
	 * @param levelName the new level name
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
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
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	public List<UserWalletAccount> getAccounts() {
		return accounts;
	}

	/**
	 * Sets the accounts.
	 *
	 * @param accounts the new accounts
	 */
	public void setAccounts(List<UserWalletAccount> accounts) {
		this.accounts = accounts;
	}

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	public Integer getChildCount() {
		return childCount;
	}

	/**
	 * Sets the child count.
	 *
	 * @param childCount the new child count
	 */
	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/**
	 * Sets the params.
	 *
	 * @param params the params
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Adds the params.
	 *
	 * @param key   the key
	 * @param value the value
	 */
	public void addParams(String key, Object value) {
		if (params == null) {
			params = new HashMap<>();
		}
		params.put(key, value);
	}

}
