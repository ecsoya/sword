package com.github.ecsoya.sword.business.task.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.business.domain.TaskRunner;
import com.github.ecsoya.sword.business.service.IProfitService;
import com.github.ecsoya.sword.business.task.IProfitTask;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.constants.IConstants;
import com.github.ecsoya.sword.constants.IMiningConstants;
import com.github.ecsoya.sword.exceptions.TransactionException;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.task.domain.SwordTask;
import com.github.ecsoya.sword.task.service.ISwordTaskService;

/**
 * The Class ProfitTaskImpl.
 */
@Service
public class ProfitTaskImpl implements IProfitTask {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(IProfitTask.class);

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/** The task service. */
	@Autowired
	private ISwordTaskService taskService;

	/**
	 * Run.
	 *
	 * @param tasks the tasks
	 * @throws TransactionException the transaction exception
	 */
	@Override
	public void run(TaskRunner[] tasks) throws TransactionException {
		if (!configService.checkBooleanConfigValue(IMiningConstants.USER_PROFIT_ENABLED)) {
			log.info("Sword > 收益已被关闭");
			return;
		}
		run(tasks, DateUtils.getLastDayEnd());
	}

	/**
	 * Run.
	 *
	 * @param tasks the tasks
	 * @param date  the date
	 * @throws TransactionException the transaction exception
	 */
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

	/**
	 * Schedule task.
	 *
	 * @param task   the task
	 * @param runner the runner
	 * @param date   the date
	 * @return the int
	 */
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
