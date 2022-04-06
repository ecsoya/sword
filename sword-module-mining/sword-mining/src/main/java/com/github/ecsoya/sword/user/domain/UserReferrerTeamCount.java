package com.github.ecsoya.sword.user.domain;

/**
 * The Class UserReferrerTeamCount.
 */
public class UserReferrerTeamCount {

	/** The user id. */
	private Long userId;

	/** The total. */
	private UserTeamCount total;

	/** The today. */
	private UserTeamCount today;

	/** The yesterday. */
	private UserTeamCount yesterday;

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
	 * Gets the 全部人数.
	 *
	 * @return the 全部人数
	 */
	public UserTeamCount getTotal() {
		return total;
	}

	/**
	 * Sets the 全部人数.
	 *
	 * @param total the new 全部人数
	 */
	public void setTotal(UserTeamCount total) {
		this.total = total;
	}

	/**
	 * Gets the 今日新增.
	 *
	 * @return the 今日新增
	 */
	public UserTeamCount getToday() {
		return today;
	}

	/**
	 * Sets the 今日新增.
	 *
	 * @param today the new 今日新增
	 */
	public void setToday(UserTeamCount today) {
		this.today = today;
	}

	/**
	 * Gets the 昨日新增.
	 *
	 * @return the 昨日新增
	 */
	public UserTeamCount getYesterday() {
		return yesterday;
	}

	/**
	 * Sets the 昨日新增.
	 *
	 * @param yesterday the new 昨日新增
	 */
	public void setYesterday(UserTeamCount yesterday) {
		this.yesterday = yesterday;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "userId=" + userId + ", total=" + total + ", today=" + today + ", yesterday=" + yesterday;
	}

}
