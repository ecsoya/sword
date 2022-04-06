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

import com.github.ecsoya.sword.article.domain.UserAdvise;
import com.github.ecsoya.sword.article.service.IUserAdviseService;
import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.page.TableDataInfo;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.poi.ExcelUtil;

/**
 * The Class AdminAdviseController.
 */
@Controller
@RequestMapping("/admin/advise")
public class AdminAdviseController extends BaseController {

	/** The prefix. */
	private final String prefix = "admin/advise";

	/** The user advise service. */
	@Autowired
	private IUserAdviseService userAdviseService;

	/**
	 * Advise.
	 *
	 * @return the string
	 */
	@RequiresPermissions("admin:advise:view")
	@GetMapping()
	public String advise() {
		return prefix + "/advise";
	}

	/**
	 * List.
	 *
	 * @param userAdvise the user advise
	 * @return the table data info
	 */
	@RequiresPermissions("admin:advise:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(UserAdvise userAdvise) {
		startPage();
		userAdvise.setType(UserAdvise.TYPE_FEEDBACK);
		final List<UserAdvise> list = userAdviseService.selectUserAdviseList(userAdvise);
		return getDataTable(list);
	}

	/**
	 * Reply list.
	 *
	 * @param userAdvise the user advise
	 * @return the table data info
	 */
	@RequiresPermissions("admin:advise:list")
	@PostMapping("/reply/list")
	@ResponseBody
	public TableDataInfo replyList(UserAdvise userAdvise) {
		startPage();
		userAdvise.setType(UserAdvise.TYPE_REPLY);
		final List<UserAdvise> list = userAdviseService.selectUserAdviseList(userAdvise);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param userAdvise the user advise
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:advise:export")
	@Log(title = "用户反馈", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(UserAdvise userAdvise) {
		final List<UserAdvise> list = userAdviseService.selectUserAdviseList(userAdvise);
		final ExcelUtil<UserAdvise> util = new ExcelUtil<UserAdvise>(UserAdvise.class);
		return util.exportExcel(list, "advise");
	}

	/**
	 * Adds the.
	 *
	 * @param parentId the parent id
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/add")
	public String add(Long parentId, ModelMap mmap) {
		mmap.put("parentId", parentId);
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param userAdvise the user advise
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:advise:add")
	@Log(title = "用户反馈", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(UserAdvise userAdvise) {
		userAdvise.setType(UserAdvise.TYPE_REPLY);
		return toAjax(userAdviseService.insertUserAdvise(userAdvise));
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
		final UserAdvise userAdvise = userAdviseService.selectUserAdviseById(id);
		mmap.put("userAdvise", userAdvise);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param userAdvise the user advise
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:advise:edit")
	@Log(title = "用户反馈", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UserAdvise userAdvise) {
		return toAjax(userAdviseService.updateUserAdvise(userAdvise));
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("admin:advise:remove")
	@Log(title = "用户反馈", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(userAdviseService.deleteUserAdviseByIds(ids));
	}
}
