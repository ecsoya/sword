package com.github.ecsoya.sword.framework.config;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.incrementer.DefaultIdentifierGenerator;

/**
 * The Class IdWorkderConfig.
 */
@Configuration
public class IdWorkderConfig {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(IdWorkderConfig.class);

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		final Long workId = getWorkId();
		final Long dataCenterId = getDataCenterId();
		log.info("IdWorkderConfig workId={} and dataCenterId={}", workId, dataCenterId);
		IdWorker.setIdentifierGenerator(new DefaultIdentifierGenerator(workId, dataCenterId));
	}

	/**
	 * Gets the work id.
	 *
	 * @return the work id
	 */
	private static Long getWorkId() {
		return RandomUtils.nextLong(0, 31);
	}

	/**
	 * Gets the data center id.
	 *
	 * @return the data center id
	 */
	private static long getDataCenterId() {
		return RandomUtils.nextLong(0, 31);
	}
}