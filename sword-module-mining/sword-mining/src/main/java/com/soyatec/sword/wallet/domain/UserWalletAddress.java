package com.soyatec.sword.wallet.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 用户钱包地址对象 t_user_wallet_address
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserWalletAddress extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	/** 名称 */
	@Excel(name = "名称")
	private String name;

	/** 地址 */
	@Excel(name = "地址")
	private String address;

	/** 币种 */
	@Excel(name = "币种")
	private String symbol;

	/** 类型 (0 - 提现 1- 转账) */
	@Excel(name = "类型 (0 - 提现 1- 转账)")
	private Integer type;

	private String qrcodeUrl;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

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

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("name", getName()).append("address", getAddress())
				.append("symbol", getSymbol()).append("type", getType()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}
}
