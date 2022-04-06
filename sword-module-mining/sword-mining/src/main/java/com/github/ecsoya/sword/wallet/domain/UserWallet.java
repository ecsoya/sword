package com.github.ecsoya.sword.wallet.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserWallet.
 */
@JsonIgnoreProperties(allowSetters = true, ignoreUnknown = true, value = { "salt", "password", "createTime",
		"updateTime", "params", "createBy", "updateBy", "searchValue", "remark" })
public class UserWallet extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	private Long userId;

	/** The name. */
	@Excel(name = "名称")
	private String name;

	/** The mnemonic. */
	@Excel(name = "助记ID")
	private String mnemonic;

	/** The password. */
	@Excel(name = "密码")
	private String password;

	/** The salt. */
	@Excel(name = "盐")
	private String salt;

	/** The accounts. */
	private List<UserWalletAccount> accounts;

	/**
	 * Sets the iD.
	 *
	 * @param userId the new iD
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 名称.
	 *
	 * @param name the new 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the 名称.
	 *
	 * @return the 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the 助记ID.
	 *
	 * @param mnemonic the new 助记ID
	 */
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	/**
	 * Gets the 助记ID.
	 *
	 * @return the 助记ID
	 */
	public String getMnemonic() {
		return mnemonic;
	}

	/**
	 * Sets the 密码.
	 *
	 * @param password the new 密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the 密码.
	 *
	 * @return the 密码
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the 盐.
	 *
	 * @param salt the new 盐
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Gets the 盐.
	 *
	 * @return the 盐
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("name", getName()).append("mnemonic", getMnemonic()).append("password", getPassword())
				.append("salt", getSalt()).append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
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
}
