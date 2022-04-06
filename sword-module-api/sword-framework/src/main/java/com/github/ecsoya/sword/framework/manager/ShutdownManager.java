package com.github.ecsoya.sword.framework.manager;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.framework.shiro.web.session.SpringSessionValidationScheduler;

/**
 * The Class ShutdownManager.
 */
@Component
public class ShutdownManager {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger("sys-user");

	/** The spring session validation scheduler. */
	@Autowired(required = false)
	private SpringSessionValidationScheduler springSessionValidationScheduler;

	/**
	 * Destroy.
	 */
	@PreDestroy
	public void destroy() {
		shutdownSpringSessionValidationScheduler();
		shutdownAsyncManager();
	}

	/**
	 * Shutdown spring session validation scheduler.
	 */
	private void shutdownSpringSessionValidationScheduler() {
		if (springSessionValidationScheduler != null && springSessionValidationScheduler.isEnabled()) {
			try {
				logger.info("====关闭会话验证任务====");
				springSessionValidationScheduler.disableSessionValidation();
			} catch (final Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * Shutdown async manager.
	 */
	private void shutdownAsyncManager() {
		try {
			logger.info("====关闭后台任务任务线程池====");
			AsyncManager.me().shutdown();
		} catch (final Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
