package com.github.ecsoya.sword.system.service;

import java.util.List;
import java.util.Set;

import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.system.domain.SysUserRole;

/**
 * The Interface ISysRoleService.
 */
public interface ISysRoleService {

	/**
	 * Select role list.
	 *
	 * @param role the role
	 * @return the list
	 */
	public List<SysRole> selectRoleList(SysRole role);

	/**
	 * Select role keys.
	 *
	 * @param userId the user id
	 * @return the sets the
	 */
	public Set<String> selectRoleKeys(Long userId);

	/**
	 * Select roles by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysRole> selectRolesByUserId(Long userId);

	/**
	 * Select role all.
	 *
	 * @return the list
	 */
	public List<SysRole> selectRoleAll();

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
	 * @return true, if successful
	 */
	public boolean deleteRoleById(Long roleId);

	/**
	 * Delete role by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteRoleByIds(String ids);

	/**
	 * Insert role.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int insertRole(SysRole role);

	/**
	 * Update role.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int updateRole(SysRole role);

	/**
	 * Auth data scope.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int authDataScope(SysRole role);

	/**
	 * Check role name unique.
	 *
	 * @param role the role
	 * @return the string
	 */
	public String checkRoleNameUnique(SysRole role);

	/**
	 * Check role key unique.
	 *
	 * @param role the role
	 * @return the string
	 */
	public String checkRoleKeyUnique(SysRole role);

	/**
	 * Check role allowed.
	 *
	 * @param role the role
	 */
	public void checkRoleAllowed(SysRole role);

	/**
	 * Count user role by role id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	public int countUserRoleByRoleId(Long roleId);

	/**
	 * Change status.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int changeStatus(SysRole role);

	/**
	 * Delete auth user.
	 *
	 * @param userRole the user role
	 * @return the int
	 */
	public int deleteAuthUser(SysUserRole userRole);

	/**
	 * Delete auth users.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteAuthUsers(Long roleId, String userIds);

	/**
	 * Insert auth users.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the int
	 */
	public int insertAuthUsers(Long roleId, String userIds);

	/**
	 * Select role ids by role key.
	 *
	 * @param roleKey the role key
	 * @return the long[]
	 */
	public Long[] selectRoleIdsByRoleKey(String roleKey);
}