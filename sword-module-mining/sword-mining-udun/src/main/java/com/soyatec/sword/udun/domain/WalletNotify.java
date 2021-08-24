package com.soyatec.sword.udun.domain;

import java.math.BigDecimal;

public class WalletNotify {

	public static final Integer TYPE_WITHDRAWAL = 2;
	public static final Integer TYPE_DEPOSITE = 1;

	public static final Integer STATUS_NONE = 0;
	public static final Integer STATUS_PASSED = 1;
	public static final Integer STATUS_REJECTED = 2;
	public static final Integer STATUS_SUCCESS = 3;
	public static final Integer STATUS_FALIURE = 4;
	// 区块高度
	private String blockHigh;
	// 交易Id
	private String txId;
	// 交易流水号
	private String tradeId;
	// 交易地址
	private String address;
	// 币种类型
	private String symbol;
	// 交易金额
	private BigDecimal amount;
	// 交易类型 1-充值 2-提款(转账)
	private Integer tradeType;
	// 交易状态 0-待审核 1-成功 2-失败,充值无审核
	private Integer status;
	// 旷工费
	private BigDecimal fee;
	private Integer decimals;
	// 提币申请单号
	private String orderNo;
	// 备注
	private String memo;

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getTradeType() {
		return tradeType;
	}

	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getDecimals() {
		return decimals;
	}

	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}

	public String getBusinessId() {
		return getOrderNo();
	}

	public void setBusinessId(String businessId) {
		this.setOrderNo(businessId);
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBlockHigh() {
		return blockHigh;
	}

	public void setBlockHigh(String blockHigh) {
		this.blockHigh = blockHigh;
	}

}
