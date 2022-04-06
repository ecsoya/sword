package com.github.ecsoya.sword.common.utils.async;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.ecsoya.sword.common.utils.Threads;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;

/**
 * The Class AsyncManager.
 */
public class AsyncManager {

	/** The operate delay time. */
	private final int OPERATE_DELAY_TIME = 10;

	/** The executor. */
	private final ScheduledExecutorService executor = SpringUtils.getBean("scheduledExecutorService");

	/**
	 * Instantiates a new async manager.
	 */
	private AsyncManager() {
	}

	/** The me. */
	private static AsyncManager me = new AsyncManager();

	/**
	 * Me.
	 *
	 * @return the async manager
	 */
	public static AsyncManager me() {
		return me;
	}

	/**
	 * Execute.
	 *
	 * @param task the task
	 */
	public void execute(TimerTask task) {
		executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	/**
	 * Execute.
	 *
	 * @param command the command
	 */
	public void execute(Runnable command) {
		execute(command, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS);
	}

	/**
	 * Execute.
	 *
	 * @param command  the command
	 * @param delay    the delay
	 * @param timeUnit the time unit
	 */
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
	 * Shutdown.
	 */
	public void shutdown() {
		Threads.shutdownAndAwaitTermination(executor);
	}
}
