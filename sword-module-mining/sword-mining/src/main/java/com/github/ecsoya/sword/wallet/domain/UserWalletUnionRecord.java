package com.github.ecsoya.sword.wallet.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserWalletUnionRecord.
 */
public class UserWalletUnionRecord extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Long id;

	/** The user id. */
	@Excel(name = "用户ID")
	private Long userId;

	/** The order no. */
	@Excel(name = "订单号")
	private String orderNo;

	/** The type. */
	@Excel(name = "类型 0-后台设置 1-加 2-减")
	private Integer type;

	/** The symbol. */
	@Excel(name = "币种")
	private String symbol;

	/** The amount. */
	@Excel(name = "金额")
	private BigDecimal amount;

	/** The status. */
	@Excel(name = "状态 0-未同步 1-已同步")
	private Integer status;

	/** The kind. */
	@Excel(name = "状态 0-未同步 1-已同步")
	private Integer kind;

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
	 * Sets the 订单号.
	 *
	 * @param orderNo the new 订单号
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the 订单号.
	 *
	 * @return the 订单号
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the 类型 0-后台设置 1-加 2-减.
	 *
	 * @param type the new 类型 0-后台设置 1-加 2-减
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Gets the 类型 0-后台设置 1-加 2-减.
	 *
	 * @return the 类型 0-后台设置 1-加 2-减
	 */
	public Integer getType() {
		return type;
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
	 * Sets the 状态 0-未同步 1-已同步.
	 *
	 * @param status the new 状态 0-未同步 1-已同步
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态 0-未同步 1-已同步.
	 *
	 * @return the 状态 0-未同步 1-已同步
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the $column.
	 *
	 * @param kind the new $column
	 */
	public void setKind(Integer kind) {
		this.kind = kind;
	}

	/**
	 * Gets the $column.
	 *
	 * @return the $column
	 */
	public Integer getKind() {
		return kind;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("orderNo", getOrderNo()).append("type", getType())
				.append("symbol", getSymbol()).append("amount", getAmount()).append("status", getStatus())
				.append("kind", getKind()).append("createTime", getCreateTime()).toString();
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
