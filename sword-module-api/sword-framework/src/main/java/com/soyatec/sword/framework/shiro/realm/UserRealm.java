package com.soyatec.sword.framework.shiro.realm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.exception.user.CaptchaException;
import com.soyatec.sword.common.exception.user.RoleBlockedException;
import com.soyatec.sword.common.exception.user.UserBlockedException;
import com.soyatec.sword.common.exception.user.UserNotExistsException;
import com.soyatec.sword.common.exception.user.UserPasswordNotMatchException;
import com.soyatec.sword.common.exception.user.UserPasswordRetryLimitExceedException;
import com.soyatec.sword.framework.shiro.service.SysLoginService;
import com.soyatec.sword.framework.shiro.util.ShiroUtils;
import com.soyatec.sword.system.service.ISysMenuService;
import com.soyatec.sword.system.service.ISysRoleService;

/**
 * 自定义Realm 处理登录 权限
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class UserRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private ISysMenuService menuService;

	@Autowired
	private ISysRoleService roleService;

	@Autowired
	private SysLoginService loginService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		final SysUser user = ShiroUtils.getSysUser();
		// 角色列表
		Set<String> roles = new HashSet<String>();
		// 功能列表
		Set<String> menus = new HashSet<String>();
		final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 管理员拥有所有权限
		if (user.isAdmin()) {
			info.addRole("admin");
			info.addStringPermission("*:*:*");
		} else {
			roles = roleService.selectRoleKeys(user.getUserId());
			menus = menuService.selectPermsByUserId(user.getUserId());
			// 角色加入AuthorizationInfo认证对象
			info.setRoles(roles);
			// 权限加入AuthorizationInfo认证对象
			info.setStringPermissions(menus);
		}
		return info;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		final String username = upToken.getUsername();
		String password = "";
		if (upToken.getPassword() != null) {
			password = new String(upToken.getPassword());
		}
		SysUser user = null;
		final List<String> userTypes = new ArrayList<>();
		final boolean withoutPassword = false;
		if (token instanceof TypedAuthenticationToken) {
			userTypes.addAll(Arrays.asList(((TypedAuthenticationToken) token).getUserTypes()));
		} else if (token instanceof AnonymousAuthenticationToken) {
			user = ((AnonymousAuthenticationToken) token).getUser();
		}
		if (user == null) {
			try {
				user = loginService.login(username, password, userTypes, withoutPassword);
			} catch (final CaptchaException e) {
				throw new AuthenticationException(e.getMessage(), e);
			} catch (final UserNotExistsException e) {
				throw new UnknownAccountException(e.getMessage(), e);
			} catch (final UserPasswordNotMatchException e) {
				throw new IncorrectCredentialsException(e.getMessage(), e);
			} catch (final UserPasswordRetryLimitExceedException e) {
				throw new ExcessiveAttemptsException(e.getMessage(), e);
			} catch (final UserBlockedException e) {
				throw new LockedAccountException(e.getMessage(), e);
			} catch (final RoleBlockedException e) {
				throw new LockedAccountException(e.getMessage(), e);
			} catch (final Exception e) {
				log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
				throw new AuthenticationException(e.getMessage(), e);
			}
		}
		final SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
		return info;
	}

	/**
	 * 清理指定用户授权信息缓存
	 */
	public void clearCachedAuthorizationInfo(Object principal) {
		final SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		this.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清理所有用户授权信息缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
		final Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (final Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
}
