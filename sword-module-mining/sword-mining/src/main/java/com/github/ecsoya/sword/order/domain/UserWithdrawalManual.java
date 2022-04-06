package com.github.ecsoya.sword.order.domain;

/**
 * The Class UserWithdrawalManual.
 */
public class UserWithdrawalManual {

	/** The Constant STATUS_MANUAL_START. */
	// 手动提币状态
	public static final Integer STATUS_MANUAL_START = 60;

	/** The Constant STATUS_MANUAL_CANCEL. */
	public static final Integer STATUS_MANUAL_CANCEL = 61;

	/** The Constant STATUS_MANUAL_SUCCESS. */
	public static final Integer STATUS_MANUAL_SUCCESS = 62;

	/** The Constant STATUS_MANUAL_FAILURE. */
	public static final Integer STATUS_MANUAL_FAILURE = 62;

	/** The id. */
	private Long id;

	/** The tx id. */
	private String txId;

	/** The status. */
	private Integer status;

	/** The remark. */
	private String remark;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the tx id.
	 *
	 * @return the tx id
	 */
	public String getTxId() {
		return txId;
	}

	/**
	 * Sets the tx id.
	 *
	 * @param txId the new tx id
	 */
	public void setTxId(String txId) {
		this.txId = txId;
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
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * Gets the remark.
	 *
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Sets the remark.
	 *
	 * @param remark the new remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
