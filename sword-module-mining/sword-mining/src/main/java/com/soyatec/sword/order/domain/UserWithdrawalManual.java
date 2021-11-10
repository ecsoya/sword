package com.soyatec.sword.order.domain;

public class UserWithdrawalManual {
	// 手动提币状态
	public static final Integer STATUS_MANUAL_START = 60;
	public static final Integer STATUS_MANUAL_CANCEL = 61;
	public static final Integer STATUS_MANUAL_SUCCESS = 62;
	public static final Integer STATUS_MANUAL_FAILURE = 62;

	private Long id;
	private String txId;
	private Integer status;
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
