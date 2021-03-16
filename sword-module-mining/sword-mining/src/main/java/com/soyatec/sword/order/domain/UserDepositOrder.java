package com.soyatec.sword.order.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.soyatec.sword.common.annotation.Excel;
import com.soyatec.sword.common.core.domain.BaseEntity;
import com.soyatec.sword.constants.IConstants;

/**
 * 充值订单对象 t_user_order_deposit
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserDepositOrder extends BaseEntity implements IConstants {
	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_COLLECT_STARTED = 2;
	public static final Integer STATUS_COLLECT_SUCCESS = 3;

	/** ID */
	private Long id;

	/** 订单ID */
	@Excel(name = "订单ID")
	private String orderNo;

	/** 用户ID */
	@Excel(name = "用户ID")
	private Long userId;

	private String loginName;

	private String email;

	private String mobile;

	/** 充值地址 */
	@Excel(name = "充值地址")
	private String address;

	/** 充值金额 */
	@Excel(name = "充值金额")
	private BigDecimal amount;

	/** 充值币种 */
	@Excel(name = "充值币种")
	private String symbol;

	/** 充值链 */
	@Excel(name = "充值链")
	private String chain;

	/** 状态 0-开始 1-取消 2-成功 3-失败 */
	@Excel(name = "状态 0-开始 1-取消 2-成功 3-失败")
	private Integer status;

	/** 交易ID */
	@Excel(name = "交易ID")
	private String txId;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getChain() {
		return chain;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getTxId() {
		return txId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("orderNo", getOrderNo()).append("userId", getUserId()).append("address", getAddress())
				.append("amount", getAmount()).append("symbol", getSymbol()).append("chain", getChain())
				.append("status", getStatus()).append("txId", getTxId()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
