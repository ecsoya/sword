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
import com.github.ecsoya.sword.system.domain.SysPost;
import com.github.ecsoya.sword.system.service.ISysPostService;

/**
 * The Class SysPostController.
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/post";

	/** The post service. */
	@Autowired
	private ISysPostService postService;

	/**
	 * Operlog.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:post:view")
	@GetMapping()
	public String operlog() {
		return prefix + "/post";
	}

	/**
	 * List.
	 *
	 * @param post the post
	 * @return the table data info
	 */
	@RequiresPermissions("system:post:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysPost post) {
		startPage();
		final List<SysPost> list = postService.selectPostList(post);
		return getDataTable(list);
	}

	/**
	 * Export.
	 *
	 * @param post the post
	 * @return the ajax result
	 */
	@Log(title = "岗位管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:post:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysPost post) {
		final List<SysPost> list = postService.selectPostList(post);
		final ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
		return util.exportExcel(list, "岗位数据");
	}

	/**
	 * Removes the.
	 *
	 * @param ids the ids
	 * @return the ajax result
	 */
	@RequiresPermissions("system:post:remove")
	@Log(title = "岗位管理", businessType = BusinessType.DELETE)
	@PostMapping("/remove")
	@ResponseBody
	public AjaxResult remove(String ids) {
		try {
			return toAjax(postService.deletePostByIds(ids));
		} catch (final Exception e) {
			return error(e.getMessage());
		}
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
	 * @param post the post
	 * @return the ajax result
	 */
	@RequiresPermissions("system:post:add")
	@Log(title = "岗位管理", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysPost post) {
		if (UserConstants.POST_NAME_NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return error("新增岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return error("新增岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(postService.insertPost(post));
	}

	/**
	 * Edits the.
	 *
	 * @param postId the post id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{postId}")
	public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
		mmap.put("post", postService.selectPostById(postId));
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param post the post
	 * @return the ajax result
	 */
	@RequiresPermissions("system:post:edit")
	@Log(title = "岗位管理", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysPost post) {
		if (UserConstants.POST_NAME_NOT_UNIQUE.equals(postService.checkPostNameUnique(post))) {
			return error("修改岗位'" + post.getPostName() + "'失败，岗位名称已存在");
		} else if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(post))) {
			return error("修改岗位'" + post.getPostName() + "'失败，岗位编码已存在");
		}
		post.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(postService.updatePost(post));
	}

	/**
	 * Check post name unique.
	 *
	 * @param post the post
	 * @return the string
	 */
	@PostMapping("/checkPostNameUnique")
	@ResponseBody
	public String checkPostNameUnique(SysPost post) {
		return postService.checkPostNameUnique(post);
	}

	/**
	 * Check post code unique.
	 *
	 * @param post the post
	 * @return the string
	 */
	@PostMapping("/checkPostCodeUnique")
	@ResponseBody
	public String checkPostCodeUnique(SysPost post) {
		return postService.checkPostCodeUnique(post);
	}
}
