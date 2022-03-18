package com.github.ecsoya.sword.framework.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.github.ecsoya.sword.framework.shiro.realm.UserRealm;

/**
 * 用户授权信息
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class AuthorizationUtils {
	/**
	 * 清理所有用户授权信息缓存
	 */
	public static void clearAllCachedAuthorizationInfo() {
		getUserRealm().clearAllCachedAuthorizationInfo();
	}

	/**
	 * 获取自定义Realm
	 */
	public static UserRealm getUserRealm() {
		final RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		return (UserRealm) rsm.getRealms().iterator().next();
	}
}