package com.github.ecsoya.sword.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class SysUserPost.
 */
public class SysUserPost {

	/** The user id. */
	private Long userId;

	/** The post id. */
	private Long postId;

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
	 * Gets the 岗位ID.
	 *
	 * @return the 岗位ID
	 */
	public Long getPostId() {
		return postId;
	}

	/**
	 * Sets the 岗位ID.
	 *
	 * @param postId the new 岗位ID
	 */
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("postId", getPostId()).toString();
	}
}
