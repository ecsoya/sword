package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysOperNotify;

/**
 * The Interface SysOperNotifyMapper.
 */
public interface SysOperNotifyMapper {

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
	 * Delete sys oper notify by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSysOperNotifyById(Long id);

	/**
	 * Delete sys oper notify by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSysOperNotifyByIds(String[] ids);
}
