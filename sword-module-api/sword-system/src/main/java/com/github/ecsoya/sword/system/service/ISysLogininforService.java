package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysLogininfor;

/**
 * The Interface ISysLogininforService.
 */
public interface ISysLogininforService {

	/**
	 * Insert logininfor.
	 *
	 * @param logininfor the logininfor
	 */
	public void insertLogininfor(SysLogininfor logininfor);

	/**
	 * Select logininfor list.
	 *
	 * @param logininfor the logininfor
	 * @return the list
	 */
	public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

	/**
	 * Delete logininfor by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteLogininforByIds(String ids);

	/**
	 * Clean logininfor.
	 */
	public void cleanLogininfor();
}
