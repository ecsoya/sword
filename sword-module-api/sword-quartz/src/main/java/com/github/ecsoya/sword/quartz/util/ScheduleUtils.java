package com.github.ecsoya.sword.quartz.util;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.github.ecsoya.sword.common.constant.ScheduleConstants;
import com.github.ecsoya.sword.common.exception.job.TaskException;
import com.github.ecsoya.sword.common.exception.job.TaskException.Code;
import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * The Class ScheduleUtils.
 */
public class ScheduleUtils {

	/**
	 * Gets the quartz job class.
	 *
	 * @param sysJob the sys job
	 * @return the quartz job class
	 */
	private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
		final boolean isConcurrent = "0".equals(sysJob.getConcurrent());
		return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
	}

	/**
	 * Gets the trigger key.
	 *
	 * @param jobId    the job id
	 * @param jobGroup the job group
	 * @return the trigger key
	 */
	public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
		return TriggerKey.triggerKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * Gets the job key.
	 *
	 * @param jobId    the job id
	 * @param jobGroup the job group
	 * @return the job key
	 */
	public static JobKey getJobKey(Long jobId, String jobGroup) {
		return JobKey.jobKey(ScheduleConstants.TASK_CLASS_NAME + jobId, jobGroup);
	}

	/**
	 * Creates the schedule job.
	 *
	 * @param scheduler the scheduler
	 * @param job       the job
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	public static void createScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
		final Class<? extends Job> jobClass = getQuartzJobClass(job);
		// 构建job信息
		final Long jobId = job.getJobId();
		final String jobGroup = job.getJobGroup();
		final JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

		// 表达式调度构建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
		cronScheduleBuilder = handleCronScheduleMisfirePolicy(job, cronScheduleBuilder);

		// 按新的cronExpression表达式构建一个新的trigger
		final CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup))
				.withSchedule(cronScheduleBuilder).build();

		// 放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);

		// 判断是否存在
		if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(getJobKey(jobId, jobGroup));
		}

		scheduler.scheduleJob(jobDetail, trigger);

		// 暂停任务
		if (job.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
	}

	/**
	 * Handle cron schedule misfire policy.
	 *
	 * @param job the job
	 * @param cb  the cb
	 * @return the cron schedule builder
	 * @throws TaskException the task exception
	 */
	public static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb)
			throws TaskException {
		switch (job.getMisfirePolicy()) {
		case ScheduleConstants.MISFIRE_DEFAULT:
			return cb;
		case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
			return cb.withMisfireHandlingInstructionIgnoreMisfires();
		case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
			return cb.withMisfireHandlingInstructionFireAndProceed();
		case ScheduleConstants.MISFIRE_DO_NOTHING:
			return cb.withMisfireHandlingInstructionDoNothing();
		default:
			throw new TaskException(
					"The task misfire policy '" + job.getMisfirePolicy() + "' cannot be used in cron schedule tasks",
					Code.CONFIG_ERROR);
		}
	}
}