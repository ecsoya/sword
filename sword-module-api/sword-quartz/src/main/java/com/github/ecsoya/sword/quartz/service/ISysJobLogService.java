package com.github.ecsoya.sword.quartz.service;

import java.util.List;

import com.github.ecsoya.sword.quartz.domain.SysJobLog;

/**
 * The Interface ISysJobLogService.
 */
public interface ISysJobLogService {

	/**
	 * Select job log list.
	 *
	 * @param jobLog the job log
	 * @return the list
	 */
	public List<SysJobLog> selectJobLogList(SysJobLog jobLog);

	/**
	 * Select job log by id.
	 *
	 * @param jobLogId the job log id
	 * @return the sys job log
	 */
	public SysJobLog selectJobLogById(Long jobLogId);

	/**
	 * Adds the job log.
	 *
	 * @param jobLog the job log
	 */
	public void addJobLog(SysJobLog jobLog);

	/**
	 * Delete job log by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteJobLogByIds(String ids);

	/**
	 * Delete job log by id.
	 *
	 * @param jobId the job id
	 * @return the int
	 */
	public int deleteJobLogById(Long jobId);

	/**
	 * Clean job log.
	 */
	public void cleanJobLog();
}
