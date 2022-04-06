package com.github.ecsoya.sword.quartz.mapper;

import java.util.List;

import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * The Interface SysJobMapper.
 */
public interface SysJobMapper {

	/**
	 * Select job list.
	 *
	 * @param job the job
	 * @return the list
	 */
	public List<SysJob> selectJobList(SysJob job);

	/**
	 * Select job all.
	 *
	 * @return the list
	 */
	public List<SysJob> selectJobAll();

	/**
	 * Select job by id.
	 *
	 * @param jobId the job id
	 * @return the sys job
	 */
	public SysJob selectJobById(Long jobId);

	/**
	 * Delete job by id.
	 *
	 * @param jobId the job id
	 * @return the int
	 */
	public int deleteJobById(Long jobId);

	/**
	 * Delete job by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteJobByIds(Long[] ids);

	/**
	 * Update job.
	 *
	 * @param job the job
	 * @return the int
	 */
	public int updateJob(SysJob job);

	/**
	 * Insert job.
	 *
	 * @param job the job
	 * @return the int
	 */
	public int insertJob(SysJob job);
}
