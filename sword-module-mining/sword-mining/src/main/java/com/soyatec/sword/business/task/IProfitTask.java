package com.soyatec.sword.business.task;

import java.util.Date;

import com.soyatec.sword.business.domain.TaskRunner;
import com.soyatec.sword.exceptions.TransactionException;

public interface IProfitTask {

	void run(TaskRunner[] tasks) throws TransactionException;

	void run(TaskRunner[] tasks, Date date) throws TransactionException;
}
