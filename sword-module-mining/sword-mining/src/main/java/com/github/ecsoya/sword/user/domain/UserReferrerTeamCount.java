package com.github.ecsoya.sword.user.domain;

public class UserReferrerTeamCount {

	private Long userId;

	/**
	 * 全部人数
	 */
	private UserTeamCount total;

	/**
	 * 今日新增
	 */
	private UserTeamCount today;

	/**
	 * 昨日新增
	 */
	private UserTeamCount yesterday;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserTeamCount getTotal() {
		return total;
	}

	public void setTotal(UserTeamCount total) {
		this.total = total;
	}

	public UserTeamCount getToday() {
		return today;
	}

	public void setToday(UserTeamCount today) {
		this.today = today;
	}

	public UserTeamCount getYesterday() {
		return yesterday;
	}

	public void setYesterday(UserTeamCount yesterday) {
		this.yesterday = yesterday;
	}

	@Override
	public String toString() {
		return "userId=" + userId + ", total=" + total + ", today=" + today + ", yesterday=" + yesterday;
	}

}
