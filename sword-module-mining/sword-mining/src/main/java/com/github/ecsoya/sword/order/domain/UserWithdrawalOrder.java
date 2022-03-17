package com.github.ecsoya.sword.order.domain;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * 提现订单对象 t_user_order_withdrawal
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserWithdrawalOrder extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final Integer STATUS_NONE = 0;
	public static final Integer STATUS_CANCEL = 1;
	public static final Integer STATUS_SUCCESS = 2;
	public static final Integer STATUS_FAILURE = 3;
	public static final Integer STATUS_CONFIRM = 4;
	public static final Integer STATUS_NOTIFIED = 5;
	// 手动提币状态
	public static final Integer STATUS_MANUAL_START = UserWithdrawalManual.STATUS_MANUAL_START;
	public static final Integer STATUS_MANUAL_CANCEL = UserWithdrawalManual.STATUS_MANUAL_CANCEL;
	public static final Integer STATUS_MANUAL_SUCCESS = UserWithdrawalManual.STATUS_MANUAL_SUCCESS;
	public static final Integer STATUS_MANUAL_FAILURE = UserWithdrawalManual.STATUS_MANUAL_FAILURE;

	public static final Integer FEEDBACK_NONE = 0;
	public static final Integer FEEDBACK_FAILURE = 1;
	public static final Integer FEEDBACK_SUCCESS = 2;

	public static final Integer CALLABLE_NONE = 0;
	public static final Integer CALLABLE_FINISHED = 1;

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

	/** 提现地址 */
	@Excel(name = "提现地址")
	private String address;

	/** 提现金额 */
	@Excel(name = "提现金额")
	private BigDecimal amount;

	/** 手续费 */
	@Excel(name = "手续费")
	private BigDecimal fee;

	/** 实际提现 */
	@Excel(name = "实际提现")
	private BigDecimal withdrawal;

	/** 提现币种 */
	@Excel(name = "提现币种")
	private String symbol;

	/** 提现链 */
	@Excel(name = "提现链")
	private String chain;

	private Integer status;

	private Integer feedback;

	private Integer callable;

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

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setWithdrawal(BigDecimal withdrawal) {
		this.withdrawal = withdrawal;
	}

	public BigDecimal getWithdrawal() {
		return withdrawal;
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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", getId())
				.append("orderNo", getOrderNo()).append("userId", getUserId()).append("address", getAddress())
				.append("amount", getAmount()).append("fee", getFee()).append("withdrawal", getWithdrawal())
				.append("symbol", getSymbol()).append("chain", getChain()).append("status", getStatus())
				.append("createTime", getCreateTime()).append("updateTime", getUpdateTime())
				.append("remark", getRemark()).toString();
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

	public Integer getFeedback() {
		return feedback;
	}

	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}

	public Integer getCallable() {
		return callable;
	}

	public void setCallable(Integer callable) {
		this.callable = callable;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
