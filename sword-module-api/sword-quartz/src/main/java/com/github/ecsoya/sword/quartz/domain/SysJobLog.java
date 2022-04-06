package com.github.ecsoya.sword.quartz.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;

/**
 * The Class SysJobLog.
 */
public class SysJobLog extends BaseEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The job log id. */
	@Excel(name = "日志序号")
	private Long jobLogId;

	/** The job name. */
	@Excel(name = "任务名称")
	private String jobName;

	/** The job group. */
	@Excel(name = "任务组名")
	private String jobGroup;

	/** The invoke target. */
	@Excel(name = "调用目标字符串")
	private String invokeTarget;

	/** The job message. */
	@Excel(name = "日志信息")
	private String jobMessage;

	/** The status. */
	@Excel(name = "执行状态", readConverterExp = "0=正常,1=失败")
	private String status;

	/** The exception info. */
	@Excel(name = "异常信息")
	private String exceptionInfo;

	/** The start time. */
	private Date startTime;

	/** The end time. */
	private Date endTime;

	/**
	 * Gets the iD.
	 *
	 * @return the iD
	 */
	public Long getJobLogId() {
		return jobLogId;
	}

	/**
	 * Sets the iD.
	 *
	 * @param jobLogId the new iD
	 */
	public void setJobLogId(Long jobLogId) {
		this.jobLogId = jobLogId;
	}

	/**
	 * Gets the 任务名称.
	 *
	 * @return the 任务名称
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * Sets the 任务名称.
	 *
	 * @param jobName the new 任务名称
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Gets the 任务组名.
	 *
	 * @return the 任务组名
	 */
	public String getJobGroup() {
		return jobGroup;
	}

	/**
	 * Sets the 任务组名.
	 *
	 * @param jobGroup the new 任务组名
	 */
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	/**
	 * Gets the 调用目标字符串.
	 *
	 * @return the 调用目标字符串
	 */
	public String getInvokeTarget() {
		return invokeTarget;
	}

	/**
	 * Sets the 调用目标字符串.
	 *
	 * @param invokeTarget the new 调用目标字符串
	 */
	public void setInvokeTarget(String invokeTarget) {
		this.invokeTarget = invokeTarget;
	}

	/**
	 * Gets the 日志信息.
	 *
	 * @return the 日志信息
	 */
	public String getJobMessage() {
		return jobMessage;
	}

	/**
	 * Sets the 日志信息.
	 *
	 * @param jobMessage the new 日志信息
	 */
	public void setJobMessage(String jobMessage) {
		this.jobMessage = jobMessage;
	}

	/**
	 * Gets the 执行状态（0正常 1失败）.
	 *
	 * @return the 执行状态（0正常 1失败）
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 执行状态（0正常 1失败）.
	 *
	 * @param status the new 执行状态（0正常 1失败）
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the 异常信息.
	 *
	 * @return the 异常信息
	 */
	public String getExceptionInfo() {
		return exceptionInfo;
	}

	/**
	 * Sets the 异常信息.
	 *
	 * @param exceptionInfo the new 异常信息
	 */
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * Gets the 开始时间.
	 *
	 * @return the 开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * Sets the 开始时间.
	 *
	 * @param startTime the new 开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * Gets the 结束时间.
	 *
	 * @return the 结束时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * Sets the 结束时间.
	 *
	 * @param endTime the new 结束时间
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("jobLogId", getJobLogId())
				.append("jobName", getJobName()).append("jobGroup", getJobGroup()).append("jobMessage", getJobMessage())
				.append("status", getStatus()).append("exceptionInfo", getExceptionInfo())
				.append("startTime", getStartTime()).append("endTime", getEndTime()).toString();
	}
}
