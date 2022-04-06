package com.github.ecsoya.sword.order.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserWithdrawalOrder.
 */
public class UserWithdrawalOrder extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant STATUS_NONE. */
	public static final Integer STATUS_NONE = 0;

	/** The Constant STATUS_CANCEL. */
	public static final Integer STATUS_CANCEL = 1;

	/** The Constant STATUS_SUCCESS. */
	public static final Integer STATUS_SUCCESS = 2;

	/** The Constant STATUS_FAILURE. */
	public static final Integer STATUS_FAILURE = 3;

	/** The Constant STATUS_CONFIRM. */
	public static final Integer STATUS_CONFIRM = 4;

	/** The Constant STATUS_NOTIFIED. */
	public static final Integer STATUS_NOTIFIED = 5;

	/** The Constant STATUS_MANUAL_START. */
	// 手动提币状态
	public static final Integer STATUS_MANUAL_START = UserWithdrawalManual.STATUS_MANUAL_START;

	/** The Constant STATUS_MANUAL_CANCEL. */
	public static final Integer STATUS_MANUAL_CANCEL = UserWithdrawalManual.STATUS_MANUAL_CANCEL;

	/** The Constant STATUS_MANUAL_SUCCESS. */
	public static final Integer STATUS_MANUAL_SUCCESS = UserWithdrawalManual.STATUS_MANUAL_SUCCESS;

	/** The Constant STATUS_MANUAL_FAILURE. */
	public static final Integer STATUS_MANUAL_FAILURE = UserWithdrawalManual.STATUS_MANUAL_FAILURE;

	/** The Constant FEEDBACK_NONE. */
	public static final Integer FEEDBACK_NONE = 0;

	/** The Constant FEEDBACK_FAILURE. */
	public static final Integer FEEDBACK_FAILURE = 1;

	/** The Constant FEEDBACK_SUCCESS. */
	public static final Integer FEEDBACK_SUCCESS = 2;

	/** The Constant CALLABLE_NONE. */
	public static final Integer CALLABLE_NONE = 0;

	/** The Constant CALLABLE_FINISHED. */
	public static final Integer CALLABLE_FINISHED = 1;

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
	@Excel(name = "提现地址")
	private String address;

	/** The amount. */
	@Excel(name = "提现金额")
	private BigDecimal amount;

	/** The fee. */
	@Excel(name = "手续费")
	private BigDecimal fee;

	/** The withdrawal. */
	@Excel(name = "实际提现")
	private BigDecimal withdrawal;

	/** The symbol. */
	@Excel(name = "提现币种")
	private String symbol;

	/** The chain. */
	@Excel(name = "提现链")
	private String chain;

	/** The status. */
	private Integer status;

	/** The feedback. */
	private Integer feedback;

	/** The callable. */
	private Integer callable;

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
	 * Sets the 提现地址.
	 *
	 * @param address the new 提现地址
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the 提现地址.
	 *
	 * @return the 提现地址
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the 提现金额.
	 *
	 * @param amount the new 提现金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the 提现金额.
	 *
	 * @return the 提现金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the 手续费.
	 *
	 * @param fee the new 手续费
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * Gets the 手续费.
	 *
	 * @return the 手续费
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * Sets the 实际提现.
	 *
	 * @param withdrawal the new 实际提现
	 */
	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}

	/**
	 * Gets the 实际提现.
	 *
	 * @return the 实际提现
	 */
	public BigDecimal getWithdrawal() {
		return withdrawal;
	}

	/**
	 * Sets the 提现币种.
	 *
	 * @param symbol the new 提现币种
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Gets the 提现币种.
	 *
	 * @return the 提现币种
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the 提现链.
	 *
	 * @param chain the new 提现链
	 */
	public void setChain(String chain) {
		this.chain = chain;
	}

	/**
	 * Gets the 提现链.
	 *
	 * @return the 提现链
	 */
	public String getChain() {
		return chain;
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
	 * Gets the status.
	 *
	 * @return the status
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
				.append("orderNo", getOrderNo()).append("userId", getUserId()).append("address", getAddress())
				.append("amount", getAmount()).append("fee", getFee()).append("withdrawal", getWithdrawal())
				.append("symbol", getSymbol()).append("chain", getChain()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
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
	 * Gets the feedback.
	 *
	 * @return the feedback
	 */
	public Integer getFeedback() {
		return feedback;
	}

	/**
	 * Sets the feedback.
	 *
	 * @param feedback the new feedback
	 */
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}

	/**
	 * Gets the callable.
	 *
	 * @return the callable
	 */
	public Integer getCallable() {
		return callable;
	}

	/**
	 * Sets the callable.
	 *
	 * @param callable the new callable
	 */
	public void setCallable(Integer callable) {
		this.callable = callable;
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
