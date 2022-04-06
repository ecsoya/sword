package com.github.ecsoya.sword.wallet.domain;

import java.math.BigDecimal;

/**
 * The Class WithdrawalResponse.
 */
public class WithdrawalResponse {

	/** The Constant STATUS_WAITING. */
	public static final Integer STATUS_WAITING = 0;

	/** The Constant STATUS_FAILURE. */
	public static final Integer STATUS_FAILURE = 1;

	/** The Constant STATUS_SUCCESS. */
	public static final Integer STATUS_SUCCESS = 2;

	/** The Constant STATUS_MANUAL. */
	public static final Integer STATUS_MANUAL = 3;

	/** The address. */
	private String address;

	/** The memo. */
	private String memo;

	/** The fees. */
	private BigDecimal fees; // 手续费

	/** The fees symbol. */
	private String feesSymbol;

	/** The amount. */
	private BigDecimal amount; // 数量

	/** The sub time. */
	private Long subTime; // 提交时间

	/** The order no. */
	private String orderNo; // 业务号

	/** The transaction id. */
	private String transactionId; // 提币申请id

	/** The status. */
	private Integer status;//// 提币状态 0提交 1失败 2成功 (内部划转成功直接返回2)

	/**
	 * Gets the sub time.
	 *
	 * @return the sub time
	 */
	public Long getSubTime() {
		return subTime;
	}

	/**
	 * Sets the sub time.
	 *
	 * @param subTime the new sub time
	 */
	public void setSubTime(Long subTime) {
		this.subTime = subTime;
	}

	/**
	 * Gets the order no.
	 *
	 * @return the order no
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * Sets the order no.
	 *
	 * @param orderNo the new order no
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
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
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * Sets the memo.
	 *
	 * @param memo the new memo
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * Gets the fees.
	 *
	 * @return the fees
	 */
	public BigDecimal getFees() {
		return fees;
	}

	/**
	 * Sets the fees.
	 *
	 * @param fees the new fees
	 */
	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * Gets the fees symbol.
	 *
	 * @return the fees symbol
	 */
	public String getFeesSymbol() {
		return feesSymbol;
	}

	/**
	 * Sets the fees symbol.
	 *
	 * @param feesSymbol the new fees symbol
	 */
	public void setFeesSymbol(String feesSymbol) {
		this.feesSymbol = feesSymbol;
	}
}
