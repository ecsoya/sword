package com.github.ecsoya.sword.quartz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.quartz.domain.SysJobLog;
import com.github.ecsoya.sword.quartz.mapper.SysJobLogMapper;
import com.github.ecsoya.sword.quartz.service.ISysJobLogService;

/**
 * The Class SysJobLogServiceImpl.
 */
@Service
public class SysJobLogServiceImpl implements ISysJobLogService {

	/** The job log mapper. */
	@Autowired
	private SysJobLogMapper jobLogMapper;

	/**
	 * Select job log list.
	 *
	 * @param jobLog the job log
	 * @return the list
	 */
	@Override
	public List<SysJobLog> selectJobLogList(SysJobLog jobLog) {
		return jobLogMapper.selectJobLogList(jobLog);
	}

	/**
	 * Select job log by id.
	 *
	 * @param jobLogId the job log id
	 * @return the sys job log
	 */
	@Override
	public SysJobLog selectJobLogById(Long jobLogId) {
		return jobLogMapper.selectJobLogById(jobLogId);
	}

	/**
	 * Adds the job log.
	 *
	 * @param jobLog the job log
	 */
	@Override
	public void addJobLog(SysJobLog jobLog) {
		jobLogMapper.insertJobLog(jobLog);
	}

	/**
	 * Delete job log by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteJobLogByIds(String ids) {
		return jobLogMapper.deleteJobLogByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete job log by id.
	 *
	 * @param jobId the job id
	 * @return the int
	 */
	@Override
	public int deleteJobLogById(Long jobId) {
		return jobLogMapper.deleteJobLogById(jobId);
	}

	/**
	 * Clean job log.
	 */
	@Override
	public void cleanJobLog() {
		jobLogMapper.cleanJobLog();
	}
}
