package com.soyatec.sword.zbx.domain;

/**
 *
 * 提币申请
 *
 * @author ecsoya
 *
 */
public class ZbxWithdrawalOrder extends ZbxResponse {

	public static final Integer STATUS_WAITING = 0;
	public static final Integer STATUS_FAILURE = 1;
	public static final Integer STATUS_SUCCESS = 2;

	private Double fees; // 手续费

	private Double amount; // 数量

	private Long subTime; // 提交时间

	private String orderNo; // 业务号

	private String transactionId; // 提币申请id

	private Integer status;//// 提币状态 0提交 1失败 2成功 (内部划转成功直接返回2)

	public Double getFees() {
		return fees;
	}

	public void setFees(Double fees) {
		this.fees = fees;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

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

}
