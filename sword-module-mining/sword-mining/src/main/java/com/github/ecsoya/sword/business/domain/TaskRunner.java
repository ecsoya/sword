package com.github.ecsoya.sword.business.domain;

import com.github.ecsoya.sword.business.service.IProfitService;

public class TaskRunner {

	private String taskName;

	private IProfitService taskRunner;

	public TaskRunner(String taskName, IProfitService taskRunner) {
		this.taskName = taskName;
		this.taskRunner = taskRunner;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public IProfitService getTaskRunner() {
		return taskRunner;
	}

	public void setTaskRunner(IProfitService taskRunner) {
		this.taskRunner = taskRunner;
	}
}
