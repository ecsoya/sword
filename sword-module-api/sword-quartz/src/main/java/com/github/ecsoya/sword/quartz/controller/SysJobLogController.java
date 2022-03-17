package com.github.ecsoya.sword.quartz.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.quartz.domain.SysJob;
import com.github.ecsoya.sword.quartz.domain.SysJobLog;
import com.github.ecsoya.sword.quartz.service.ISysJobLogService;
import com.github.ecsoya.sword.quartz.service.ISysJobService;

/**
 * 调度日志操作处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/monitor/jobLog")
public class SysJobLogController extends BaseController {
	private final String prefix = "monitor/job";

	@Autowired
	private ISysJobService jobService;

	@Autowired
	private ISysJobLogService jobLogService;

	@RequiresPermissions("monitor:job:view")
	@GetMapping()
	public String jobLog(@RequestParam(value = "jobId", required = false) Long jobId, ModelMap mmap) {
		if (StringUtils.isNotNull(jobId)) {
			final SysJob job = jobService.selectJobById(jobId);
			mmap.put("job", job);
		}
		return prefix + "/jobLog";
	}

	@RequiresPermissions("monitor:job:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysJobLog jobLog) {
		startPage();
		final List<SysJobLog> list = jobLogService.selectJobLogList(jobLog);
		return getDataTable(list);
	}

	@Log(title = "调度日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:job:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysJobLog jobLog) {
		final List<SysJobLog> list = jobLogService.selectJobLogList(jobLog);
		final ExcelUtil<SysJobLog> util = new ExcelUtil<SysJobLog>(SysJobLog.class);
		return util.exportExcel(list, "调度日志");
	}

	@Log(title = "调度日志", businessType = BusinessType.DELETE)
	@RequiresPermissions("monitor:job:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(jobLogService.deleteJobLogByIds(ids));
	}

	@RequiresPermissions("monitor:job:detail")
	@GetMapping("/detail/{jobLogId}")
	public String detail(@PathVariable("jobLogId") Long jobLogId, ModelMap mmap) {
		mmap.put("name", "jobLog");
		mmap.put("jobLog", jobLogService.selectJobLogById(jobLogId));
		return prefix + "/detail";
	}

	@Log(title = "调度日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("monitor:job:remove")
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		jobLogService.cleanJobLog();
		return success();
	}
}
