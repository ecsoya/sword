package com.soyatec.sword.task.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.constants.IConstants;
import com.soyatec.sword.task.domain.SwordTask;
import com.soyatec.sword.task.mapper.SwordTaskMapper;
import com.soyatec.sword.task.service.ISwordTaskService;

/**
 * 定时任务执行结果Service业务层处理
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-06
 */
@Service
public class SwordTaskServiceImpl implements ISwordTaskService {
	@Autowired
	private SwordTaskMapper swordTaskMapper;

	/**
	 * 查询定时任务执行结果
	 *
	 * @param id 定时任务执行结果ID
	 * @return 定时任务执行结果
	 */
	@Override
	public SwordTask selectSwordTaskById(Long id) {
		return swordTaskMapper.selectSwordTaskById(id);
	}

	/**
	 * 查询定时任务执行结果列表
	 *
	 * @param swordTask 定时任务执行结果
	 * @return 定时任务执行结果
	 */
	@Override
	public List<SwordTask> selectSwordTaskList(SwordTask swordTask) {
		return swordTaskMapper.selectSwordTaskList(swordTask);
	}

	/**
	 * 新增定时任务执行结果
	 *
	 * @param swordTask 定时任务执行结果
	 * @return 结果
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
	 * 修改定时任务执行结果
	 *
	 * @param swordTask 定时任务执行结果
	 * @return 结果
	 */
	@Override
	public int updateSwordTask(SwordTask swordTask) {
		swordTask.setUpdateTime(DateUtils.getNowDate());
		return swordTaskMapper.updateSwordTask(swordTask);
	}

	/**
	 * 删除定时任务执行结果对象
	 *
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordTaskByIds(String ids) {
		return swordTaskMapper.deleteSwordTaskByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除定时任务执行结果信息
	 *
	 * @param id 定时任务执行结果ID
	 * @return 结果
	 */
	@Override
	public int deleteSwordTaskById(Long id) {
		return swordTaskMapper.deleteSwordTaskById(id);
	}

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
