package com.github.ecsoya.sword.wallet.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserWalletAddress.
 */
public class UserWalletAddress extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The user id. */
	@Excel(name = "用户ID")
	private Long userId;

	/** The name. */
	@Excel(name = "名称")
	private String name;

	/** The address. */
	@Excel(name = "地址")
	private String address;

	/** The symbol. */
	@Excel(name = "币种")
	private String symbol;

	/** The type. */
	@Excel(name = "类型 (0 - 提现 1- 转账)")
	private Integer type;

	/** The qrcode url. */
	private String qrcodeUrl;

	/**
	 * Sets the iD.
	 *
	 * @param id the new iD
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public Long getId() {
		return id;
	}

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
	 * Sets the 地址.
	 *
	 * @param address the new 地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the 地址.
	 *
	 * @return the 地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the 币种.
	 *
	 * @param symbol the new 币种
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the 币种.
	 *
	 * @return the 币种
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the 类型 (0 - 提现 1- 转账).
	 *
	 * @param type the new 类型 (0 - 提现 1- 转账)
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型 (0 - 提现 1- 转账).
	 *
	 * @return the 类型 (0 - 提现 1- 转账)
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("name", getName()).append("address", getAddress())
				.append("symbol", getSymbol()).append("type", getType()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	/**
	 * Gets the qrcode url.
	 *
	 * @return the qrcode url
	 */
	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	/**
	 * Sets the qrcode url.
	 *
	 * @param qrcodeUrl the new qrcode url
	 */
	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
}
