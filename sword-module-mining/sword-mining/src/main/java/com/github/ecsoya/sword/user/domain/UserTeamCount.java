package com.github.ecsoya.sword.user.domain;

/**
 * The Class UserTeamCount.
 */
public class UserTeamCount {

	/** The user id. */
	private Long userId;

	/** The referrer count. */
	private Long referrerCount;

	/** The umbrella count. */
	private Long umbrellaCount;

	/**
	 * Gets the 直推人数.
	 *
	 * @return the 直推人数
	 */
	public Long getReferrerCount() {
		return referrerCount;
	}

	/**
	 * Sets the 直推人数.
	 *
	 * @param referrerCount the new 直推人数
	 */
	public void setReferrerCount(Long referrerCount) {
		this.referrerCount = referrerCount;
	}

	/**
	 * Gets the 伞下人数.
	 *
	 * @return the 伞下人数
	 */
	public Long getUmbrellaCount() {
		return umbrellaCount;
	}

	/**
	 * Sets the 伞下人数.
	 *
	 * @param umbrellaCount the new 伞下人数
	 */
	public void setUmbrellaCount(Long umbrellaCount) {
		this.umbrellaCount = umbrellaCount;
	}

	/**
	 * Gets the 用户ID.
	 *
	 * @return the 用户ID
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 用户ID.
	 *
	 * @param userId the new 用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "userId=" + userId + ", referrerCount=" + referrerCount + ", umbrellaCount=" + umbrellaCount;
	}

}
