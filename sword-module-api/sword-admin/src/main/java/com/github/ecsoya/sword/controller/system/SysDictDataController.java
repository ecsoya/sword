package com.github.ecsoya.sword.controller.system;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.github.ecsoya.sword.common.core.domain.entity.SysDictData;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysDictDataService;

/**
 * The Class SysDictDataController.
 */
@Controller
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/dict/data";

	/** The dict data service. */
	@Autowired
	private ISysDictDataService dictDataService;

	/**
	 * Dict data.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:dict:view")
	@GetMapping()
	public String dictData() {
		return prefix + "/data";
	}

	/**
	 * List.
	 *
	 * @param dictData the dict data
	 * @return the table data info
	 */
	@PostMapping("/list")
	@RequiresPermissions("system:dict:list")
	@ResponseBody
	public TableDataInfo list(SysDictData dictData) {
		startPage();
		final List<SysDictData> list = dictDataService.selectDictDataList(dictData);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param dictData the dict data
	 * @return the ajax result
	 */
	@Log(title = "字典数据", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:dict:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysDictData dictData) {
		final List<SysDictData> list = dictDataService.selectDictDataList(dictData);
		final ExcelUtil<SysDictData> util = new ExcelUtil<SysDictData>(SysDictData.class);
		return util.exportExcel(list, "字典数据");
	}

	/**
	 * Adds the.
	 *
	 * @param dictType the dict type
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/add/{dictType}")
	public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
		mmap.put("dictType", dictType);
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param dict the dict
	 * @return the ajax result
	 */
	@Log(title = "字典数据", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:dict:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysDictData dict) {
		dict.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(dictDataService.insertDictData(dict));
	}

	/**
	 * Edits the.
	 *
	 * @param dictCode the dict code
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{dictCode}")
	public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
		mmap.put("dict", dictDataService.selectDictDataById(dictCode));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param dict the dict
	 * @return the ajax result
	 */
	@Log(title = "字典数据", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:dict:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysDictData dict) {
		dict.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(dictDataService.updateDictData(dict));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@Log(title = "字典数据", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:dict:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(dictDataService.deleteDictDataByIds(ids));
	}
}
