package com.github.ecsoya.sword.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.common.annotation.DataScope;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.system.domain.SysRoleDept;
import com.github.ecsoya.sword.system.domain.SysRoleMenu;
import com.github.ecsoya.sword.system.domain.SysUserRole;
import com.github.ecsoya.sword.system.mapper.SysRoleDeptMapper;
import com.github.ecsoya.sword.system.mapper.SysRoleMapper;
import com.github.ecsoya.sword.system.mapper.SysRoleMenuMapper;
import com.github.ecsoya.sword.system.mapper.SysUserRoleMapper;
import com.github.ecsoya.sword.system.service.ISysRoleService;

/**
 * The Class SysRoleServiceImpl.
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

	/** The role mapper. */
	@Autowired
	private SysRoleMapper roleMapper;

	/** The role menu mapper. */
	@Autowired
	private SysRoleMenuMapper roleMenuMapper;

	/** The user role mapper. */
	@Autowired
	private SysUserRoleMapper userRoleMapper;

	/** The role dept mapper. */
	@Autowired
	private SysRoleDeptMapper roleDeptMapper;

	/**
	 * Select role list.
	 *
	 * @param role the role
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d")
	public List<SysRole> selectRoleList(SysRole role) {
		return roleMapper.selectRoleList(role);
	}

	/**
	 * Select role keys.
	 *
	 * @param userId the user id
	 * @return the sets the
	 */
	@Override
	public Set<String> selectRoleKeys(Long userId) {
		final List<SysRole> perms = roleMapper.selectRolesByUserId(userId);
		final Set<String> permsSet = new HashSet<>();
		for (final SysRole perm : perms) {
			if (StringUtils.isNotNull(perm)) {
				permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
			}
		}
		return permsSet;
	}

	/**
	 * Select roles by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<SysRole> selectRolesByUserId(Long userId) {
		final List<SysRole> userRoles = roleMapper.selectRolesByUserId(userId);
		final List<SysRole> roles = selectRoleAll();
		for (final SysRole role : roles) {
			for (final SysRole userRole : userRoles) {
				if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
					role.setFlag(true);
					break;
				}
			}
		}
		return roles;
	}

	/**
	 * Select role all.
	 *
	 * @return the list
	 */
	@Override
	public List<SysRole> selectRoleAll() {
		return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
	}

	/**
	 * Select role by id.
	 *
	 * @param roleId the role id
	 * @return the sys role
	 */
	@Override
	public SysRole selectRoleById(Long roleId) {
		return roleMapper.selectRoleById(roleId);
	}

	/**
	 * Delete role by id.
	 *
	 * @param roleId the role id
	 * @return true, if successful
	 */
	@Override
	@Transactional
	public boolean deleteRoleById(Long roleId) {
		// 删除角色与菜单关联
		roleMenuMapper.deleteRoleMenuByRoleId(roleId);
		// 删除角色与部门关联
		roleDeptMapper.deleteRoleDeptByRoleId(roleId);
		return roleMapper.deleteRoleById(roleId) > 0 ? true : false;
	}

	/**
	 * Delete role by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	@Transactional
	public int deleteRoleByIds(String ids) {
		final Long[] roleIds = Convert.toLongArray(ids);
		for (final Long roleId : roleIds) {
			checkRoleAllowed(new SysRole(roleId));
			final SysRole role = selectRoleById(roleId);
			if (countUserRoleByRoleId(roleId) > 0) {
				throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
			}
		}
		// 删除角色与菜单关联
		roleMenuMapper.deleteRoleMenu(roleIds);
		// 删除角色与部门关联
		roleDeptMapper.deleteRoleDept(roleIds);
		return roleMapper.deleteRoleByIds(roleIds);
	}

	/**
	 * Insert role.
	 *
	 * @param role the role
	 * @return the int
	 */
	@Override
	@Transactional
	public int insertRole(SysRole role) {
		// 新增角色信息
		roleMapper.insertRole(role);
		return insertRoleMenu(role);
	}

	/**
	 * Update role.
	 *
	 * @param role the role
	 * @return the int
	 */
	@Override
	@Transactional
	public int updateRole(SysRole role) {
		// 修改角色信息
		roleMapper.updateRole(role);
		// 删除角色与菜单关联
		roleMenuMapper.deleteRoleMenuByRoleId(role.getRoleId());
		return insertRoleMenu(role);
	}

	/**
	 * Auth data scope.
	 *
	 * @param role the role
	 * @return the int
	 */
	@Override
	@Transactional
	public int authDataScope(SysRole role) {
		// 修改角色信息
		roleMapper.updateRole(role);
		// 删除角色与部门关联
		roleDeptMapper.deleteRoleDeptByRoleId(role.getRoleId());
		// 新增角色和部门信息（数据权限）
		return insertRoleDept(role);
	}

	/**
	 * Insert role menu.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int insertRoleMenu(SysRole role) {
		int rows = 1;
		// 新增用户与角色管理
		final List<SysRoleMenu> list = new ArrayList<SysRoleMenu>();
		for (final Long menuId : role.getMenuIds()) {
			final SysRoleMenu rm = new SysRoleMenu();
			rm.setRoleId(role.getRoleId());
			rm.setMenuId(menuId);
			list.add(rm);
		}
		if (list.size() > 0) {
			rows = roleMenuMapper.batchRoleMenu(list);
		}
		return rows;
	}

	/**
	 * Insert role dept.
	 *
	 * @param role the role
	 * @return the int
	 */
	public int insertRoleDept(SysRole role) {
		int rows = 1;
		// 新增角色与部门（数据权限）管理
		final List<SysRoleDept> list = new ArrayList<SysRoleDept>();
		for (final Long deptId : role.getDeptIds()) {
			final SysRoleDept rd = new SysRoleDept();
			rd.setRoleId(role.getRoleId());
			rd.setDeptId(deptId);
			list.add(rd);
		}
		if (list.size() > 0) {
			rows = roleDeptMapper.batchRoleDept(list);
		}
		return rows;
	}

	/**
	 * Check role name unique.
	 *
	 * @param role the role
	 * @return the string
	 */
	@Override
	public String checkRoleNameUnique(SysRole role) {
		final Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		final SysRole info = roleMapper.checkRoleNameUnique(role.getRoleName());
		if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.ROLE_NAME_NOT_UNIQUE;
		}
		return UserConstants.ROLE_NAME_UNIQUE;
	}

	/**
	 * Check role key unique.
	 *
	 * @param role the role
	 * @return the string
	 */
	@Override
	public String checkRoleKeyUnique(SysRole role) {
		final Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
		final SysRole info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
		if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
			return UserConstants.ROLE_KEY_NOT_UNIQUE;
		}
		return UserConstants.ROLE_KEY_UNIQUE;
	}

	/**
	 * Check role allowed.
	 *
	 * @param role the role
	 */
	@Override
	public void checkRoleAllowed(SysRole role) {
		if (StringUtils.isNotNull(role.getRoleId()) && role.isAdmin()) {
			throw new BusinessException("不允许操作超级管理员角色");
		}
	}

	/**
	 * Count user role by role id.
	 *
	 * @param roleId the role id
	 * @return the int
	 */
	@Override
	public int countUserRoleByRoleId(Long roleId) {
		return userRoleMapper.countUserRoleByRoleId(roleId);
	}

	/**
	 * Change status.
	 *
	 * @param role the role
	 * @return the int
	 */
	@Override
	public int changeStatus(SysRole role) {
		return roleMapper.updateRole(role);
	}

	/**
	 * Delete auth user.
	 *
	 * @param userRole the user role
	 * @return the int
	 */
	@Override
	public int deleteAuthUser(SysUserRole userRole) {
		return userRoleMapper.deleteUserRoleInfo(userRole);
	}

	/**
	 * Delete auth users.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the int
	 */
	@Override
	public int deleteAuthUsers(Long roleId, String userIds) {
		return userRoleMapper.deleteUserRoleInfos(roleId, Convert.toLongArray(userIds));
	}

	/**
	 * Insert auth users.
	 *
	 * @param roleId  the role id
	 * @param userIds the user ids
	 * @return the int
	 */
	@Override
	public int insertAuthUsers(Long roleId, String userIds) {
		final Long[] users = Convert.toLongArray(userIds);
		// 新增用户与角色管理
		final List<SysUserRole> list = new ArrayList<SysUserRole>();
		for (final Long userId : users) {
			final SysUserRole ur = new SysUserRole();
			ur.setUserId(userId);
			ur.setRoleId(roleId);
			list.add(ur);
		}
		return userRoleMapper.batchUserRole(list);
	}

	/**
	 * Select role ids by role key.
	 *
	 * @param roleKey the role key
	 * @return the long[]
	 */
	@Override
	public Long[] selectRoleIdsByRoleKey(String roleKey) {
		if (StringUtils.isEmpty(roleKey)) {
			return new Long[0];
		}
		SysRole query = new SysRole();
		query.setRoleKey(roleKey);
		List<SysRole> list = roleMapper.selectRoleList(query);
		return list.stream().map(r -> r.getRoleId()).collect(Collectors.toList()).toArray(new Long[list.size()]);
	}
}
