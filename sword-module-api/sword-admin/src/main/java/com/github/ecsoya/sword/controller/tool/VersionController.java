package com.github.ecsoya.sword.controller.tool;

import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;
import com.github.ecsoya.sword.upload.utils.FileUploadUtils;
import com.github.ecsoya.sword.version.domain.SwordVersion;
import com.github.ecsoya.sword.version.service.ISwordVersionService;

/**
 * The Class VersionController.
 */
@Controller
@RequestMapping("/tool/version")
public class VersionController extends BaseController {

	/** The prefix. */
	private final String prefix = "tool/version";

	/** The sword version service. */
	@Autowired
	private ISwordVersionService swordVersionService;

	/**
	 * Version.
	 *
	 * @return the string
	 */
	@RequiresPermissions("tool:version:view")
	@GetMapping()
	public String version() {
		return prefix + "/version";
	}

	/**
	 * List.
	 *
	 * @param beeplusVersion the beeplus version
	 * @return the table data info
	 */
	@RequiresPermissions("tool:version:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SwordVersion beeplusVersion) {
		startPage();
		final List<SwordVersion> list = swordVersionService.selectSwordVersionList(beeplusVersion);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param beeplusVersion the beeplus version
	 * @return the ajax result
	 */
	@RequiresPermissions("tool:version:export")
	@Log(title = "版本", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SwordVersion beeplusVersion) {
		final List<SwordVersion> list = swordVersionService.selectSwordVersionList(beeplusVersion);
		final ExcelUtil<SwordVersion> util = new ExcelUtil<SwordVersion>(SwordVersion.class);
		return util.exportExcel(list, "version");
	}

	/**
	 * Adds the.
	 *
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/add")
	public String add(ModelMap mmap) {
		SwordVersion version = swordVersionService.selectLatestVersion(SwordVersion.TYPE_ANDROID);
		if (version != null) {
			Long versionNumber = version.getVersion();
			if (versionNumber != null) {
				mmap.put("version", versionNumber + 1);
			}
		}
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param version     the version
	 * @param android     the android
	 * @param ios         the ios
	 * @param status      the status
	 * @param description the description
	 * @return the ajax result
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
			final SwordVersion va = new SwordVersion();
			va.setType(SwordVersion.TYPE_ANDROID);
			va.setVersion(version);
			va.setUrl(android);
			va.setDescription(description);
			va.setStatus(status);
			swordVersionService.insertSwordVersion(va);
		}
		if (StringUtils.isNotEmpty(ios)) {
			final SwordVersion va = new SwordVersion();
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
	 * Edits the.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		final SwordVersion beeplusVersion = swordVersionService.selectSwordVersionById(id);
		mmap.put("swordVersion", beeplusVersion);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param beeplusVersion the beeplus version
	 * @return the ajax result
	 */
	@RequiresPermissions("tool:version:edit")
	@Log(title = "版本", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SwordVersion beeplusVersion) {
		return toAjax(swordVersionService.updateSwordVersion(beeplusVersion));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("tool:version:remove")
	@Log(title = "版本", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(swordVersionService.deleteSwordVersionByIds(ids));
	}

	/**
	 * Upload file.
	 *
	 * @param file    the file
	 * @param version the version
	 * @return the ajax result
	 * @throws Exception the exception
	 */
	@PostMapping("/upload")
	@ResponseBody
	public AjaxResult uploadFile(MultipartFile file, Long version) throws Exception {
		try {
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			if (index != -1) {
				String prefix = fileName.substring(0, index);
				String ext = fileName.substring(index);
				if (version != null) {
					fileName = prefix + "-" + version;
				}
				fileName += DateUtils.dateTimeNow() + ext;
				InputStream in = file.getInputStream();
				// 上传并返回新文件名称
				final String url = FileUploadUtils.upload(fileName, in);
				final AjaxResult ajax = AjaxResult.success();
				ajax.put("fileName", url);
				ajax.put("url", url);
				return ajax;
			}
			// 上传并返回新文件名称
			final String url = FileUploadUtils.upload(file);
			final AjaxResult ajax = AjaxResult.success();
			ajax.put("fileName", url);
			ajax.put("url", url);
			return ajax;
		} catch (final Exception e) {
			return AjaxResult.error(e.getMessage());
		}
	}
}
