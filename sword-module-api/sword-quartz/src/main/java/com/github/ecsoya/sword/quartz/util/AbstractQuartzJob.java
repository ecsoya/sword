package com.github.ecsoya.sword.quartz.util;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ecsoya.sword.common.constant.Constants;
import com.github.ecsoya.sword.common.constant.ScheduleConstants;
import com.github.ecsoya.sword.common.utils.ExceptionUtil;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.quartz.domain.SysJob;
import com.github.ecsoya.sword.quartz.domain.SysJobLog;
import com.github.ecsoya.sword.quartz.service.ISysJobLogService;

/**
 * The Class AbstractQuartzJob.
 */
public abstract class AbstractQuartzJob implements Job {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(AbstractQuartzJob.class);

	/** The thread local. */
	private static ThreadLocal<Date> threadLocal = new ThreadLocal<>();

	/**
	 * Execute.
	 *
	 * @param context the context
	 * @throws JobExecutionException the job execution exception
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final SysJob sysJob = new SysJob();
		BeanUtils.copyBeanProp(sysJob, context.getMergedJobDataMap().get(ScheduleConstants.TASK_PROPERTIES));
		try {
			before(context, sysJob);
			if (sysJob != null) {
				doExecute(context, sysJob);
			}
			after(context, sysJob, null);
		} catch (final Exception e) {
			log.error("任务执行异常  - ：", e);
			after(context, sysJob, e);
		}
	}

	/**
	 * Before.
	 *
	 * @param context the context
	 * @param sysJob  the sys job
	 */
	protected void before(JobExecutionContext context, SysJob sysJob) {
		threadLocal.set(new Date());
	}

	/**
	 * After.
	 *
	 * @param context the context
	 * @param sysJob  the sys job
	 * @param e       the e
	 */
	protected void after(JobExecutionContext context, SysJob sysJob, Exception e) {
		final Date startTime = threadLocal.get();
		threadLocal.remove();

		final SysJobLog sysJobLog = new SysJobLog();
		sysJobLog.setJobName(sysJob.getJobName());
		sysJobLog.setJobGroup(sysJob.getJobGroup());
		sysJobLog.setInvokeTarget(sysJob.getInvokeTarget());
		sysJobLog.setStartTime(startTime);
		sysJobLog.setEndTime(new Date());
		final long runMs = sysJobLog.getEndTime().getTime() - sysJobLog.getStartTime().getTime();
		sysJobLog.setJobMessage(sysJobLog.getJobName() + " 总共耗时：" + runMs + "毫秒");
		if (e != null) {
			sysJobLog.setStatus(Constants.FAIL);
			final String errorMsg = StringUtils.substring(ExceptionUtil.getExceptionMessage(e), 0, 2000);
			sysJobLog.setExceptionInfo(errorMsg);
		} else {
			sysJobLog.setStatus(Constants.SUCCESS);
		}

		// 写入数据库当中
		SpringUtils.getBean(ISysJobLogService.class).addJobLog(sysJobLog);
	}

	/**
	 * Do execute.
	 *
	 * @param context the context
	 * @param sysJob  the sys job
	 * @throws Exception the exception
	 */
	protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
