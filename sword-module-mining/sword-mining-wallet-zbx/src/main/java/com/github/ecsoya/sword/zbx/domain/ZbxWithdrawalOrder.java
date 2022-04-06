package com.github.ecsoya.sword.zbx.domain;

/**
 * The Class ZbxWithdrawalOrder.
 */
public class ZbxWithdrawalOrder extends ZbxResponse {

	/** The Constant STATUS_WAITING. */
	public static final Integer STATUS_WAITING = 0;

	/** The Constant STATUS_FAILURE. */
	public static final Integer STATUS_FAILURE = 1;

	/** The Constant STATUS_SUCCESS. */
	public static final Integer STATUS_SUCCESS = 2;

	/** The fees. */
	private Double fees; // 手续费

	/** The amount. */
	private Double amount; // 数量

	/** The sub time. */
	private Long subTime; // 提交时间

	/** The order no. */
	private String orderNo; // 业务号

	/** The transaction id. */
	private String transactionId; // 提币申请id

	/** The status. */
	private Integer status;//// 提币状态 0提交 1失败 2成功 (内部划转成功直接返回2)

	/**
	 * Gets the fees.
	 *
	 * @return the fees
	 */
	public Double getFees() {
		return fees;
	}

	/**
	 * Sets the fees.
	 *
	 * @param fees the new fees
	 */
	public void setFees(Double fees) {
		this.fees = fees;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

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

}
