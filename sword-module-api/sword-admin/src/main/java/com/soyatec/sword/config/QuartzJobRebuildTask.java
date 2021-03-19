package com.soyatec.sword.config;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.quartz.domain.SysJob;
import com.soyatec.sword.quartz.mapper.SysJobMapper;
import com.soyatec.sword.quartz.util.ScheduleUtils;

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
			List<SysJob> jobList = jobMapper.selectJobAll();
			for (SysJob job : jobList) {
				Long jobId = job.getJobId();
				String jobGroup = job.getJobGroup();
				JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
				if (scheduler.checkExists(jobKey)) {
					log.info("Job exist: " + jobKey);
					continue;
				}
				ScheduleUtils.createScheduleJob(scheduler, job);
				log.info("Job rebuild: " + jobKey);
			}
		} catch (Exception e) {
			log.error("Job rebuild failed: " + e.getLocalizedMessage());
		}
	}
}