package com.soyatec.sword.report.domain;

import java.math.BigDecimal;

public class UserAmount {

	private Long userId;

	private BigDecimal amount;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isEmpty() {
		return amount == null || amount.doubleValue() <= 0;
	}

	public static UserAmount empty(Long userId) {
		UserAmount userAmount = new UserAmount();
		userAmount.setUserId(userId);
		userAmount.setAmount(BigDecimal.ZERO);
		return userAmount;
	}
}
