package com.soyatec.sword.user.domain;

public class UserTeamCount {

	private Long userId;

	private Long referrerCount;

	private Long umbrellaCount;

	public Long getReferrerCount() {
		return referrerCount;
	}

	public void setReferrerCount(Long referrerCount) {
		this.referrerCount = referrerCount;
	}

	public Long getUmbrellaCount() {
		return umbrellaCount;
	}

	public void setUmbrellaCount(Long umbrellaCount) {
		this.umbrellaCount = umbrellaCount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
