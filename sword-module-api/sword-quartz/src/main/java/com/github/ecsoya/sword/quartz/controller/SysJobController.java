package com.github.ecsoya.sword.quartz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.exception.job.TaskException;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.quartz.domain.SysJob;
import com.github.ecsoya.sword.quartz.service.ISysJobService;
import com.github.ecsoya.sword.quartz.util.CronUtils;

/**
 * The Class SysJobController.
 */
@Controller
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {

	/** The prefix. */
	private final String prefix = "monitor/job";

	/** The Constant LOOKUP_RMI. */
	public static final String LOOKUP_RMI = "rmi://";

	/** The job service. */
	@Autowired
	private ISysJobService jobService;

	/**
	 * Job.
	 *
	 * @return the string
	 */
	@RequiresPermissions("monitor:job:view")
	@GetMapping()
	public String job() {
		return prefix + "/job";
	}

	/**
	 * List.
	 *
	 * @param job the job
	 * @return the table data info
	 */
	@RequiresPermissions("monitor:job:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysJob job) {
		startPage();
		List<SysJob> list = jobService.selectJobList(job);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param job the job
	 * @return the ajax result
	 */
	@Log(title = "定时任务", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:job:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysJob job) {
		List<SysJob> list = jobService.selectJobList(job);
		ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
		return util.exportExcel(list, "定时任务");
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 * @throws SchedulerException the scheduler exception
	 */
	@Log(title = "定时任务", businessType = BusinessType.DELETE)
	@RequiresPermissions("monitor:job:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) throws SchedulerException {
		jobService.deleteJobByIds(ids);
		return success();
	}

	/**
	 * Detail.
	 *
	 * @param jobId the job id
	 * @param mmap  the mmap
	 * @return the string
	 */
	@RequiresPermissions("monitor:job:detail")
	@GetMapping("/detail/{jobId}")
	public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap) {
		mmap.put("name", "job");
		mmap.put("job", jobService.selectJobById(jobId));
		return prefix + "/detail";
	}

	/**
	 * Change status.
	 *
	 * @param job the job
	 * @return the ajax result
	 * @throws SchedulerException the scheduler exception
	 */
	@Log(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequiresPermissions("monitor:job:changeStatus")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysJob job) throws SchedulerException {
		SysJob newJob = jobService.selectJobById(job.getJobId());
		newJob.setStatus(job.getStatus());
		return toAjax(jobService.changeStatus(newJob));
	}

	/**
	 * Run.
	 *
	 * @param job the job
	 * @return the ajax result
	 * @throws SchedulerException the scheduler exception
	 */
	@Log(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequiresPermissions("monitor:job:changeStatus")
	@PostMapping("/run")
	@ResponseBody
	public AjaxResult run(SysJob job) throws SchedulerException {
		jobService.run(job);
		return success();
	}

	/**
	 * Adds the.
	 *
	 * @return the string
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param job the job
	 * @return the ajax result
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	@Log(title = "定时任务", businessType = BusinessType.INSERT)
	@RequiresPermissions("monitor:job:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return AjaxResult.error("新增任务'" + job.getJobName() + "'失败，Cron表达式不正确");
		} else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), LOOKUP_RMI)) {
			return AjaxResult.error("新增任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用");
		}
		return toAjax(jobService.insertJob(job));
	}

	/**
	 * Edits the.
	 *
	 * @param jobId the job id
	 * @param mmap  the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{jobId}")
	public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap) {
		mmap.put("job", jobService.selectJobById(jobId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param job the job
	 * @return the ajax result
	 * @throws SchedulerException the scheduler exception
	 * @throws TaskException      the task exception
	 */
	@Log(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequiresPermissions("monitor:job:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
		} else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), LOOKUP_RMI)) {
			return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi://'调用");
		}
		return toAjax(jobService.updateJob(job));
	}

	/**
	 * Check cron expression is valid.
	 *
	 * @param job the job
	 * @return true, if successful
	 */
	@PostMapping("/checkCronExpressionIsValid")
	@ResponseBody
	public boolean checkCronExpressionIsValid(SysJob job) {
		return jobService.checkCronExpressionIsValid(job.getCronExpression());
	}
}
