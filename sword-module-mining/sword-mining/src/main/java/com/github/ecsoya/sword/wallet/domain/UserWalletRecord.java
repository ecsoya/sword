package com.github.ecsoya.sword.wallet.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.constants.IConstants;

/**
 * The Class UserWalletRecord.
 */
public class UserWalletRecord extends BaseEntity implements IConstants {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant KIND_AMOUNT. */
	public static final Integer KIND_AMOUNT = UserWalletAccount.KIND_AMOUNT;// 余额

	/** The Constant KIND_FROZEN. */
	public static final Integer KIND_FROZEN = UserWalletAccount.KIND_FROZEN;// 冻结

	/** The Constant KIND_LOCKED. */
	public static final Integer KIND_LOCKED = UserWalletAccount.KIND_LOCKED;// 锁定

	/** The Constant TYPE_IN. */
	public static final Integer TYPE_IN = 0; // 收入

	/** The Constant TYPE_OUT. */
	public static final Integer TYPE_OUT = 1;// 支出

	/** The Constant TYPE_ADMIN. */
	public static final Integer TYPE_ADMIN = 2;// 支出

	/** The id. */
	private Long id;

	/** The user id. */
	@Excel(name = "用户ID")
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The email. */
	private String email;

	/** The address. */
	private String address;

	/** The symbol. */
	@Excel(name = "币种")
	private String symbol;

	/** The amount. */
	@Excel(name = "金额")
	private BigDecimal amount;

	/** The type. */
	@Excel(name = "类型：0加1减")
	private Integer type;

	/** The kind. */
	@Excel(name = "种类：0余额1冻结")
	private Integer kind;

	/** The status. */
	@Excel(name = "状态")
	private Integer status;

	/** The order no. */
	@Excel(name = "订单No")
	private String orderNo;

	/** The days. */
	@Excel(name = "冻结天数")
	private Integer days;

	/** The details. */
	private Integer details;

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
	 * Sets the 金额.
	 *
	 * @param amount the new 金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the 金额.
	 *
	 * @return the 金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the 类型：0加1减.
	 *
	 * @param type the new 类型：0加1减
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型：0加1减.
	 *
	 * @return the 类型：0加1减
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Sets the 种类：0余额1冻结.
	 *
	 * @param kind the new 种类：0余额1冻结
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * Gets the 种类：0余额1冻结.
	 *
	 * @return the 种类：0余额1冻结
	 */
	public Integer getKind() {
		return kind;
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
	 * Sets the 订单No.
	 *
	 * @param orderNo the new 订单No
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the 订单No.
	 *
	 * @return the 订单No
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 冻结天数.
	 *
	 * @param days the new 冻结天数
	 */
	public void setDays(Integer days) {
		this.days = days;
	}

	/**
	 * Gets the 冻结天数.
	 *
	 * @return the 冻结天数
	 */
	public Integer getDays() {
		return days;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("symbol", getSymbol()).append("amount", getAmount())
				.append("type", getType()).append("kind", getKind()).append("status", getStatus())
				.append("orderNo", getOrderNo()).append("days", getDays()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
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
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the details.
	 *
	 * @return the details
	 */
	public Integer getDetails() {
		return details;
	}

	/**
	 * Sets the details.
	 *
	 * @param details the new details
	 */
	public void setDetails(Integer details) {
		this.details = details;
	}
}
