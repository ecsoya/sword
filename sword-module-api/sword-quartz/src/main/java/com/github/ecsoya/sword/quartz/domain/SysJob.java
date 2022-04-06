package com.github.ecsoya.sword.quartz.domain;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.ecsoya.sword.common.annotation.Excel;
import com.github.ecsoya.sword.common.annotation.Excel.ColumnType;
import com.github.ecsoya.sword.common.constant.ScheduleConstants;
import com.github.ecsoya.sword.common.core.domain.BaseEntity;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.quartz.util.CronUtils;

/**
 * The Class SysJob.
 */
public class SysJob extends BaseEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The job id. */
	@Excel(name = "任务序号", cellType = ColumnType.NUMERIC)
	private Long jobId;

	/** The job name. */
	@Excel(name = "任务名称")
	private String jobName;

	/** The job group. */
	@Excel(name = "任务组名")
	private String jobGroup;

	/** The invoke target. */
	@Excel(name = "调用目标字符串")
	private String invokeTarget;

	/** The cron expression. */
	@Excel(name = "执行表达式 ")
	private String cronExpression;

	/** The misfire policy. */
	@Excel(name = "计划策略 ", readConverterExp = "0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行")
	private String misfirePolicy = ScheduleConstants.MISFIRE_DEFAULT;

	/** The concurrent. */
	@Excel(name = "并发执行", readConverterExp = "0=允许,1=禁止")
	private String concurrent;

	/** The status. */
	@Excel(name = "任务状态", readConverterExp = "0=正常,1=暂停")
	private String status;

	/**
	 * Gets the 任务ID.
	 *
	 * @return the 任务ID
	 */
	public Long getJobId() {
		return jobId;
	}

	/**
	 * Sets the 任务ID.
	 *
	 * @param jobId the new 任务ID
	 */
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the 任务名称.
	 *
	 * @return the 任务名称
	 */
	@NotBlank(message = "任务名称不能为空")
	@Size(min = 0, max = 64, message = "任务名称不能超过64个字符")
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
	@NotBlank(message = "调用目标字符串不能为空")
	@Size(min = 0, max = 1000, message = "调用目标字符串长度不能超过500个字符")
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
	 * Gets the cron执行表达式.
	 *
	 * @return the cron执行表达式
	 */
	@NotBlank(message = "Cron执行表达式不能为空")
	@Size(min = 0, max = 255, message = "Cron执行表达式不能超过255个字符")
	public String getCronExpression() {
		return cronExpression;
	}

	/**
	 * Sets the cron执行表达式.
	 *
	 * @param cronExpression the new cron执行表达式
	 */
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * Gets the next valid time.
	 *
	 * @return the next valid time
	 */
	public Date getNextValidTime() {
		if (StringUtils.isNotEmpty(cronExpression)) {
			return CronUtils.getNextExecution(cronExpression);
		}
		return null;
	}

	/**
	 * Gets the cron计划策略.
	 *
	 * @return the cron计划策略
	 */
	public String getMisfirePolicy() {
		return misfirePolicy;
	}

	/**
	 * Sets the cron计划策略.
	 *
	 * @param misfirePolicy the new cron计划策略
	 */
	public void setMisfirePolicy(String misfirePolicy) {
		this.misfirePolicy = misfirePolicy;
	}

	/**
	 * Gets the 是否并发执行（0允许 1禁止）.
	 *
	 * @return the 是否并发执行（0允许 1禁止）
	 */
	public String getConcurrent() {
		return concurrent;
	}

	/**
	 * Sets the 是否并发执行（0允许 1禁止）.
	 *
	 * @param concurrent the new 是否并发执行（0允许 1禁止）
	 */
	public void setConcurrent(String concurrent) {
		this.concurrent = concurrent;
	}

	/**
	 * Gets the 任务状态（0正常 1暂停）.
	 *
	 * @return the 任务状态（0正常 1暂停）
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the 任务状态（0正常 1暂停）.
	 *
	 * @param status the new 任务状态（0正常 1暂停）
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("jobId", getJobId())
				.append("jobName", getJobName()).append("jobGroup", getJobGroup())
				.append("cronExpression", getCronExpression()).append("nextValidTime", getNextValidTime())
				.append("misfirePolicy", getMisfirePolicy()).append("concurrent", getConcurrent())
				.append("status", getStatus()).append("createBy", getCreateBy()).append("createTime", getCreateTime())
				.append("updateBy", getUpdateBy()).append("updateTime", getUpdateTime()).append("remark", getRemark())
				.toString();
	}
}