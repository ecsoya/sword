package com.github.ecsoya.sword.udun.domain;

import java.math.BigDecimal;

/**
 * The Class WalletNotify.
 */
public class WalletNotify {

	/** The Constant TYPE_WITHDRAWAL. */
	public static final Integer TYPE_WITHDRAWAL = 2;

	/** The Constant TYPE_DEPOSITE. */
	public static final Integer TYPE_DEPOSITE = 1;

	/** The Constant STATUS_NONE. */
	public static final Integer STATUS_NONE = 0;

	/** The Constant STATUS_PASSED. */
	public static final Integer STATUS_PASSED = 1;

	/** The Constant STATUS_REJECTED. */
	public static final Integer STATUS_REJECTED = 2;

	/** The Constant STATUS_SUCCESS. */
	public static final Integer STATUS_SUCCESS = 3;

	/** The Constant STATUS_FALIURE. */
	public static final Integer STATUS_FALIURE = 4;

	/** The block high. */
	// 区块高度
	private String blockHigh;

	/** The tx id. */
	// 交易Id
	private String txId;

	/** The trade id. */
	// 交易流水号
	private String tradeId;

	/** The address. */
	// 交易地址
	private String address;

	/** The symbol. */
	// 币种类型
	private String symbol;

	/** The amount. */
	// 交易金额
	private BigDecimal amount;

	/** The trade type. */
	// 交易类型 1-充值 2-提款(转账)
	private Integer tradeType;

	/** The status. */
	// 交易状态 0-待审核 1-成功 2-失败,充值无审核
	private Integer status;

	/** The fee. */
	// 旷工费
	private BigDecimal fee;

	/** The decimals. */
	private Integer decimals;

	/** The order no. */
	// 提币申请单号
	private String orderNo;

	/** The memo. */
	// 备注
	private String memo;

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
	 * Gets the trade id.
	 *
	 * @return the trade id
	 */
	public String getTradeId() {
		return tradeId;
	}

	/**
	 * Sets the trade id.
	 *
	 * @param tradeId the new trade id
	 */
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
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
	 * Gets the trade type.
	 *
	 * @return the trade type
	 */
	public Integer getTradeType() {
		return tradeType;
	}

	/**
	 * Sets the trade type.
	 *
	 * @param tradeType the new trade type
	 */
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
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
	 * Gets the fee.
	 *
	 * @return the fee
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * Sets the fee.
	 *
	 * @param fee the new fee
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * Gets the decimals.
	 *
	 * @return the decimals
	 */
	public Integer getDecimals() {
		return decimals;
	}

	/**
	 * Sets the decimals.
	 *
	 * @param decimals the new decimals
	 */
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}

	/**
	 * Gets the business id.
	 *
	 * @return the business id
	 */
	public String getBusinessId() {
		return getOrderNo();
	}

	/**
	 * Sets the business id.
	 *
	 * @param businessId the new business id
	 */
	public void setBusinessId(String businessId) {
		this.setOrderNo(businessId);
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
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
	 * Gets the block high.
	 *
	 * @return the block high
	 */
	public String getBlockHigh() {
		return blockHigh;
	}

	/**
	 * Sets the block high.
	 *
	 * @param blockHigh the new block high
	 */
	public void setBlockHigh(String blockHigh) {
		this.blockHigh = blockHigh;
	}

}
