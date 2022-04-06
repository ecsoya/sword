package com.github.ecsoya.sword.framework.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;
import com.github.ecsoya.sword.framework.shiro.realm.UserRealm;

/**
 * The Class ShiroUtils.
 */
public class ShiroUtils {

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	/**
	 * Logout.
	 */
	public static void logout() {
		getSubject().logout();
	}

	/**
	 * Gets the sys user.
	 *
	 * @return the sys user
	 */
	public static SysUser getSysUser() {
		SysUser user = null;
		final Object obj = getSubject().getPrincipal();
		if (StringUtils.isNotNull(obj)) {
			user = new SysUser();
			BeanUtils.copyBeanProp(user, obj);
		}
		return user;
	}

	/**
	 * Sets the sys user.
	 *
	 * @param user the new sys user
	 */
	public static void setSysUser(SysUser user) {
		final Subject subject = getSubject();
		final PrincipalCollection principalCollection = subject.getPrincipals();
		final String realmName = principalCollection.getRealmNames().iterator().next();
		final PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
		// 重新加载Principal
		subject.runAs(newPrincipalCollection);
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public static Long getUserId() {
		return getSysUser().getUserId().longValue();
	}

	/**
	 * Gets the login name.
	 *
	 * @return the login name
	 */
	public static String getLoginName() {
		return getSysUser().getLoginName();
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public static String getIp() {
		return getSubject().getSession().getHost();
	}

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public static String getSessionId() {
		return String.valueOf(getSubject().getSession().getId());
	}

	/**
	 * Clear cached authorization info.
	 */
	public static void clearCachedAuthorizationInfo() {
		final RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
		final UserRealm realm = (UserRealm) rsm.getRealms().iterator().next();
		realm.clearAllCachedAuthorizationInfo();
	}

	/**
	 * Random salt.
	 *
	 * @return the string
	 */
	public static String randomSalt() {
		// 一个Byte占两个字节，此处生成的3字节，字符串长度为6
		final SecureRandomNumberGenerator secureRandom = new SecureRandomNumberGenerator();
		final String hex = secureRandom.nextBytes(3).toHex();
		return hex;
	}
}
