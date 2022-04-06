package com.github.ecsoya.sword.user.domain;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class UserBinaryTree.
 */
public class UserBinaryTree extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The user id. */
	private Long userId;

	/** The left id. */
	@Excel(name = "左区ID")
	private Long leftId;

	/** The left time. */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "左区时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date leftTime;

	/** The right id. */
	@Excel(name = "右区ID")
	private Long rightId;

	/** The right time. */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "右区时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date rightTime;

	/**
	 * Sets the 用户Id.
	 *
	 * @param userId the new 用户Id
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the 用户Id.
	 *
	 * @return the 用户Id
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * Sets the 左区ID.
	 *
	 * @param leftId the new 左区ID
	 */
	public void setLeftId(Long leftId) {
		this.leftId = leftId;
	}

	/**
	 * Gets the 左区ID.
	 *
	 * @return the 左区ID
	 */
	public Long getLeftId() {
		return leftId;
	}

	/**
	 * Sets the 左区时间.
	 *
	 * @param leftTime the new 左区时间
	 */
	public void setLeftTime(Date leftTime) {
		this.leftTime = leftTime;
	}

	/**
	 * Gets the 左区时间.
	 *
	 * @return the 左区时间
	 */
	public Date getLeftTime() {
		return leftTime;
	}

	/**
	 * Sets the 右区ID.
	 *
	 * @param rightId the new 右区ID
	 */
	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	/**
	 * Gets the 右区ID.
	 *
	 * @return the 右区ID
	 */
	public Long getRightId() {
		return rightId;
	}

	/**
	 * Sets the 右区时间.
	 *
	 * @param rightTime the new 右区时间
	 */
	public void setRightTime(Date rightTime) {
		this.rightTime = rightTime;
	}

	/**
	 * Gets the 右区时间.
	 *
	 * @return the 右区时间
	 */
	public Date getRightTime() {
		return rightTime;
	}

	/**
	 * Checks if is left empty.
	 *
	 * @return true, if is left empty
	 */
	public boolean isLeftEmpty() {
		return isEmpty(leftId);
	}

	/**
	 * Checks if is right empty.
	 *
	 * @return true, if is right empty
	 */
	public boolean isRightEmpty() {
		return isEmpty(rightId);
	}

	/**
	 * Checks if is empty.
	 *
	 * @param id the id
	 * @return true, if is empty
	 */
	private boolean isEmpty(Long id) {
		return id == null || 0l == id;
	}

	/**
	 * List ids.
	 *
	 * @return the list
	 */
	public List<Long> listIds() {
		final Vector<Long> ids = new Vector<Long>();
		if (!isLeftEmpty()) {
			ids.add(leftId);
		}
		if (!isRightEmpty()) {
			ids.add(rightId);
		}
		return ids;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("leftId", getLeftId()).append("leftTime", getLeftTime()).append("rightId", getRightId())
				.append("rightTime", getRightTime()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
