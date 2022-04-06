package com.github.ecsoya.sword.task.service;

import java.util.Date;
import java.util.List;

import com.github.ecsoya.sword.task.domain.SwordTask;

/**
 * The Interface ISwordTaskService.
 */
public interface ISwordTaskService {

	/**
	 * Select sword task by id.
	 *
	 * @param id the id
	 * @return the sword task
	 */
	public SwordTask selectSwordTaskById(Long id);

	/**
	 * Select sword task list.
	 *
	 * @param swordTask the sword task
	 * @return the list
	 */
	public List<SwordTask> selectSwordTaskList(SwordTask swordTask);

	/**
	 * Insert sword task.
	 *
	 * @param swordTask the sword task
	 * @return the int
	 */
	public int insertSwordTask(SwordTask swordTask);

	/**
	 * Update sword task.
	 *
	 * @param swordTask the sword task
	 * @return the int
	 */
	public int updateSwordTask(SwordTask swordTask);

	/**
	 * Delete sword task by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteSwordTaskByIds(String ids);

	/**
	 * Delete sword task by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteSwordTaskById(Long id);

	/**
	 * Select sword task by name.
	 *
	 * @param date the date
	 * @param name the name
	 * @return the sword task
	 */
	public SwordTask selectSwordTaskByName(Date date, String name);
}
