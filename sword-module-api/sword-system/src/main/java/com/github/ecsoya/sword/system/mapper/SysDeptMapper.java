package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.common.core.domain.entity.SysDept;

/**
 * The Interface SysDeptMapper.
 */
public interface SysDeptMapper {

	/**
	 * Select dept count.
	 *
	 * @param dept the dept
	 * @return the int
	 */
	public int selectDeptCount(SysDept dept);

	/**
	 * Check dept exist user.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	public int checkDeptExistUser(Long deptId);

	/**
	 * Select dept list.
	 *
	 * @param dept the dept
	 * @return the list
	 */
	public List<SysDept> selectDeptList(SysDept dept);

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
	 * Update dept children.
	 *
	 * @param depts the depts
	 * @return the int
	 */
	public int updateDeptChildren(@Param("depts") List<SysDept> depts);

	/**
	 * Select dept by id.
	 *
	 * @param deptId the dept id
	 * @return the sys dept
	 */
	public SysDept selectDeptById(Long deptId);

	/**
	 * Check dept name unique.
	 *
	 * @param deptName the dept name
	 * @param parentId the parent id
	 * @return the sys dept
	 */
	public SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId);

	/**
	 * Select role dept tree.
	 *
	 * @param roleId the role id
	 * @return the list
	 */
	public List<String> selectRoleDeptTree(Long roleId);

	/**
	 * Update dept status.
	 *
	 * @param dept the dept
	 */
	public void updateDeptStatus(SysDept dept);

	/**
	 * Select children dept by id.
	 *
	 * @param deptId the dept id
	 * @return the list
	 */
	public List<SysDept> selectChildrenDeptById(Long deptId);

	/**
	 * Select normal children dept by id.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	public int selectNormalChildrenDeptById(Long deptId);
}
