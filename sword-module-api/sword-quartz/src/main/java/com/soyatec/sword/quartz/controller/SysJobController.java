package com.soyatec.sword.quartz.controller;

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

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.exception.job.TaskException;
import com.soyatec.sword.common.utils.poi.ExcelUtil;
import com.soyatec.sword.quartz.domain.SysJob;
import com.soyatec.sword.quartz.service.ISysJobService;
import com.soyatec.sword.quartz.util.CronUtils;

/**
 * 调度任务信息操作处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/monitor/job")
public class SysJobController extends BaseController {
	private final String prefix = "monitor/job";

	@Autowired
	private ISysJobService jobService;

	@RequiresPermissions("monitor:job:view")
	@GetMapping()
	public String job() {
		return prefix + "/job";
	}

	@RequiresPermissions("monitor:job:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysJob job) {
		startPage();
		final List<SysJob> list = jobService.selectJobList(job);
		return getDataTable(list);
	}

	@Log(title = "定时任务", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:job:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysJob job) {
		final List<SysJob> list = jobService.selectJobList(job);
		final ExcelUtil<SysJob> util = new ExcelUtil<SysJob>(SysJob.class);
		return util.exportExcel(list, "定时任务");
	}

	@Log(title = "定时任务", businessType = BusinessType.DELETE)
	@RequiresPermissions("monitor:job:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) throws SchedulerException {
		jobService.deleteJobByIds(ids);
		return success();
	}

	@RequiresPermissions("monitor:job:detail")
	@GetMapping("/detail/{jobId}")
	public String detail(@PathVariable("jobId") Long jobId, ModelMap mmap) {
		mmap.put("name", "job");
		mmap.put("job", jobService.selectJobById(jobId));
		return prefix + "/detail";
	}

	/**
	 * 任务调度状态修改
	 */
	@Log(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequiresPermissions("monitor:job:changeStatus")
	@PostMapping("/changeStatus")
	@ResponseBody
	public AjaxResult changeStatus(SysJob job) throws SchedulerException {
		final SysJob newJob = jobService.selectJobById(job.getJobId());
		newJob.setStatus(job.getStatus());
		return toAjax(jobService.changeStatus(newJob));
	}

	/**
	 * 任务调度立即执行一次
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
	 * 新增调度
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存调度
	 */
	@Log(title = "定时任务", businessType = BusinessType.INSERT)
	@RequiresPermissions("monitor:job:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return AjaxResult.error("cron表达式不正确");
		}
		return toAjax(jobService.insertJob(job));
	}

	/**
	 * 修改调度
	 */
	@GetMapping("/edit/{jobId}")
	public String edit(@PathVariable("jobId") Long jobId, ModelMap mmap) {
		mmap.put("job", jobService.selectJobById(jobId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存调度
	 */
	@Log(title = "定时任务", businessType = BusinessType.UPDATE)
	@RequiresPermissions("monitor:job:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysJob job) throws SchedulerException, TaskException {
		if (!CronUtils.isValid(job.getCronExpression())) {
			return AjaxResult.error("cron表达式不正确");
		}
		return toAjax(jobService.updateJob(job));
	}

	/**
	 * 校验cron表达式是否有效
	 */
	@PostMapping("/checkCronExpressionIsValid")
	@ResponseBody
	public boolean checkCronExpressionIsValid(SysJob job) {
		return jobService.checkCronExpressionIsValid(job.getCronExpression());
	}
}
