package com.soyatec.sword.controller.tool;

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
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.poi.ExcelUtil;
import com.soyatec.sword.version.domain.SwordVersion;
import com.soyatec.sword.version.service.ISwordVersionService;

/**
 * 版本Controller
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-01-22
 */
@Controller
@RequestMapping("/tool/version")
public class VersionController extends BaseController {
	private String prefix = "tool/version";

	@Autowired
	private ISwordVersionService swordVersionService;

	@RequiresPermissions("tool:version:view")
	@GetMapping()
	public String version() {
		return prefix + "/version";
	}

	/**
	 * 查询版本列表
	 */
	@RequiresPermissions("tool:version:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SwordVersion beeplusVersion) {
		startPage();
		List<SwordVersion> list = swordVersionService.selectSwordVersionList(beeplusVersion);
		return getDataTable(list);
	}

	/**
	 * 导出版本列表
	 */
	@RequiresPermissions("tool:version:export")
	@Log(title = "版本", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SwordVersion beeplusVersion) {
		List<SwordVersion> list = swordVersionService.selectSwordVersionList(beeplusVersion);
		ExcelUtil<SwordVersion> util = new ExcelUtil<SwordVersion>(SwordVersion.class);
		return util.exportExcel(list, "version");
	}

	/**
	 * 新增版本
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存版本
	 */
	@RequiresPermissions("tool:version:add")
	@Log(title = "版本", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Long version, String android, String ios, Integer status, String description) {
		if (version == null) {
			return AjaxResult.error("");
		}
		if (StringUtils.isNotEmpty(android)) {
			SwordVersion va = new SwordVersion();
			va.setType(SwordVersion.TYPE_ANDROID);
			va.setVersion(version);
			va.setUrl(android);
			va.setDescription(description);
			va.setStatus(status);
			swordVersionService.insertSwordVersion(va);
		}
		if (StringUtils.isNotEmpty(ios)) {
			SwordVersion va = new SwordVersion();
			va.setType(SwordVersion.TYPE_IOS);
			va.setVersion(version);
			va.setUrl(ios);
			va.setDescription(description);
			va.setStatus(status);
			swordVersionService.insertSwordVersion(va);
		}
		return AjaxResult.success();
	}

	/**
	 * 修改版本
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		SwordVersion beeplusVersion = swordVersionService.selectSwordVersionById(id);
		mmap.put("swordVersion", beeplusVersion);
		return prefix + "/edit";
	}

	/**
	 * 修改保存版本
	 */
	@RequiresPermissions("tool:version:edit")
	@Log(title = "版本", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SwordVersion beeplusVersion) {
		return toAjax(swordVersionService.updateSwordVersion(beeplusVersion));
	}

	/**
	 * 删除版本
	 */
	@RequiresPermissions("tool:version:remove")
	@Log(title = "版本", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(swordVersionService.deleteSwordVersionByIds(ids));
	}
}
