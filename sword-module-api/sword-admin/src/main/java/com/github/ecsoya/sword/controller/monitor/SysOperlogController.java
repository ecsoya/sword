package com.github.ecsoya.sword.controller.monitor;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.domain.SysOperLog;
import com.github.ecsoya.sword.system.service.ISysOperLogService;

/**
 * The Class SysOperlogController.
 */
@Controller
@RequestMapping("/monitor/operlog")
public class SysOperlogController extends BaseController {

	/** The prefix. */
	private final String prefix = "monitor/operlog";

	/** The oper log service. */
	@Autowired
	private ISysOperLogService operLogService;

	/**
	 * Operlog.
	 *
	 * @return the string
	 */
	@RequiresPermissions("monitor:operlog:view")
	@GetMapping()
	public String operlog() {
		return prefix + "/operlog";
	}

	/**
	 * List.
	 *
	 * @param operLog the oper log
	 * @return the table data info
	 */
	@RequiresPermissions("monitor:operlog:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysOperLog operLog) {
		startPage();
		operLog.getParams().put("admin", ShiroUtils.getLoginName());
		final List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param operLog the oper log
	 * @return the ajax result
	 */
	@Log(title = "操作日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:operlog:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysOperLog operLog) {
		operLog.getParams().put("admin", ShiroUtils.getLoginName());
		final List<SysOperLog> list = operLogService.selectOperLogList(operLog);
		final ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
		return util.exportExcel(list, "操作日志");
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("monitor:operlog:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(operLogService.deleteOperLogByIds(ids));
	}

	/**
	 * Detail.
	 *
	 * @param operId the oper id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@RequiresPermissions("monitor:operlog:detail")
	@GetMapping("/detail/{operId}")
	public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
		mmap.put("operLog", operLogService.selectOperLogById(operId));
		return prefix + "/detail";
	}

	/**
	 * Clean.
	 *
	 * @return the ajax result
	 */
	@Log(title = "操作日志", businessType = BusinessType.CLEAN)
	@RequiresPermissions("monitor:operlog:remove")
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		operLogService.cleanOperLog();
		return success();
	}
}
