package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import com.github.ecsoya.sword.common.core.domain.entity.SysRole;

/**
 * The Interface SysRoleMapper.
 */
public interface SysRoleMapper {

	/**
	 * Select role list.
	 *
	 * @param role the role
	 * @return the list
	 */
	public List<SysRole> selectRoleList(SysRole role);

	/**
	 * Select roles by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysRole> selectRolesByUserId(Long userId);

	/**
	 * Select role by id.
	 *
	 * @param roleId the role id
	 * @return the sys role
	 */
	public SysRole selectRoleById(Long roleId);

	/**
	 * Delete role by id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	public int deleteRoleById(Long roleId);

	/**
	 * Delete role by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteRoleByIds(Long[] ids);

	/**
	 * Update role.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int updateRole(SysRole role);

	/**
	 * Insert role.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int insertRole(SysRole role);

	/**
	 * Check role name unique.
	 *
	 * @param roleName the role name
	 * @return the sys role
	 */
	public SysRole checkRoleNameUnique(String roleName);

	/**
	 * Check role key unique.
	 *
	 * @param roleKey the role key
	 * @return the sys role
	 */
	public SysRole checkRoleKeyUnique(String roleKey);
}
