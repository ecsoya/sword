package com.github.ecsoya.sword.business.task;

import java.util.Date;

import com.github.ecsoya.sword.business.domain.TaskRunner;
import com.github.ecsoya.sword.exceptions.TransactionException;

/**
 * The Interface IProfitTask.
 */
public interface IProfitTask {

	/**
	 * Run.
	 *
	 * @param tasks the tasks
	 * @throws TransactionException the transaction exception
	 */
	void run(TaskRunner[] tasks) throws TransactionException;

	/**
	 * Run.
	 *
	 * @param tasks the tasks
	 * @param date  the date
	 * @throws TransactionException the transaction exception
	 */
	void run(TaskRunner[] tasks, Date date) throws TransactionException;
}
