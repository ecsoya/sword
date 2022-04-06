package com.github.ecsoya.sword.controller.admin;

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

import com.github.ecsoya.sword.article.domain.SwordArticle;
import com.github.ecsoya.sword.article.service.ISwordArticleService;
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;

/**
 * The Class AdminArticleController.
 */
@Controller
@RequestMapping("/admin/article")
public class AdminArticleController extends BaseController {

	/** The prefix. */
	private final String prefix = "admin/article";

	/** The sword article service. */
	@Autowired
	private ISwordArticleService swordArticleService;

	/**
	 * Article.
	 *
	 * @return the string
	 */
	@RequiresPermissions("admin:article:view")
	@GetMapping()
	public String article() {
		return prefix + "/article";
	}

	/**
	 * List.
	 *
	 * @param swordArticle the sword article
	 * @return the table data info
	 */
	@RequiresPermissions("admin:article:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SwordArticle swordArticle) {
		startPage();
		final List<SwordArticle> list = swordArticleService.selectSwordArticleList(swordArticle);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param swordArticle the sword article
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:article:export")
	@Log(title = "文章", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SwordArticle swordArticle) {
		final List<SwordArticle> list = swordArticleService.selectSwordArticleList(swordArticle);
		final ExcelUtil<SwordArticle> util = new ExcelUtil<SwordArticle>(SwordArticle.class);
		return util.exportExcel(list, "article");
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
	 * Preview.
	 *
	 * @param id   the id
	 * @param mmap the mmap
	 * @return the string
	 */
	@GetMapping("/preview")
	public String preview(Long id, ModelMap mmap) {
		mmap.put("id", id);
		return prefix + "/preview";
	}

	/**
	 * Preview.
	 *
	 * @param id the id
	 * @return the ajax result
	 */
	@GetMapping("/content")
	@ResponseBody
	public AjaxResult preview(Long id) {
		final SwordArticle article = swordArticleService.selectSwordArticleById(id);
		if (article == null) {
			return AjaxResult.error("错误");
		}
		return AjaxResult.success("成功", article.getContent());
	}

	/**
	 * Adds the save.
	 *
	 * @param swordArticle the sword article
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:article:add")
	@Log(title = "文章", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(SwordArticle swordArticle) {
		return toAjax(swordArticleService.insertSwordArticle(swordArticle));
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
		final SwordArticle swordArticle = swordArticleService.selectSwordArticleById(id);
		mmap.put("swordArticle", swordArticle);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param swordArticle the sword article
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:article:edit")
	@Log(title = "文章", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(SwordArticle swordArticle) {
		return toAjax(swordArticleService.updateSwordArticle(swordArticle));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:article:remove")
	@Log(title = "文章", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(swordArticleService.deleteSwordArticleByIds(ids));
	}
}
