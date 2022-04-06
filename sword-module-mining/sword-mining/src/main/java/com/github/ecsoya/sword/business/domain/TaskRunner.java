package com.github.ecsoya.sword.business.domain;

import com.github.ecsoya.sword.business.service.IProfitService;

/**
 * The Class TaskRunner.
 */
public class TaskRunner {

	/** The task name. */
	private String taskName;

	/** The task runner. */
	private IProfitService taskRunner;

	/**
	 * Instantiates a new task runner.
	 *
	 * @param taskName   the task name
	 * @param taskRunner the task runner
	 */
	public TaskRunner(String taskName, IProfitService taskRunner) {
		this.taskName = taskName;
		this.taskRunner = taskRunner;
	}

	/**
	 * Gets the task name.
	 *
	 * @return the task name
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * Sets the task name.
	 *
	 * @param taskName the new task name
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * Gets the task runner.
	 *
	 * @return the task runner
	 */
	public IProfitService getTaskRunner() {
		return taskRunner;
	}

	/**
	 * Sets the task runner.
	 *
	 * @param taskRunner the new task runner
	 */
	public void setTaskRunner(IProfitService taskRunner) {
		this.taskRunner = taskRunner;
	}
}
