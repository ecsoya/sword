package com.github.ecsoya.sword.controller.monitor;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.service.SysPasswordService;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.domain.SysLogininfor;
import com.github.ecsoya.sword.system.service.ISysLogininforService;

/**
 * The Class SysLogininforController.
 */
@Controller
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {

	/** The prefix. */
	private final String prefix = "monitor/logininfor";

	/** The logininfor service. */
	@Autowired
	private ISysLogininforService logininforService;

	/** The password service. */
	@Autowired
	private SysPasswordService passwordService;

	/**
	 * Logininfor.
	 *
	 * @return the string
	 */
	@RequiresPermissions("monitor:logininfor:view")
	@GetMapping()
	public String logininfor() {
		return prefix + "/logininfor";
	}

	/**
	 * List.
	 *
	 * @param logininfor the logininfor
	 * @return the table data info
	 */
	@RequiresPermissions("monitor:logininfor:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysLogininfor logininfor) {
		startPage();
		logininfor.getParams().put("admin", ShiroUtils.getLoginName());
		final List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param logininfor the logininfor
	 * @return the ajax result
	 */
	@Log(title = "登录日志", businessType = BusinessType.EXPORT)
	@RequiresPermissions("monitor:logininfor:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysLogininfor logininfor) {
		logininfor.getParams().put("admin", ShiroUtils.getLoginName());
		final List<SysLogininfor> list = logininforService.selectLogininforList(logininfor);
		final ExcelUtil<SysLogininfor> util = new ExcelUtil<SysLogininfor>(SysLogininfor.class);
		return util.exportExcel(list, "登录日志");
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("monitor:logininfor:remove")
	@Log(title = "登录日志", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(logininforService.deleteLogininforByIds(ids));
	}

	/**
	 * Clean.
	 *
	 * @return the ajax result
	 */
	@RequiresPermissions("monitor:logininfor:remove")
	@Log(title = "登录日志", businessType = BusinessType.CLEAN)
	@PostMapping("/clean")
	@ResponseBody
	public AjaxResult clean() {
		logininforService.cleanLogininfor();
		return success();
	}

	/**
	 * Unlock.
	 *
	 * @param loginName the login name
	 * @return the ajax result
	 */
	@RequiresPermissions("monitor:logininfor:unlock")
	@Log(title = "账户解锁", businessType = BusinessType.OTHER)
	@PostMapping("/unlock")
	@ResponseBody
	public AjaxResult unlock(String loginName) {
		passwordService.clearLoginRecordCache(loginName);
		return success();
	}
}
