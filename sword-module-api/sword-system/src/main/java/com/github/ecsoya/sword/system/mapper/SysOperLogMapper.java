package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysOperLog;

/**
 * The Interface SysOperLogMapper.
 */
public interface SysOperLogMapper {

	/**
	 * Insert operlog.
	 *
	 * @param operLog the oper log
	 */
	public void insertOperlog(SysOperLog operLog);

	/**
	 * Select oper log list.
	 *
	 * @param operLog the oper log
	 * @return the list
	 */
	public List<SysOperLog> selectOperLogList(SysOperLog operLog);

	/**
	 * Delete oper log by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteOperLogByIds(String[] ids);

	/**
	 * Select oper log by id.
	 *
	 * @param operId the oper id
	 * @return the sys oper log
	 */
	public SysOperLog selectOperLogById(Long operId);

	/**
	 * Clean oper log.
	 */
	public void cleanOperLog();
}
