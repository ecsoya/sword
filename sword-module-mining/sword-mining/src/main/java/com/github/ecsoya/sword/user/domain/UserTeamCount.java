package com.github.ecsoya.sword.user.domain;

/**
 * 用户直推及伞下人数
 *
 * @author ecsoya
 */
public class UserTeamCount {

	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 直推人数
	 */
	private Long referrerCount;

	/**
	 * 伞下人数
	 */
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

	@Override
	public String toString() {
		return "userId=" + userId + ", referrerCount=" + referrerCount + ", umbrellaCount=" + umbrellaCount;
	}

}
