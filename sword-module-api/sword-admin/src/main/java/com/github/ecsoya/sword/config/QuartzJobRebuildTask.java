package com.github.ecsoya.sword.config;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.quartz.domain.SysJob;
import com.github.ecsoya.sword.quartz.mapper.SysJobMapper;
import com.github.ecsoya.sword.quartz.util.ScheduleUtils;

@Configuration
@EnableScheduling
public class QuartzJobRebuildTask {

	private static final Logger log = LoggerFactory.getLogger(QuartzJobRebuildTask.class);

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private SysJobMapper jobMapper;

	@Scheduled(fixedRate = 20 * 60 * 1000)
	private void configureTasks() {
		if (SpringUtils.testProfile("local")) {
			return;
		}
		log.info("Job recreator");
		try {
			final List<SysJob> jobList = jobMapper.selectJobAll();
			for (final SysJob job : jobList) {
				final Long jobId = job.getJobId();
				final String jobGroup = job.getJobGroup();
				final JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
				if (scheduler.checkExists(jobKey)) {
					log.info("Job exist: " + jobKey);
					continue;
				}
				ScheduleUtils.createScheduleJob(scheduler, job);
				log.info("Job rebuild: " + jobKey);
			}
		} catch (final Exception e) {
			log.error("Job rebuild failed: " + e.getLocalizedMessage());
		}
	}
}