package com.github.ecsoya.sword.quartz.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.common.constant.ScheduleConstants;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.job.TaskException;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.quartz.domain.SysJob;
import com.github.ecsoya.sword.quartz.mapper.SysJobMapper;
import com.github.ecsoya.sword.quartz.service.ISysJobService;
import com.github.ecsoya.sword.quartz.util.CronUtils;
import com.github.ecsoya.sword.quartz.util.ScheduleUtils;

/**
 * The Class SysJobServiceImpl.
 */
@Service
public class SysJobServiceImpl implements ISysJobService {

	/** The scheduler. */
	@Autowired
	private Scheduler scheduler;

	/** The job mapper. */
	@Autowired
	private SysJobMapper jobMapper;

	/**
	 * Inits the.
	 *
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	@PostConstruct
	public void init() throws SchedulerException, TaskException {
		if (SpringUtils.testProfile("local")) {
			return;
		}
		scheduler.clear();
		final List<SysJob> jobList = jobMapper.selectJobAll();
		for (final SysJob job : jobList) {
			ScheduleUtils.createScheduleJob(scheduler, job);
		}
	}

	/**
	 * Select job list.
	 *
	 * @param job the job
	 * @return the list
	 */
	@Override
	public List<SysJob> selectJobList(SysJob job) {
		return jobMapper.selectJobList(job);
	}

	/**
	 * Select job by id.
	 *
	 * @param jobId the job id
	 * @return the sys job
	 */
	@Override
	public SysJob selectJobById(Long jobId) {
		return jobMapper.selectJobById(jobId);
	}

	/**
	 * Pause job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	@Override
	@Transactional
	public int pauseJob(SysJob job) throws SchedulerException {
		final Long jobId = job.getJobId();
		final String jobGroup = job.getJobGroup();
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		final int rows = jobMapper.updateJob(job);
		if (rows > 0) {
			scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return rows;
	}

	/**
	 * Resume job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	@Override
	@Transactional
	public int resumeJob(SysJob job) throws SchedulerException {
		final Long jobId = job.getJobId();
		final String jobGroup = job.getJobGroup();
		job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
		final int rows = jobMapper.updateJob(job);
		if (rows > 0) {
			scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return rows;
	}

	/**
	 * Delete job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	@Override
	@Transactional
	public int deleteJob(SysJob job) throws SchedulerException {
		final Long jobId = job.getJobId();
		final String jobGroup = job.getJobGroup();
		final int rows = jobMapper.deleteJobById(jobId);
		if (rows > 0) {
			scheduler.deleteJob(ScheduleUtils.getJobKey(jobId, jobGroup));
		}
		return rows;
	}

	/**
	 * Delete job by ids.
	 *
	 * @param ids the ids
	 * @throws SchedulerException the scheduler exception
	 */
	@Override
	@Transactional
	public void deleteJobByIds(String ids) throws SchedulerException {
		final Long[] jobIds = Convert.toLongArray(ids);
		for (final Long jobId : jobIds) {
			final SysJob job = jobMapper.selectJobById(jobId);
			deleteJob(job);
		}
	}

	/**
	 * Change status.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 */
	@Override
	@Transactional
	public int changeStatus(SysJob job) throws SchedulerException {
		int rows = 0;
		final String status = job.getStatus();
		if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
			rows = resumeJob(job);
		} else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
			rows = pauseJob(job);
		}
		return rows;
	}

	/**
	 * Run.
	 *
	 * @param job the job
	 * @throws SchedulerException the scheduler exception
	 */
	@Override
	@Transactional
	public void run(SysJob job) throws SchedulerException {
		final Long jobId = job.getJobId();
		final SysJob tmpObj = selectJobById(job.getJobId());
		// 参数
		final JobDataMap dataMap = new JobDataMap();
		dataMap.put(ScheduleConstants.TASK_PROPERTIES, tmpObj);
		scheduler.triggerJob(ScheduleUtils.getJobKey(jobId, tmpObj.getJobGroup()), dataMap);
	}

	/**
	 * Insert job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	@Override
	@Transactional
	public int insertJob(SysJob job) throws SchedulerException, TaskException {
		job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
		final int rows = jobMapper.insertJob(job);
		if (rows > 0) {
			ScheduleUtils.createScheduleJob(scheduler, job);
		}
		return rows;
	}

	/**
	 * Update job.
	 *
	 * @param job the job
	 * @return the int
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	@Override
	@Transactional
	public int updateJob(SysJob job) throws SchedulerException, TaskException {
		final SysJob properties = selectJobById(job.getJobId());
		final int rows = jobMapper.updateJob(job);
		if (rows > 0) {
			updateSchedulerJob(job, properties.getJobGroup());
		}
		return rows;
	}

	/**
	 * Update scheduler job.
	 *
	 * @param job      the job
	 * @param jobGroup the job group
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	public void updateSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
		final Long jobId = job.getJobId();
		// 判断是否存在
		final JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
		if (scheduler.checkExists(jobKey)) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(jobKey);
		}
		ScheduleUtils.createScheduleJob(scheduler, job);
	}

	/**
	 * Check cron expression is valid.
	 *
	 * @param cronExpression the cron expression
	 * @return true, if successful
	 */
	@Override
	public boolean checkCronExpressionIsValid(String cronExpression) {
		return CronUtils.isValid(cronExpression);
	}
}