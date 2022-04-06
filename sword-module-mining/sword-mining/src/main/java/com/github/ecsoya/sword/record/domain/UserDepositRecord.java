package com.github.ecsoya.sword.record.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.constants.IConstants;

/**
 * The Class UserDepositRecord.
 */
public class UserDepositRecord extends BaseEntity implements IConstants {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant TYPE_ADMIN. */
	public static final Integer TYPE_ADMIN = 0;

	/** The Constant TYPE_WALLET. */
	public static final Integer TYPE_WALLET = 1;

	/** The id. */
	private Long id;

	/** The height. */
	@Excel(name = "区块高度")
	private Long height;

	/** The tx id. */
	@Excel(name = "交易Hash")
	private String txId;

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
	 * Sets the 区块高度.
	 *
	 * @param height the new 区块高度
	 */
	public void setHeight(Long height) {
		this.height = height;
	}

	/**
	 * Gets the 区块高度.
	 *
	 * @return the 区块高度
	 */
	public Long getHeight() {
		return height;
	}

	/**
	 * Sets the 交易Hash.
	 *
	 * @param txId the new 交易Hash
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * Gets the 交易Hash.
	 *
	 * @return the 交易Hash
	 */
	public String getTxId() {
		return txId;
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("height", getHeight()).append("txId", getTxId()).append("userId", getUserId())
				.append("orderNo", getOrderNo()).append("type", getType()).append("symbol", getSymbol())
				.append("amount", getAmount()).append("status", getStatus()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
