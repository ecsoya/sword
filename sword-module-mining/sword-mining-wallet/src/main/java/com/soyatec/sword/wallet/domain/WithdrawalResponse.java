package com.soyatec.sword.wallet.domain;

import java.math.BigDecimal;

public class WithdrawalResponse {
	public static final Integer STATUS_WAITING = 0;
	public static final Integer STATUS_FAILURE = 1;
	public static final Integer STATUS_SUCCESS = 2;

	private String address;

	private String memo;

	private BigDecimal fees; // 手续费
	private String feesSymbol;

	private BigDecimal amount; // 数量

	private Long subTime; // 提交时间

	private String orderNo; // 业务号

	private String transactionId; // 提币申请id

	private Integer status;//// 提币状态 0提交 1失败 2成功 (内部划转成功直接返回2)

	public Long getSubTime() {
		return subTime;
	}

	public void setSubTime(Long subTime) {
		this.subTime = subTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getFees() {
		return fees;
	}

	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getFeesSymbol() {
		return feesSymbol;
	}

	public void setFeesSymbol(String feesSymbol) {
		this.feesSymbol = feesSymbol;
	}
}
