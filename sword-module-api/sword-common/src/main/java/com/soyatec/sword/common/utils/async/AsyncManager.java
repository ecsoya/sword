package com.soyatec.sword.common.utils.async;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.soyatec.sword.common.utils.Threads;
import com.soyatec.sword.common.utils.spring.SpringUtils;

/**
 * 异步任务管理器
 * 
 * @author liuhulu
 */
public class AsyncManager {
	/**
	 * 操作延迟10毫秒
	 */
	private final int OPERATE_DELAY_TIME = 10;

	/**
	 * 异步操作任务调度线程池
	 */
	private ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

	/**
	 * 单例模式
	 */
	private AsyncManager() {
	}

	private static AsyncManager me = new AsyncManager();

	public static AsyncManager me() {
		return me;
	}

	/**
	 * 执行任务
	 * 
	 * @param task 任务
	 */
	public void execute(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	public void execute(Runnable command) {
		execute(command, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	public void execute(Runnable command, long delay, TimeUnit timeUnit) {
		if (command == null) {
			return;
		}
		if (executor == null) {
			command.run();
		} else {
			executor.schedule(command, delay, timeUnit);
		}
	}

	/**
	 * 停止任务线程池
	 */
	public void shutdown() {
		Threads.shutdownAndAwaitTermination(executor);
	}
}
