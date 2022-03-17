package com.github.ecsoya.sword.record.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.constants.IConstants;

/**
 * 用户提现对象 t_user_record_withdrawal
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserWithdrawalRecord extends BaseEntity implements IConstants {
	private static final long serialVersionUID = 1L;

	public static final Integer TYPE_ADMIN = 0;
	public static final Integer TYPE_WALLET = 1;

	/** ID */
	private Long id;

	/** 区块高度 */
	@Excel(name = "区块高度")
	private Long height;

	/** 交易Hash */
	@Excel(name = "交易Hash")
	private String txId;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	/** 订单号 */
	@Excel(name = "订单号")
	private String orderNo;

	/** 类型 0-后台设置 1-加 2-减 */
	@Excel(name = "类型 0-后台设置 1-加 2-减")
	private Integer type;

	/** 币种 */
	@Excel(name = "币种")
	private String symbol;

	/** 金额 */
	@Excel(name = "金额")
	private BigDecimal amount;

	/** 手续费 */
	@Excel(name = "手续费")
	private BigDecimal fee;

	/** 状态 0-未同步 1-已同步 */
	@Excel(name = "状态 0-未同步 1-已同步")
	private Integer status;

	/** 交易手续费 */
	@Excel(name = "交易手续费")
	private BigDecimal walletFee;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getHeight() {
		return height;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getTxId() {
		return txId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setWalletFee(BigDecimal walletFee) {
		this.walletFee = walletFee;
	}

	public BigDecimal getWalletFee() {
		return walletFee;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("height", getHeight()).append("txId", getTxId()).append("userId", getUserId())
				.append("orderNo", getOrderNo()).append("type", getType()).append("symbol", getSymbol())
				.append("amount", getAmount()).append("fee", getFee()).append("status", getStatus())
				.append("walletFee", getWalletFee()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
