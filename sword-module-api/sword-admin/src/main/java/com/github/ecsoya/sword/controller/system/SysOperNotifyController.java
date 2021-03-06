package com.github.ecsoya.sword.controller.system;

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
import com.github.ecsoya.sword.system.domain.SysOperNotify;
import com.github.ecsoya.sword.system.service.ISysOperNotifyService;

/**
 * The Class SysOperNotifyController.
 */
@Controller
@RequestMapping("/system/notify")
public class SysOperNotifyController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/notify";

	/** The sys oper notify service. */
	@Autowired
	private ISysOperNotifyService sysOperNotifyService;

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:notify:view")
	@GetMapping()
	public String index() {
		return prefix + "/notify";
	}

	/**
	 * List.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the table data info
	 */
	@RequiresPermissions("system:notify:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysOperNotify sysOperNotify) {
		startPage();
		final List<SysOperNotify> list = sysOperNotifyService.selectSysOperNotifyList(sysOperNotify);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the ajax result
	 */
	@RequiresPermissions("system:notify:export")
	@Log(title = "??????????????????", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysOperNotify sysOperNotify) {
		final List<SysOperNotify> list = sysOperNotifyService.selectSysOperNotifyList(sysOperNotify);
		final ExcelUtil<SysOperNotify> util = new ExcelUtil<SysOperNotify>(SysOperNotify.class);
		return util.exportExcel(list, "notify");
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
	 * @param sysOperNotify the sys oper notify
	 * @return the ajax result
	 */
	@RequiresPermissions("system:notify:add")
	@Log(title = "??????????????????", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SysOperNotify sysOperNotify) {
		return toAjax(sysOperNotifyService.insertSysOperNotify(sysOperNotify));
	}

	/**
	 * Edits the.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		final SysOperNotify sysOperNotify = sysOperNotifyService.selectSysOperNotifyById(id);
		mmap.put("sysOperNotify", sysOperNotify);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param sysOperNotify the sys oper notify
	 * @return the ajax result
	 */
	@RequiresPermissions("system:notify:edit")
	@Log(title = "??????????????????", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SysOperNotify sysOperNotify) {
		return toAjax(sysOperNotifyService.updateSysOperNotify(sysOperNotify));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("system:notify:remove")
	@Log(title = "??????????????????", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(sysOperNotifyService.deleteSysOperNotifyByIds(ids));
	}
}
