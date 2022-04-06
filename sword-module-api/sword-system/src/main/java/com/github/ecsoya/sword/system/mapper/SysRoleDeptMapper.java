package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.system.domain.SysRoleDept;

/**
 * The Interface SysRoleDeptMapper.
 */
public interface SysRoleDeptMapper {

	/**
	 * Delete role dept by role id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	public int deleteRoleDeptByRoleId(Long roleId);

	/**
	 * Delete role dept.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteRoleDept(Long[] ids);

	/**
	 * Select count role dept by dept id.
	 *
	 * @param deptId the dept id
	 * @return the int
	 */
	public int selectCountRoleDeptByDeptId(Long deptId);

	/**
	 * Batch role dept.
	 *
	 * @param roleDeptList the role dept list
	 * @return the int
	 */
	public int batchRoleDept(List<SysRoleDept> roleDeptList);
}
