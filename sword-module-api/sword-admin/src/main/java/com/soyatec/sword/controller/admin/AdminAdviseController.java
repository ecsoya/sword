package com.soyatec.sword.controller.admin;

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

import com.soyatec.sword.article.domain.UserAdvise;
import com.soyatec.sword.article.service.IUserAdviseService;
import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.poi.ExcelUtil;

@Controller
@RequestMapping("/admin/advise")
public class AdminAdviseController extends BaseController {
	private final String prefix = "admin/advise";

	@Autowired
	private IUserAdviseService userAdviseService;

	@RequiresPermissions("admin:advise:view")
	@GetMapping()
	public String advise() {
		return prefix + "/advise";
	}

	/**
	 * 查询用户反馈列表
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
	 * 查询用户反馈列表
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
	 * 导出用户反馈列表
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
	 * 新增用户反馈
	 */
	@GetMapping("/add")
	public String add(Long parentId, ModelMap mmap) {
		mmap.put("parentId", parentId);
		return prefix + "/add";
	}

	/**
	 * 新增保存用户反馈
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
	 * 修改用户反馈
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Long id, ModelMap mmap) {
		final UserAdvise userAdvise = userAdviseService.selectUserAdviseById(id);
		mmap.put("userAdvise", userAdvise);
		return prefix + "/edit";
	}

	/**
	 * 修改保存用户反馈
	 */
	@RequiresPermissions("admin:advise:edit")
	@Log(title = "用户反馈", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(UserAdvise userAdvise) {
		return toAjax(userAdviseService.updateUserAdvise(userAdvise));
	}

	/**
	 * 删除用户反馈
	 */
	@RequiresPermissions("admin:advise:remove")
	@Log(title = "用户反馈", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		return toAjax(userAdviseService.deleteUserAdviseByIds(ids));
	}
}
