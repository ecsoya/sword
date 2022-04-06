package com.github.ecsoya.sword.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysNotice.
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "searchValue" })
public class SysNotice extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant NOTICE_TYPE_INFORM. */
	public static final String NOTICE_TYPE_INFORM = "1"; // 通知

	/** The Constant NOTICE_TYPE_NOTICE. */
	public static final String NOTICE_TYPE_NOTICE = "2"; // 公告

	/** The Constant NOTICE_TYPE_ARTICAL. */
	public static final String NOTICE_TYPE_ARTICAL = "3"; // 文章

	/** The Constant STATUS_NORMAL. */
	public static final String STATUS_NORMAL = "0"; // 正常

	/** The Constant STATUS_CLOSED. */
	public static final String STATUS_CLOSED = "1"; // 关闭

	/** The Constant STICK_OFF. */
	public static final String STICK_OFF = "0"; // 普通

	/** The Constant STICK_ON. */
	public static final String STICK_ON = "1"; // 置顶

	/** The notice id. */
	private Long noticeId;

	/** The notice title. */
	private String noticeTitle;

	/** The notice type. */
	private String noticeType;

	/** The notice content. */
	private String noticeContent;

	/** The status. */
	private String status;

	/**
	 * Gets the 公告ID.
	 *
	 * @return the 公告ID
	 */
	public Long getNoticeId() {
		return noticeId;
	}

	/**
	 * Sets the 公告ID.
	 *
	 * @param noticeId the new 公告ID
	 */
	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * Sets the 公告标题.
	 *
	 * @param noticeTitle the new 公告标题
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * Gets the 公告标题.
	 *
	 * @return the 公告标题
	 */
	@NotBlank(message = "公告标题不能为空")
	@Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * Sets the 公告类型（1通知 2公告）.
	 *
	 * @param noticeType the new 公告类型（1通知 2公告）
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	/**
	 * Gets the 公告类型（1通知 2公告）.
	 *
	 * @return the 公告类型（1通知 2公告）
	 */
	public String getNoticeType() {
		return noticeType;
	}

	/**
	 * Sets the 公告内容.
	 *
	 * @param noticeContent the new 公告内容
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * Gets the 公告内容.
	 *
	 * @return the 公告内容
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * Sets the 公告状态（0正常 1关闭）.
	 *
	 * @param status the new 公告状态（0正常 1关闭）
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 公告状态（0正常 1关闭）.
	 *
	 * @return the 公告状态（0正常 1关闭）
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("noticeId", getNoticeId())
				.append("noticeTitle", getNoticeTitle()).append("noticeType", getNoticeType())
				.append("noticeContent", getNoticeContent()).append("status", getStatus())
				.append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}
