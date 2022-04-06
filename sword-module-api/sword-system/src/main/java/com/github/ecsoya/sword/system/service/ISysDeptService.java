package com.github.ecsoya.sword.system.service;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.Ztree;
import com.github.ecsoya.sword.common.core.domain.entity.SysDept;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;

/**
 * The Interface ISysDeptService.
 */
public interface ISysDeptService {

	/**
	 * Select dept list.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	public List<SysDept> selectDeptList(SysDept dept);

	/**
	 * Select dept tree.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	public List<Ztree> selectDeptTree(SysDept dept);

	/**
	 * Select dept tree exclude child.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	public List<Ztree> selectDeptTreeExcludeChild(SysDept dept);

	/**
	 * Role dept tree data.
	 *
	 * @param role the role
	 * @return the list
	 */
	public List<Ztree> roleDeptTreeData(SysRole role);

	/**
	 * Select dept count.
	 *
	 * @param parentId the parent id
	 * @return the int
	 */
	public int selectDeptCount(Long parentId);

	/**
	 * Check dept exist user.
	 *
	 * @param deptId the dept id
	 * @return true, if successful
	 */
	public boolean checkDeptExistUser(Long deptId);

	/**
	 * Delete dept by id.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	public int deleteDeptById(Long deptId);

	/**
	 * Insert dept.
	 *
	 * @param dept the dept
	 * @return the int
	 */
	public int insertDept(SysDept dept);

	/**
	 * Update dept.
	 *
	 * @param dept the dept
	 * @return the int
	 */
	public int updateDept(SysDept dept);

	/**
	 * Select dept by id.
	 *
	 * @param deptId the dept id
	 * @return the sys dept
	 */
	public SysDept selectDeptById(Long deptId);

	/**
	 * Select normal children dept by id.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	public int selectNormalChildrenDeptById(Long deptId);

	/**
	 * Check dept name unique.
	 *
	 * @param dept the dept
	 * @return the string
	 */
	public String checkDeptNameUnique(SysDept dept);
}
