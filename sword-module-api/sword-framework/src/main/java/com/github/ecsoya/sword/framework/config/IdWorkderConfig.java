package com.github.ecsoya.sword.framework.config;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.incrementer.DefaultIdentifierGenerator;

@Configuration
public class IdWorkderConfig {

	private static final Logger log = LoggerFactory.getLogger(IdWorkderConfig.class);

	@PostConstruct
	public void init() {
		final Long workId = getWorkId();
		final Long dataCenterId = getDataCenterId();
		log.info("IdWorkderConfig workId={} and dataCenterId={}", workId, dataCenterId);
		IdWorker.setIdentifierGenerator(new DefaultIdentifierGenerator(workId, dataCenterId));
	}

	private static Long getWorkId() {
		return RandomUtils.nextLong(0, 31);
	}

	private static long getDataCenterId() {
		return RandomUtils.nextLong(0, 31);
	}
}