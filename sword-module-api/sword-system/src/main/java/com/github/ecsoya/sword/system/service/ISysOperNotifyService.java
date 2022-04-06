package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysOperLog;
import com.github.ecsoya.sword.system.domain.SysOperNotify;

/**
 * The Interface ISysOperNotifyService.
 */
public interface ISysOperNotifyService {

	/**
	 * Select sys oper notify by id.
	 *
	 * @param id the id
	 * @return the sys oper notify
	 */
	public SysOperNotify selectSysOperNotifyById(Long id);

	/**
	 * Select sys oper notify list.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the list
	 */
	public List<SysOperNotify> selectSysOperNotifyList(SysOperNotify sysOperNotify);

	/**
	 * Insert sys oper notify.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the int
	 */
	public int insertSysOperNotify(SysOperNotify sysOperNotify);

	/**
	 * Update sys oper notify.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the int
	 */
	public int updateSysOperNotify(SysOperNotify sysOperNotify);

	/**
	 * Delete sys oper notify by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSysOperNotifyByIds(String ids);

	/**
	 * Delete sys oper notify by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSysOperNotifyById(Long id);

	/**
	 * Notify by oper log.
	 *
	 * @param operLog the oper log
	 */
	public void notifyByOperLog(SysOperLog operLog);
}
