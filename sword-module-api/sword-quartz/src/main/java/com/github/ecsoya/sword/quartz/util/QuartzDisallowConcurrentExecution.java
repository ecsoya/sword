package com.github.ecsoya.sword.quartz.util;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * The Class QuartzDisallowConcurrentExecution.
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {

	/**
	 * Do execute.
	 *
	 * @param context the context
	 * @param sysJob  the sys job
	 * @throws Exception the exception
	 */
	@Override
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		JobInvokeUtil.invokeMethod(sysJob);
	}
}
