package com.github.ecsoya.sword.framework.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.github.ecsoya.sword.framework.shiro.realm.UserRealm;

/**
 * The Class AuthorizationUtils.
 */
public class AuthorizationUtils {

	/**
	 * Clear all cached authorization info.
	 */
	public static void clearAllCachedAuthorizationInfo() {
		getUserRealm().clearAllCachedAuthorizationInfo();
	}

	/**
	 * Gets the user realm.
	 *
	 * @return the user realm
	 */
	public static UserRealm getUserRealm() {
		final RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		return (UserRealm) rsm.getRealms().iterator().next();
	}
}
