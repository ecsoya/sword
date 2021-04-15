package com.soyatec.sword.wallet.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;

/**
 * 钱包记录，联合查询对象 t_wallet_record
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2020-12-18
 */
public class UserWalletUnionRecord extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** ID */
	private Long id;

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

	/** 状态 0-未同步 1-已同步 */
	@Excel(name = "状态 0-未同步 1-已同步")
	private Integer status;

	/** $column.columnComment */
	@Excel(name = "状态 0-未同步 1-已同步")
	private Integer kind;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getKind() {
		return kind;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("orderNo", getOrderNo()).append("type", getType())
				.append("symbol", getSymbol()).append("amount", getAmount()).append("status", getStatus())
				.append("kind", getKind()).append("createTime", getCreateTime()).toString();
	}
}
