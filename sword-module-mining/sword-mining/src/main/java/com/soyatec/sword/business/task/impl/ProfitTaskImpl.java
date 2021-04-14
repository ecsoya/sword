package com.soyatec.sword.business.task.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.business.domain.TaskRunner;
import com.soyatec.sword.business.service.IProfitService;
import com.soyatec.sword.business.task.IProfitTask;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.constants.IConstants;
import com.soyatec.sword.constants.IMiningConstants;
import com.soyatec.sword.exceptions.TransactionException;
import com.soyatec.sword.system.service.ISysConfigService;
import com.soyatec.sword.task.domain.SwordTask;
import com.soyatec.sword.task.service.ISwordTaskService;

@Service
public class ProfitTaskImpl implements IProfitTask {
	private static final Logger log = LoggerFactory.getLogger(IProfitTask.class);
	@Autowired
	private ISysConfigService configService;

	@Autowired
	private ISwordTaskService taskService;

	@Override
	public void run(TaskRunner[] tasks) throws TransactionException {
		if (!configService.checkBooleanConfigValue(IMiningConstants.USER_PROFIT_ENABLED)) {
			log.info("Sword > 收益已被关闭");
			return;
		}
		run(tasks, DateUtils.getLastDayEnd());
	}

	@Override
	public void run(TaskRunner[] tasks, Date date) throws TransactionException {
		log.debug("Sword > 调度任务时间：{}", DateUtils.toDefault(date));
		log.debug("Sword > 执行任务时间：{}", DateUtils.toDefault(DateUtils.getNowDate()));
		if (tasks == null || tasks.length == 0) {
			return;
		}
		for (final TaskRunner runner : tasks) {
			final String taskName = runner.getTaskName();
			if (StringUtils.isEmpty(taskName)) {
				continue;
			}
			log.debug("Sword > 处理任务：{}", taskName);
			final SwordTask task = taskService.selectSwordTaskByName(date, taskName);
			if (task == null) {
				continue; // taskName is null
			}
			if (!task.scheduled()) {
				log.debug("Sword > {} 未完成", taskName);
				scheduleTask(task, runner, date);
			} else {
				log.debug("Sword > {} 已完成", taskName);
			}
		}
	}

	private int scheduleTask(SwordTask task, TaskRunner runner, Date date) {
		if (task == null || runner == null) {
			return 0;
		}
		final String taskName = runner.getTaskName();
		log.debug("Sword > {} 开始执行", taskName);
		final IProfitService taskRunner = runner.getTaskRunner();
		int result = 0;
		if (taskRunner != null) {
			result = taskRunner.dispatchProfit(date);
		}
		if (result > 0) {
			task.setStatus(IConstants.STATUS_FINISHED);
			return taskService.updateSwordTask(task);
		}
		log.debug("Sword > {} 执行完成", taskName);
		return result;
	}

}
