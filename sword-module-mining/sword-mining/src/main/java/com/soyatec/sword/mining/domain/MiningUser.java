package com.soyatec.sword.mining.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soyatec.sword.wallet.domain.UserWalletAccount;

public class MiningUser {

	private Long parentId;

	private Long userId;

	private String loginName;

	private String email;

	private String mobile;

	private Integer status;

	private Date createTime;

	private Long levelId;

	private String levelName;

	private Long referralId;

	private String referralName;

	private String referralEmail;

	private String referralMobile;

	private List<UserWalletAccount> accounts;

	private Integer childCount;

	private Map<String, Object> params;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public Long getReferralId() {
		return referralId;
	}

	public void setReferralId(Long referralId) {
		this.referralId = referralId;
	}

	public String getReferralName() {
		return referralName;
	}

	public void setReferralName(String referralName) {
		this.referralName = referralName;
	}

	public String getReferralEmail() {
		return referralEmail;
	}

	public void setReferralEmail(String referralEmail) {
		this.referralEmail = referralEmail;
	}

	public String getReferralMobile() {
		return referralMobile;
	}

	public void setReferralMobile(String referralMobile) {
		this.referralMobile = referralMobile;
	}

	public List<UserWalletAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<UserWalletAccount> accounts) {
		this.accounts = accounts;
	}

	public Integer getChildCount() {
		return childCount;
	}

	public void setChildCount(Integer childCount) {
		this.childCount = childCount;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void addParams(String key, Object value) {
		if (params == null) {
			params = new HashMap<>();
		}
		params.put(key, value);
	}

}
