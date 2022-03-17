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
 * 用户双区树对象 t_user_binary_tree
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-05
 */
public class UserBinaryTree extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/** 用户Id */
	private Long userId;

	/** 左区ID */
	@Excel(name = "左区ID")
	private Long leftId;

	/** 左区时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "左区时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date leftTime;

	/** 右区ID */
	@Excel(name = "右区ID")
	private Long rightId;

	/** 右区时间 */
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Excel(name = "右区时间", width = 30, dateFormat = "yyyy-MM-dd")
	private Date rightTime;

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setLeftId(Long leftId) {
		this.leftId = leftId;
	}

	public Long getLeftId() {
		return leftId;
	}

	public void setLeftTime(Date leftTime) {
		this.leftTime = leftTime;
	}

	public Date getLeftTime() {
		return leftTime;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public Long getRightId() {
		return rightId;
	}

	public void setRightTime(Date rightTime) {
		this.rightTime = rightTime;
	}

	public Date getRightTime() {
		return rightTime;
	}

	public boolean isLeftEmpty() {
		return isEmpty(leftId);
	}

	public boolean isRightEmpty() {
		return isEmpty(rightId);
	}

	private boolean isEmpty(Long id) {
		return id == null || 0l == id;
	}

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

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("userId", getUserId())
				.append("leftId", getLeftId()).append("leftTime", getLeftTime()).append("rightId", getRightId())
				.append("rightTime", getRightTime()).append("createTime", getCreateTime())
				.append("updateTime", getUpdateTime()).append("remark", getRemark()).toString();
	}
}
