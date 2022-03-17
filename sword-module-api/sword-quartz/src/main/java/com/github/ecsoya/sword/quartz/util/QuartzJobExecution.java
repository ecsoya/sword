package com.github.ecsoya.sword.quartz.util;

import org.quartz.JobExecutionContext;

import com.github.ecsoya.sword.quartz.domain.SysJob;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author Jin Liu (angryred@qq.com)
 *
 */
public class QuartzJobExecution extends AbstractQuartzJob {
	@Override
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		JobInvokeUtil.invokeMethod(sysJob);
	}
}
