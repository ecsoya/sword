package com.soyatec.sword.user.domain;

public class UserReferrerTeam {

	private Long userId;

	private UserTeamCount total;

	private UserTeamCount today;

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

}
