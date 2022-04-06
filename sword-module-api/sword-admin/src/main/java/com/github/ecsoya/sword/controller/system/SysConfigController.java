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
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.domain.SysConfig;
import com.github.ecsoya.sword.system.service.ISysConfigService;

/**
 * The Class SysConfigController.
 */
@Controller
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/config";

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/**
	 * Config.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:config:view")
	@GetMapping()
	public String config() {
		return prefix + "/config";
	}

	/**
	 * List.
	 *
	 * @param config the config
	 * @return the table data info
	 */
	@RequiresPermissions("system:config:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysConfig config) {
		startPage();
		final List<SysConfig> list = configService.selectConfigList(config);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param config the config
	 * @return the ajax result
	 */
	@Log(title = "参数管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:config:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysConfig config) {
		final List<SysConfig> list = configService.selectConfigList(config);
		final ExcelUtil<SysConfig> util = new ExcelUtil<SysConfig>(SysConfig.class);
		return util.exportExcel(list, "参数数据");
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
	 * @param config the config
	 * @return the ajax result
	 */
	@RequiresPermissions("system:config:add")
	@Log(title = "参数管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysConfig config) {
		if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return error("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(configService.insertConfig(config));
	}

	/**
	 * Edits the.
	 *
	 * @param configId the config id
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{configId}")
	public String edit(@PathVariable("configId") Long configId, ModelMap mmap) {
		mmap.put("config", configService.selectConfigById(configId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param config the config
	 * @return the ajax result
	 */
	@RequiresPermissions("system:config:edit")
	@Log(title = "参数管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysConfig config) {
		if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
			return error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
		}
		config.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(configService.updateConfig(config));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("system:config:remove")
	@Log(title = "参数管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(configService.deleteConfigByIds(ids));
	}

	/**
	 * Clear cache.
	 *
	 * @return the ajax result
	 */
	@RequiresPermissions("system:config:remove")
	@Log(title = "参数管理", businessType = BusinessType.CLEAN)
	@GetMapping("/clearCache")
	@ResponseBody
	public AjaxResult clearCache() {
		configService.clearCache();
		return success();
	}

	/**
	 * Check config key unique.
	 *
	 * @param config the config
	 * @return the string
	 */
	@PostMapping("/checkConfigKeyUnique")
	@ResponseBody
	public String checkConfigKeyUnique(SysConfig config) {
		return configService.checkConfigKeyUnique(config);
	}
}
