package com.github.ecsoya.sword.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.system.domain.SysUserRole;

/**
 * The Interface SysUserRoleMapper.
 */
public interface SysUserRoleMapper {

	/**
	 * Select user role by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	public List<SysUserRole> selectUserRoleByUserId(Long userId);

	/**
	 * Delete user role by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int deleteUserRoleByUserId(Long userId);

	/**
	 * Delete user role.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserRole(Long[] ids);

	/**
	 * Count user role by role id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	public int countUserRoleByRoleId(Long roleId);

	/**
	 * Batch user role.
	 *
	 * @param userRoleList the user role list
	 * @return the int
	 */
	public int batchUserRole(List<SysUserRole> userRoleList);

	/**
	 * Delete user role info.
	 *
	 * @param userRole the user role
	 * @return the int
	 */
	public int deleteUserRoleInfo(SysUserRole userRole);

	/**
	 * Delete user role infos.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the int
	 */
	public int deleteUserRoleInfos(@Param("roleId") Long roleId, @Param("userIds") Long[] userIds);
}
