package com.soyatec.sword.wallet.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;
import com.soyatec.sword.constants.IConstants;

/**
 * 用户钱包记录对象 t_user_wallet_record
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserWalletRecord extends BaseEntity implements IConstants {
	private static final long serialVersionUID = 1L;

	public static final Integer KIND_AMOUNT = UserWalletAccount.KIND_AMOUNT;// 余额
	public static final Integer KIND_FROZEN = UserWalletAccount.KIND_FROZEN;// 冻结
	public static final Integer KIND_LOCKED = UserWalletAccount.KIND_LOCKED;// 锁定

	public static final Integer TYPE_IN = 0; // 收入
	public static final Integer TYPE_OUT = 1;// 支出
	public static final Integer TYPE_ADMIN = 2;// 支出

	/** ID */
	private Long id;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	/** 币种 */
	@Excel(name = "币种")
	private String symbol;

	/** 金额 */
	@Excel(name = "金额")
	private BigDecimal amount;

	/** 类型：0加1减 */
	@Excel(name = "类型：0加1减")
	private Integer type;

	/** 种类：0余额1冻结 */
	@Excel(name = "种类：0余额1冻结")
	private Integer kind;

	/** 状态 */
	@Excel(name = "状态")
	private Integer status;

	/** 订单No */
	@Excel(name = "订单No")
	private String orderNo;

	/** 冻结天数 */
	@Excel(name = "冻结天数")
	private Integer days;

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

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public Integer getKind() {
		return kind;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Integer getDays() {
		return days;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("userId", getUserId()).append("symbol", getSymbol()).append("amount", getAmount())
				.append("type", getType()).append("kind", getKind()).append("status", getStatus())
				.append("orderNo", getOrderNo()).append("days", getDays()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
