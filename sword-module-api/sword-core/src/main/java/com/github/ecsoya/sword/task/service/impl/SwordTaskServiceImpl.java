package com.github.ecsoya.sword.task.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IConstants;
import com.github.ecsoya.sword.task.domain.SwordTask;
import com.github.ecsoya.sword.task.mapper.SwordTaskMapper;
import com.github.ecsoya.sword.task.service.ISwordTaskService;

/**
 * The Class SwordTaskServiceImpl.
 */
@Service
public class SwordTaskServiceImpl implements ISwordTaskService {

	/** The sword task mapper. */
	@Autowired
	private SwordTaskMapper swordTaskMapper;

	/**
	 * Select sword task by id.
	 *
	 * @param id the id
	 * @return the sword task
	 */
	@Override
	public SwordTask selectSwordTaskById(Long id) {
		return swordTaskMapper.selectSwordTaskById(id);
	}

	/**
	 * Select sword task list.
	 *
	 * @param swordTask the sword task
	 * @return the list
	 */
	@Override
	public List<SwordTask> selectSwordTaskList(SwordTask swordTask) {
		return swordTaskMapper.selectSwordTaskList(swordTask);
	}

	/**
	 * Insert sword task.
	 *
	 * @param swordTask the sword task
	 * @return the int
	 */
	@Override
	public int insertSwordTask(SwordTask swordTask) {
		if (swordTask.getId() == null) {
			swordTask.setId(IdWorker.getId());
		}
		if (swordTask.getCreateTime() == null) {
			swordTask.setCreateTime(DateUtils.getNowDate());
		}
		return swordTaskMapper.insertSwordTask(swordTask);
	}

	/**
	 * Update sword task.
	 *
	 * @param swordTask the sword task
	 * @return the int
	 */
	@Override
	public int updateSwordTask(SwordTask swordTask) {
		swordTask.setUpdateTime(DateUtils.getNowDate());
		return swordTaskMapper.updateSwordTask(swordTask);
	}

	/**
	 * Delete sword task by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteSwordTaskByIds(String ids) {
		return swordTaskMapper.deleteSwordTaskByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete sword task by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteSwordTaskById(Long id) {
		return swordTaskMapper.deleteSwordTaskById(id);
	}

	/**
	 * Select sword task by name.
	 *
	 * @param date the date
	 * @param name the name
	 * @return the sword task
	 */
	@Override
	public SwordTask selectSwordTaskByName(Date date, String name) {
		if (date == null || StringUtils.isEmpty(name)) {
			return null;
		}
		SwordTask task = swordTaskMapper.selectSwordTaskByName(date, name);
		if (task == null) {
			task = new SwordTask();
			task.setDate(date);
			task.setName(name);
			task.setStatus(IConstants.STATUS_NONE);
			insertSwordTask(task);
		}
		return task;
	}
}
