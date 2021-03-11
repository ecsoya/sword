package com.soyatec.sword.wallet.domain;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 用户钱包对象 t_user_wallet
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
@JsonIgnoreProperties(allowSetters = true, ignoreUnknown = true, value = { "salt", "password", "createTime",
		"updateTime", "params", "createBy", "updateBy", "searchValue", "remark" })
public class UserWallet extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long userId;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 助记ID */
	@Excel(name = "助记ID")
	private String mnemonic;

	/** 密码 */
	@Excel(name = "密码")
	private String password;

	/** 盐 */
	@Excel(name = "盐")
	private String salt;

	private List<UserWalletAccount> accounts;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSalt() {
		return salt;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("name", getName()).append("mnemonic", getMnemonic()).append("password", getPassword())
				.append("salt", getSalt()).append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
	}

	public List<UserWalletAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<UserWalletAccount> accounts) {
		this.accounts = accounts;
	}
}
