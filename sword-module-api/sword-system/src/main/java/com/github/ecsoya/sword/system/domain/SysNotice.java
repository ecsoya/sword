package com.github.ecsoya.sword.system.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * 通知公告表 sys_notice
 *
 * @author Jin Liu (angryred@qq.com)
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = { "searchValue" })
public class SysNotice extends BaseEntity {
	private static final long serialVersionUID = 1L;

	public static final String NOTICE_TYPE_INFORM = "1"; // 通知
	public static final String NOTICE_TYPE_NOTICE = "2"; // 公告
	public static final String NOTICE_TYPE_ARTICAL = "3"; // 文章

	public static final String STATUS_NORMAL = "0"; // 正常
	public static final String STATUS_CLOSED = "1"; // 关闭

	public static final String STICK_OFF = "0"; // 普通
	public static final String STICK_ON = "1"; // 置顶

	/** 公告ID */
	private Long noticeId;

	/** 公告标题 */
	private String noticeTitle;

	/** 公告类型（1通知 2公告） */
	private String noticeType;

	/** 公告内容 */
	private String noticeContent;

	/** 公告状态（0正常 1关闭） */
	private String status;

	public Long getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(Long noticeId) {
		this.noticeId = noticeId;
	}

	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	@NotBlank(message = "公告标题不能为空")
	@Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
	public String getNoticeTitle() {
		return noticeTitle;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	public String getNoticeContent() {
		return noticeContent;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

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
