package com.github.ecsoya.sword.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.github.ecsoya.sword.common.exception.job.TaskException;
import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * The Interface ISysJobService.
 */
public interface ISysJobService {

	/**
	 * Select job list.
	 *
	 * @param job the job
	 * @return the list
	 */
	public List<SysJob> selectJobList(SysJob job);

	/**
	 * Select job by id.
	 *
	 * @param jobId the job id
	 * @return the sys job
	 */
	public SysJob selectJobById(Long jobId);

	/**
	 * Pause job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	public int pauseJob(SysJob job) throws SchedulerException;

	/**
	 * Resume job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	public int resumeJob(SysJob job) throws SchedulerException;

	/**
	 * Delete job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	public int deleteJob(SysJob job) throws SchedulerException;

	/**
	 * Delete job by ids.
	 *
	 * @param ids the ids
	 * @throws SchedulerException the scheduler exception
	 */
	public void deleteJobByIds(String ids) throws SchedulerException;

	/**
	 * Change status.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	public int changeStatus(SysJob job) throws SchedulerException;

	/**
	 * Run.
	 *
	 * @param job the job
	 * @throws SchedulerException the scheduler exception
	 */
	public void run(SysJob job) throws SchedulerException;

	/**
	 * Insert job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	public int insertJob(SysJob job) throws SchedulerException, TaskException;

	/**
	 * Update job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	public int updateJob(SysJob job) throws SchedulerException, TaskException;

	/**
	 * Check cron expression is valid.
	 *
	 * @param cronExpression the cron expression
	 * @return true, if successful
	 */
	public boolean checkCronExpressionIsValid(String cronExpression);
}