package com.github.ecsoya.sword.order.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.constants.IConstants;

/**
 * The Class UserDepositOrder.
 */
public class UserDepositOrder extends BaseEntity implements IConstants {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant STATUS_COLLECT_STARTED. */
	public static final Integer STATUS_COLLECT_STARTED = 2;

	/** The Constant STATUS_COLLECT_SUCCESS. */
	public static final Integer STATUS_COLLECT_SUCCESS = 3;

	/** The id. */
	private Long id;

	/** The order no. */
	@Excel(name = "订单ID")
	private String orderNo;

	/** The user id. */
	@Excel(name = "用户ID")
	private Long userId;

	/** The login name. */
	private String loginName;

	/** The email. */
	private String email;

	/** The mobile. */
	private String mobile;

	/** The address. */
	@Excel(name = "充值地址")
	private String address;

	/** The amount. */
	@Excel(name = "充值金额")
	private BigDecimal amount;

	/** The symbol. */
	@Excel(name = "充值币种")
	private String symbol;

	/** The chain. */
	@Excel(name = "充值链")
	private String chain;

	/** The status. */
	@Excel(name = "状态 0-开始 1-取消 2-成功 3-失败")
	private Integer status;

	/** The tx id. */
	@Excel(name = "交易ID")
	private String txId;

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
	 * Sets the 订单ID.
	 *
	 * @param orderNo the new 订单ID
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the 订单ID.
	 *
	 * @return the 订单ID
	 */
	public String getOrderNo() {
		return orderNo;
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
	 * Sets the 充值地址.
	 *
	 * @param address the new 充值地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the 充值地址.
	 *
	 * @return the 充值地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the 充值金额.
	 *
	 * @param amount the new 充值金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the 充值金额.
	 *
	 * @return the 充值金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the 充值币种.
	 *
	 * @param symbol the new 充值币种
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the 充值币种.
	 *
	 * @return the 充值币种
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the 充值链.
	 *
	 * @param chain the new 充值链
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

	/**
	 * Gets the 充值链.
	 *
	 * @return the 充值链
	 */
	public String getChain() {
		return chain;
	}

	/**
	 * Sets the 状态 0-开始 1-取消 2-成功 3-失败.
	 *
	 * @param status the new 状态 0-开始 1-取消 2-成功 3-失败
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the 状态 0-开始 1-取消 2-成功 3-失败.
	 *
	 * @return the 状态 0-开始 1-取消 2-成功 3-失败
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * Sets the 交易ID.
	 *
	 * @param txId the new 交易ID
	 */
	public void setTxId(String txId) {
		this.txId = txId;
	}

	/**
	 * Gets the 交易ID.
	 *
	 * @return the 交易ID
	 */
	public String getTxId() {
		return txId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("orderNo", getOrderNo()).append("userId", getUserId()).append("address", getAddress())
				.append("amount", getAmount()).append("symbol", getSymbol()).append("chain", getChain())
				.append("status", getStatus()).append("txId", getTxId()).append("createTime", getCreateTime())
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
}
