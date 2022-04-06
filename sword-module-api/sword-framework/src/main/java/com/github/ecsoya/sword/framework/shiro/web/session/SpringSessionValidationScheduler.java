package com.github.ecsoya.sword.framework.shiro.web.session;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.Threads;

/**
 * The Class SpringSessionValidationScheduler.
 */
@Component
public class SpringSessionValidationScheduler implements SessionValidationScheduler {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SpringSessionValidationScheduler.class);

	/** The Constant DEFAULT_SESSION_VALIDATION_INTERVAL. */
	public static final long DEFAULT_SESSION_VALIDATION_INTERVAL = DefaultSessionManager.DEFAULT_SESSION_VALIDATION_INTERVAL;

	/** The executor service. */
	@Autowired
	@Qualifier("scheduledExecutorService")
	private ScheduledExecutorService executorService;

	/** The enabled. */
	private volatile boolean enabled = false;

	/** The session manager. */
	@Autowired
	@Qualifier("sessionManager")
	@Lazy
	private ValidatingSessionManager sessionManager;

	/** The session validation interval. */
	// 相隔多久检查一次session的有效性，单位毫秒，默认就是10分钟
	@Value("${shiro.session.validationInterval}")
	private long sessionValidationInterval;

	/**
	 * Checks if is enabled.
	 *
	 * @return the enabled
	 */
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * Sets the session validation interval.
	 *
	 * @param sessionValidationInterval the new session validation interval
	 */
	public void setSessionValidationInterval(long sessionValidationInterval) {
		this.sessionValidationInterval = sessionValidationInterval;
	}

	/**
	 * Enable session validation.
	 */
	@Override
	public void enableSessionValidation() {

		enabled = true;

		if (log.isDebugEnabled()) {
			log.debug("Scheduling session validation job using Spring Scheduler with "
					+ "session validation interval of [" + sessionValidationInterval + "]ms...");
		}

		try {
			executorService.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					if (enabled) {
						sessionManager.validateSessions();
					}
				}
			}, 1000, sessionValidationInterval * 60 * 1000, TimeUnit.MILLISECONDS);

			this.enabled = true;

			if (log.isDebugEnabled()) {
				log.debug("Session validation job successfully scheduled with Spring Scheduler.");
			}

		} catch (final Exception e) {
			if (log.isErrorEnabled()) {
				log.error(
						"Error starting the Spring Scheduler session validation job.  Session validation may not occur.",
						e);
			}
		}
	}

	/**
	 * Disable session validation.
	 */
	@Override
	public void disableSessionValidation() {
		if (log.isDebugEnabled()) {
			log.debug("Stopping Spring Scheduler session validation job...");
		}

		if (this.enabled) {
			Threads.shutdownAndAwaitTermination(executorService);
		}
		this.enabled = false;
	}
}
