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
import com.github.ecsoya.sword.common.core.domain.entity.SysDept;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.github.ecsoya.sword.system.service.ISysDeptService;

/**
 * The Class SysDeptController.
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

	/** The prefix. */
	private final String prefix = "system/dept";

	/** The dept service. */
	@Autowired
	private ISysDeptService deptService;

	/**
	 * Dept.
	 *
	 * @return the string
	 */
	@RequiresPermissions("system:dept:view")
	@GetMapping()
	public String dept() {
		return prefix + "/dept";
	}

	/**
	 * List.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	@RequiresPermissions("system:dept:list")
	@PostMapping("/list")
	@ResponseBody
	public List<SysDept> list(SysDept dept) {
		final List<SysDept> deptList = deptService.selectDeptList(dept);
		return deptList;
	}

	/**
	 * Adds the.
	 *
	 * @param parentId the parent id
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/add/{parentId}")
	public String add(@PathVariable("parentId") Long parentId, ModelMap mmap) {
		mmap.put("dept", deptService.selectDeptById(parentId));
		return prefix + "/add";
	}

	/**
	 * Adds the save.
	 *
	 * @param dept the dept
	 * @return the ajax result
	 */
	@Log(title = "部门管理", businessType = BusinessType.INSERT)
	@RequiresPermissions("system:dept:add")
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(@Validated SysDept dept) {
		if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return error("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		}
		dept.setCreateBy(ShiroUtils.getLoginName());
		return toAjax(deptService.insertDept(dept));
	}

	/**
	 * Edits the.
	 *
	 * @param deptId the dept id
	 * @param mmap   the mmap
	 * @return the string
	 */
	@GetMapping("/edit/{deptId}")
	public String edit(@PathVariable("deptId") Long deptId, ModelMap mmap) {
		final SysDept dept = deptService.selectDeptById(deptId);
		if (StringUtils.isNotNull(dept) && 100L == deptId) {
			dept.setParentName("无");
		}
		mmap.put("dept", dept);
		return prefix + "/edit";
	}

	/**
	 * Edits the save.
	 *
	 * @param dept the dept
	 * @return the ajax result
	 */
	@Log(title = "部门管理", businessType = BusinessType.UPDATE)
	@RequiresPermissions("system:dept:edit")
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(@Validated SysDept dept) {
		if (UserConstants.DEPT_NAME_NOT_UNIQUE.equals(deptService.checkDeptNameUnique(dept))) {
			return error("修改部门'" + dept.getDeptName() + "'失败，部门名称已存在");
		} else if (dept.getParentId().equals(dept.getDeptId())) {
			return error("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
		} else if (org.apache.commons.lang3.StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus())
				&& deptService.selectNormalChildrenDeptById(dept.getDeptId()) > 0) {
			return AjaxResult.error("该部门包含未停用的子部门！");
		}
		dept.setUpdateBy(ShiroUtils.getLoginName());
		return toAjax(deptService.updateDept(dept));
	}

	/**
	 * Removes the.
	 *
	 * @param deptId the dept id
	 * @return the ajax result
	 */
	@Log(title = "部门管理", businessType = BusinessType.DELETE)
	@RequiresPermissions("system:dept:remove")
	@GetMapping("/remove/{deptId}")
	@ResponseBody
	public AjaxResult remove(@PathVariable("deptId") Long deptId) {
		if (deptService.selectDeptCount(deptId) > 0) {
			return AjaxResult.warn("存在下级部门,不允许删除");
		}
		if (deptService.checkDeptExistUser(deptId)) {
			return AjaxResult.warn("部门存在用户,不允许删除");
		}
		return toAjax(deptService.deleteDeptById(deptId));
	}

	/**
	 * Check dept name unique.
	 *
	 * @param dept the dept
	 * @return the string
	 */
	@PostMapping("/checkDeptNameUnique")
	@ResponseBody
	public String checkDeptNameUnique(SysDept dept) {
		return deptService.checkDeptNameUnique(dept);
	}

	/**
	 * Select dept tree.
	 *
	 * @param deptId    the dept id
	 * @param excludeId the exclude id
	 * @param mmap      the mmap
	 * @return the string
	 */
	@GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
	public String selectDeptTree(@PathVariable("deptId") Long deptId,
			@PathVariable(value = "excludeId", required = false) String excludeId, ModelMap mmap) {
		mmap.put("dept", deptService.selectDeptById(deptId));
		mmap.put("excludeId", excludeId);
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
		final List<Ztree> ztrees = deptService.selectDeptTree(new SysDept());
		return ztrees;
	}

	/**
	 * Tree data exclude child.
	 *
	 * @param excludeId the exclude id
	 * @return the list
	 */
	@GetMapping("/treeData/{excludeId}")
	@ResponseBody
	public List<Ztree> treeDataExcludeChild(@PathVariable(value = "excludeId", required = false) Long excludeId) {
		final SysDept dept = new SysDept();
		dept.setDeptId(excludeId);
		final List<Ztree> ztrees = deptService.selectDeptTreeExcludeChild(dept);
		return ztrees;
	}

	/**
	 * Dept tree data.
	 *
	 * @param role the role
	 * @return the list
	 */
	@GetMapping("/roleDeptTreeData")
	@ResponseBody
	public List<Ztree> deptTreeData(SysRole role) {
		final List<Ztree> ztrees = deptService.roleDeptTreeData(role);
		return ztrees;
	}
}
