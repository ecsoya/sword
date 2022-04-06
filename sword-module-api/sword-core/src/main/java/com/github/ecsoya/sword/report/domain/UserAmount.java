package com.github.ecsoya.sword.report.domain;

import java.math.BigDecimal;

/**
 * The Class UserAmount.
 */
public class UserAmount {

	/** The user id. */
	private Long userId;

	/** The amount. */
	private BigDecimal amount;

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return amount == null || amount.doubleValue() <= 0;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "UserAmount [userId=" + userId + ", amount=" + amount + "]";
	}

	/**
	 * Empty.
	 *
	 * @param userId the user id
	 * @return the user amount
	 */
	public static UserAmount empty(Long userId) {
		final UserAmount userAmount = new UserAmount();
		userAmount.setUserId(userId);
		userAmount.setAmount(BigDecimal.ZERO);
		return userAmount;
	}
}
