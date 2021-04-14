package com.soyatec.sword.controller.system;

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

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.poi.ExcelUtil;
import com.soyatec.sword.system.domain.SysOperNotify;
import com.soyatec.sword.system.service.ISysOperNotifyService;

/**
 * 敏感操作通知Controller
 *
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-04-06
 */
@Controller
@RequestMapping("/system/notify")
public class SysOperNotifyController extends BaseController {
	private final String prefix = "system/notify";

	@Autowired
	private ISysOperNotifyService sysOperNotifyService;

	@RequiresPermissions("system:notify:view")
	@GetMapping()
	public String index() {
		return prefix + "/notify";
	}

	/**
	 * 查询敏感操作通知列表
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
	 * 导出敏感操作通知列表
	 */
	@RequiresPermissions("system:notify:export")
	@Log(title = "敏感操作通知", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysOperNotify sysOperNotify) {
		final List<SysOperNotify> list = sysOperNotifyService.selectSysOperNotifyList(sysOperNotify);
		final ExcelUtil<SysOperNotify> util = new ExcelUtil<SysOperNotify>(SysOperNotify.class);
		return util.exportExcel(list, "notify");
	}

	/**
	 * 新增敏感操作通知
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存敏感操作通知
	 */
	@RequiresPermissions("system:notify:add")
	@Log(title = "敏感操作通知", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SysOperNotify sysOperNotify) {
		return toAjax(sysOperNotifyService.insertSysOperNotify(sysOperNotify));
	}

	/**
	 * 修改敏感操作通知
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		final SysOperNotify sysOperNotify = sysOperNotifyService.selectSysOperNotifyById(id);
		mmap.put("sysOperNotify", sysOperNotify);
		return prefix + "/edit";
	}

	/**
	 * 修改保存敏感操作通知
	 */
	@RequiresPermissions("system:notify:edit")
	@Log(title = "敏感操作通知", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SysOperNotify sysOperNotify) {
		return toAjax(sysOperNotifyService.updateSysOperNotify(sysOperNotify));
	}

	/**
	 * 删除敏感操作通知
	 */
	@RequiresPermissions("system:notify:remove")
	@Log(title = "敏感操作通知", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(sysOperNotifyService.deleteSysOperNotifyByIds(ids));
	}
}
