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
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysDictType;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysDictTypeService;

/**
 * The Class SysDictTypeController.
 */
@Controller
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/dict/type";

	/** The dict type service. */
	@Autowired
	private ISysDictTypeService dictTypeService;

	/**
	 * Dict type.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:dict:view")
	@GetMapping()
	public String dictType() {
		return prefix + "/type";
	}

	/**
	 * List.
	 *
	 * @param dictType the dict type
	 * @return the table data info
	 */
	@PostMapping("/list")
	@RequiresPermissions("system:dict:list")
	@ResponseBody
	public TableDataInfo list(SysDictType dictType) {
		startPage();
		final List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param dictType the dict type
	 * @return the ajax result
	 */
	@Log(title = "字典类型", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:dict:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysDictType dictType) {

		final List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
		final ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
		return util.exportExcel(list, "字典类型");
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
	 * @param dict the dict
	 * @return the ajax result
	 */
	@Log(title = "字典类型", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:dict:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysDictType dict) {
		if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(dictTypeService.insertDictType(dict));
	}

	/**
	 * Edits the.
	 *
	 * @param dictId the dict id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{dictId}")
	public String edit(@PathVariable("dictId") Long dictId, ModelMap mmap) {
		mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param dict the dict
	 * @return the ajax result
	 */
	@Log(title = "字典类型", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:dict:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysDictType dict) {
		if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
			return error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
		}
		dict.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(dictTypeService.updateDictType(dict));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@Log(title = "字典类型", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:dict:remove")
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(dictTypeService.deleteDictTypeByIds(ids));
	}

	/**
	 * Clear cache.
	 *
	 * @return the ajax result
	 */
	@RequiresPermissions("system:dict:remove")
	@Log(title = "字典类型", businessType = BusinessType.CLEAN)
	@GetMapping("/clearCache")
	@ResponseBody
	public AjaxResult clearCache() {
		dictTypeService.clearCache();
		return success();
	}

	/**
	 * Detail.
	 *
	 * @param dictId the dict id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@RequiresPermissions("system:dict:list")
	@GetMapping("/detail/{dictId}")
	public String detail(@PathVariable("dictId") Long dictId, ModelMap mmap) {
		mmap.put("dict", dictTypeService.selectDictTypeById(dictId));
		mmap.put("dictList", dictTypeService.selectDictTypeAll());
		return "system/dict/data/data";
	}

	/**
	 * Check dict type unique.
	 *
	 * @param dictType the dict type
	 * @return the string
	 */
	@PostMapping("/checkDictTypeUnique")
	@ResponseBody
	public String checkDictTypeUnique(SysDictType dictType) {
		return dictTypeService.checkDictTypeUnique(dictType);
	}

	/**
	 * Select dept tree.
	 *
	 * @param columnId the column id
	 * @param dictType the dict type
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/selectDictTree/{columnId}/{dictType}")
	public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType,
			ModelMap mmap) {
		mmap.put("columnId", columnId);
		mmap.put("dict", dictTypeService.selectDictTypeByType(dictType));
		return prefix + "/tree";
	}

	/**
	 * Tree data.
	 *
	 * @return the list
	 */
	@GetMapping("/treeData")
	@ResponseBody
	public List<Ztree> treeData() {
		final List<Ztree> ztrees = dictTypeService.selectDictTree(new SysDictType());
		return ztrees;
	}
}
