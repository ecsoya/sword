package com.github.ecsoya.sword.quartz.util;

import java.text.ParseException;
import java.util.Date;

import org.quartz.CronExpression;

/**
 * The Class CronUtils.
 */
public class CronUtils {

	/**
	 * Checks if is valid.
	 *
	 * @param cronExpression the cron expression
	 * @return true, if is valid
	 */
	public static boolean isValid(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}

	/**
	 * Gets the invalid message.
	 *
	 * @param cronExpression the cron expression
	 * @return the invalid message
	 */
	public static String getInvalidMessage(String cronExpression) {
		try {
			new CronExpression(cronExpression);
			return null;
		} catch (final ParseException pe) {
			return pe.getMessage();
		}
	}

	/**
	 * Gets the next execution.
	 *
	 * @param cronExpression the cron expression
	 * @return the next execution
	 */
	public static Date getNextExecution(String cronExpression) {
		try {
			final CronExpression cron = new CronExpression(cronExpression);
			return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));
		} catch (final ParseException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
}
