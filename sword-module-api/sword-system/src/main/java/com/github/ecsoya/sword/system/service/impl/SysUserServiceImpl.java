package com.github.ecsoya.sword.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ecsoya.sword.common.annotation.DataScope;
import com.github.ecsoya.sword.common.constant.UserConstants;
import com.github.ecsoya.sword.common.core.domain.entity.SysRole;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.exception.BusinessException;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.security.Md5Utils;
import com.github.ecsoya.sword.system.domain.SysPost;
import com.github.ecsoya.sword.system.domain.SysUserRole;
import com.github.ecsoya.sword.system.mapper.SysPostMapper;
import com.github.ecsoya.sword.system.mapper.SysRoleMapper;
import com.github.ecsoya.sword.system.mapper.SysUserMapper;
import com.github.ecsoya.sword.system.mapper.SysUserPostMapper;
import com.github.ecsoya.sword.system.mapper.SysUserRoleMapper;
import com.github.ecsoya.sword.system.service.ISysConfigService;
import com.github.ecsoya.sword.system.service.ISysUserService;

/**
 * The Class SysUserServiceImpl.
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(SysUserServiceImpl.class);

	/** The user mapper. */
	@Autowired
	private SysUserMapper userMapper;

	/** The role mapper. */
	@Autowired
	private SysRoleMapper roleMapper;

	/** The post mapper. */
	@Autowired
	private SysPostMapper postMapper;

	/** The user post mapper. */
	@Autowired
	private SysUserPostMapper userPostMapper;

	/** The user role mapper. */
	@Autowired
	private SysUserRoleMapper userRoleMapper;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/**
	 * Select user list.
	 *
	 * @param user the user
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectUserList(SysUser user) {
		return userMapper.selectUserList(user);
	}

	/**
	 * Select allocated list.
	 *
	 * @param user the user
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectAllocatedList(SysUser user) {
		return userMapper.selectAllocatedList(user);
	}

	/**
	 * Select unallocated list.
	 *
	 * @param user the user
	 * @return the list
	 */
	@Override
	@DataScope(deptAlias = "d", userAlias = "u")
	public List<SysUser> selectUnallocatedList(SysUser user) {
		return userMapper.selectUnallocatedList(user);
	}

	/**
	 * Select user by login name.
	 *
	 * @param userName the user name
	 * @return the sys user
	 */
	@Override
	public SysUser selectUserByLoginName(String userName) {
		return userMapper.selectUserByLoginName(userName);
	}

	/**
	 * Select user by phone number.
	 *
	 * @param phoneNumber the phone number
	 * @return the sys user
	 */
	@Override
	public SysUser selectUserByPhoneNumber(String phoneNumber) {
		return userMapper.selectUserByPhoneNumber(phoneNumber);
	}

	/**
	 * Select user by email.
	 *
	 * @param email the email
	 * @return the sys user
	 */
	@Override
	public SysUser selectUserByEmail(String email) {
		return userMapper.selectUserByEmail(email);
	}

	/**
	 * Select user by id.
	 *
	 * @param userId the user id
	 * @return the sys user
	 */
	@Override
	public SysUser selectUserById(Long userId) {
		return userMapper.selectUserById(userId);
	}

	/**
	 * Select user role by user id.
	 *
	 * @param userId the user id
	 * @return the list
	 */
	@Override
	public List<SysUserRole> selectUserRoleByUserId(Long userId) {
		return userRoleMapper.selectUserRoleByUserId(userId);
	}

	/**
	 * Delete user by id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	@Transactional
	public int deleteUserById(Long userId) {
		// 删除用户与角色关联
		userRoleMapper.deleteUserRoleByUserId(userId);
		// 删除用户与岗位表
		userPostMapper.deleteUserPostByUserId(userId);
		return userMapper.deleteUserById(userId);
	}

	/**
	 * Delete user by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	@Transactional
	public int deleteUserByIds(String ids) {
		final Long[] userIds = Convert.toLongArray(ids);
		for (final Long userId : userIds) {
			checkUserAllowed(new SysUser(userId));
		}
		// 删除用户与角色关联
		userRoleMapper.deleteUserRole(userIds);
		// 删除用户与岗位关联
		userPostMapper.deleteUserPost(userIds);
		return userMapper.deleteUserByIds(userIds);
	}

	/**
	 * Insert user.
	 *
	 * @param user the user
	 * @return the int
	 */
	@Override
	@Transactional
	public int insertUser(SysUser user) {
		// 新增用户信息
		final int rows = userMapper.insertUser(user);
		// 新增用户岗位关联
		insertUserPost(user);
		// 新增用户与角色管理
		insertUserRole(user.getUserId(), user.getRoleIds());
		return rows;
	}

	/**
	 * Register user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean registerUser(SysUser user) {
		user.setUserType(UserConstants.REGISTER_USER_TYPE);
		return userMapper.insertUser(user) > 0;
	}

	/**
	 * Update user.
	 *
	 * @param user the user
	 * @return the int
	 */
	@Override
	@Transactional
	public int updateUser(SysUser user) {
		final Long userId = user.getUserId();
		// 删除用户与角色关联
		userRoleMapper.deleteUserRoleByUserId(userId);
		// 新增用户与角色管理
		insertUserRole(user.getUserId(), user.getRoleIds());
		// 删除用户与岗位关联
		userPostMapper.deleteUserPostByUserId(userId);
		// 新增用户与岗位管理
		insertUserPost(user);
		return userMapper.updateUser(user);
	}

	/**
	 * Update user info.
	 *
	 * @param user the user
	 * @return the int
	 */
	@Override
	public int updateUserInfo(SysUser user) {
		return userMapper.updateUser(user);
	}

	/**
	 * Insert user auth.
	 *
	 * @param userId  the user id
	 * @param roleIds the role ids
	 */
	@Override
	public void insertUserAuth(Long userId, Long[] roleIds) {
		userRoleMapper.deleteUserRoleByUserId(userId);
		insertUserRole(userId, roleIds);
	}

	/**
	 * Reset user pwd.
	 *
	 * @param user the user
	 * @return the int
	 */
	@Override
	public int resetUserPwd(SysUser user) {
		return updateUserInfo(user);
	}

	/**
	 * Insert user role.
	 *
	 * @param userId  the user id
	 * @param roleIds the role ids
	 */
	public void insertUserRole(Long userId, Long[] roleIds) {
		if (StringUtils.isNotNull(roleIds)) {
			// 新增用户与角色管理
			final List<SysUserRole> list = new ArrayList<SysUserRole>();
			for (final Long roleId : roleIds) {
				final SysUserRole ur = new SysUserRole();
				ur.setUserId(userId);
				ur.setRoleId(roleId);
				list.add(ur);
			}
			if (list.size() > 0) {
				userRoleMapper.batchUserRole(list);
			}
		}
	}

	/**
	 * Insert user post.
	 *
	 * @param user the user
	 */
	public void insertUserPost(SysUser user) {
	}

	/**
	 * Check login name unique.
	 *
	 * @param loginName the login name
	 * @return the string
	 */
	@Override
	public String checkLoginNameUnique(String loginName) {
		final int count = userMapper.checkLoginNameUnique(loginName);
		if (count > 0) {
			return UserConstants.USER_NAME_NOT_UNIQUE;
		}
		return UserConstants.USER_NAME_UNIQUE;
	}

	/**
	 * Check phone unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	@Override
	public String checkPhoneUnique(SysUser user) {
		final Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		final SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber(), user.getUserType());
		if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.USER_PHONE_NOT_UNIQUE;
		}
		return UserConstants.USER_PHONE_UNIQUE;
	}

	/**
	 * Check email unique.
	 *
	 * @param user the user
	 * @return the string
	 */
	@Override
	public String checkEmailUnique(SysUser user) {
		final Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
		final SysUser info = userMapper.checkEmailUnique(user.getEmail(), user.getUserType());
		if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
			return UserConstants.USER_EMAIL_NOT_UNIQUE;
		}
		return UserConstants.USER_EMAIL_UNIQUE;
	}

	/**
	 * Check user allowed.
	 *
	 * @param user the user
	 */
	@Override
	public void checkUserAllowed(SysUser user) {
		if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
			throw new BusinessException("不允许操作超级管理员用户");
		}
	}

	/**
	 * Select user role group.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	@Override
	public String selectUserRoleGroup(Long userId) {
		final List<SysRole> list = roleMapper.selectRolesByUserId(userId);
		final StringBuffer idsStr = new StringBuffer();
		for (final SysRole role : list) {
			idsStr.append(role.getRoleName()).append(",");
		}
		if (StringUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * Select user post group.
	 *
	 * @param userId the user id
	 * @return the string
	 */
	@Override
	public String selectUserPostGroup(Long userId) {
		final List<SysPost> list = postMapper.selectPostsByUserId(userId);
		final StringBuffer idsStr = new StringBuffer();
		for (final SysPost post : list) {
			idsStr.append(post.getPostName()).append(",");
		}
		if (StringUtils.isNotEmpty(idsStr.toString())) {
			return idsStr.substring(0, idsStr.length() - 1);
		}
		return idsStr.toString();
	}

	/**
	 * Import user.
	 *
	 * @param userList        the user list
	 * @param isUpdateSupport the is update support
	 * @param operName        the oper name
	 * @return the string
	 */
	@Override
	public String importUser(List<SysUser> userList, Boolean isUpdateSupport, String operName) {
		if (StringUtils.isNull(userList) || userList.size() == 0) {
			throw new BusinessException("导入用户数据不能为空！");
		}
		int successNum = 0;
		int failureNum = 0;
		final StringBuilder successMsg = new StringBuilder();
		final StringBuilder failureMsg = new StringBuilder();
		final String password = configService.selectConfigValueByKey("sys.user.initPassword");
		for (final SysUser user : userList) {
			try {
				// 验证是否存在这个用户
				final SysUser u = userMapper.selectUserByLoginName(user.getLoginName());
				if (StringUtils.isNull(u)) {
					user.setPassword(Md5Utils.hash(user.getLoginName() + password));
					user.setCreateBy(operName);
					this.insertUser(user);
					successNum++;
					successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
				} else if (isUpdateSupport) {
					user.setUpdateBy(operName);
					this.updateUser(user);
					successNum++;
					successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
				} else {
					failureNum++;
					failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
				}
			} catch (final Exception e) {
				failureNum++;
				final String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
				failureMsg.append(msg + e.getMessage());
				log.error(msg, e);
			}
		}
		if (failureNum > 0) {
			failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
			throw new BusinessException(failureMsg.toString());
		} else {
			successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
		}
		return successMsg.toString();
	}

	/**
	 * Change status.
	 *
	 * @param user the user
	 * @return the int
	 */
	@Override
	public int changeStatus(SysUser user) {
		return userMapper.updateUser(user);
	}
}
