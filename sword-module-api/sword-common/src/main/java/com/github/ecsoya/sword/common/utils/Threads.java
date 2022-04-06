package com.github.ecsoya.sword.common.utils;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class Threads.
 */
public class Threads {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(Threads.class);

	/**
	 * Sleep.
	 *
	 * @param milliseconds the milliseconds
	 */
	public static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (final InterruptedException e) {
			return;
		}
	}

	/**
	 * Shutdown and await termination.
	 *
	 * @param pool the pool
	 */
	public static void shutdownAndAwaitTermination(ExecutorService pool) {
		if (pool != null && !pool.isShutdown()) {
			pool.shutdown();
			try {
				if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
					pool.shutdownNow();
					if (!pool.awaitTermination(120, TimeUnit.SECONDS)) {
						logger.info("Pool did not terminate");
					}
				}
			} catch (final InterruptedException ie) {
				pool.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
	}

	/**
	 * Prints the exception.
	 *
	 * @param r the r
	 * @param t the t
	 */
	public static void printException(Runnable r, Throwable t) {
		if (t == null && r instanceof Future<?>) {
			try {
				final Future<?> future = (Future<?>) r;
				if (future.isDone()) {
					future.get();
				}
			} catch (final CancellationException ce) {
				t = ce;
			} catch (final ExecutionException ee) {
				t = ee.getCause();
			} catch (final InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
		}
		if (t != null) {
			logger.error(t.getMessage(), t);
		}
	}
}
