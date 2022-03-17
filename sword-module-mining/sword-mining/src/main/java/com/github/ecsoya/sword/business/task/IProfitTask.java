package com.github.ecsoya.sword.business.task;

import java.util.Date;

import com.github.ecsoya.sword.business.domain.TaskRunner;
import com.github.ecsoya.sword.exceptions.TransactionException;

public interface IProfitTask {

	void run(TaskRunner[] tasks) throws TransactionException;

	void run(TaskRunner[] tasks, Date date) throws TransactionException;
}
