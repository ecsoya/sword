package com.soyatec.sword.controller.system;

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

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.constant.UserConstants;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.page.TableDataInfo;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.utils.poi.ExcelUtil;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.system.domain.SysPost;
import com.soyatec.sword.system.service.ISysPostService;

/**
 * 岗位信息操作处理
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
@RequestMapping("/system/post")
public class SysPostController extends BaseController {
	private final String prefix = "system/post";

	@Autowired
	private ISysPostService postService;

	@RequiresPermissions("system:post:view")
	@GetMapping()
	public String operlog() {
		return prefix + "/post";
	}

	@RequiresPermissions("system:post:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(SysPost post) {
		startPage();
		final List<SysPost> list = postService.selectPostList(post);
		return getDataTable(list);
	}

	@Log(title = "岗位管理", businessType = BusinessType.EXPORT)
	@RequiresPermissions("system:post:export")
	@PostMapping("/export")
	@ResponseBody
	public AjaxResult export(SysPost post) {
		final List<SysPost> list = postService.selectPostList(post);
		final ExcelUtil<SysPost> util = new ExcelUtil<SysPost>(SysPost.class);
		return util.exportExcel(list, "岗位数据");
	}

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
	 * 新增岗位
	 */
	@GetMapping("/add")
	public String add() {
		return prefix + "/add";
	}

	/**
	 * 新增保存岗位
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
	 * 修改岗位
	 */
	@GetMapping("/edit/{postId}")
	public String edit(@PathVariable("postId") Long postId, ModelMap mmap) {
		mmap.put("post", postService.selectPostById(postId));
		return prefix + "/edit";
	}

	/**
	 * 修改保存岗位
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
	 * 校验岗位名称
	 */
	@PostMapping("/checkPostNameUnique")
	@ResponseBody
	public String checkPostNameUnique(SysPost post) {
		return postService.checkPostNameUnique(post);
	}

	/**
	 * 校验岗位编码
	 */
	@PostMapping("/checkPostCodeUnique")
	@ResponseBody
	public String checkPostCodeUnique(SysPost post) {
		return postService.checkPostCodeUnique(post);
	}
}
