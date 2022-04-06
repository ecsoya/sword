package com.github.ecsoya.sword.common.password;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class PasswordMatcher.
 */
public class PasswordMatcher {

	/** The Constant loader. */
	private static final ServiceLoader<PasswordEncryptService> loader = ServiceLoader
			.load(PasswordEncryptService.class);

	/**
	 * Gets the password match service.
	 *
	 * @return the password match service
	 */
	private static PasswordEncryptService getPasswordMatchService() {
		Iterator<PasswordEncryptService> it = loader.iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	/**
	 * Matches.
	 *
	 * @param user        the user
	 * @param newPassword the new password
	 * @return true, if successful
	 */
	public static boolean matches(SysUser user, String newPassword) {
		PasswordEncryptService service = getPasswordMatchService();
		if (service != null) {
			return service.matchesPassword(user, newPassword);
		}
		return user.getPassword().equals(encryptPassword(user, newPassword));
	}

	/**
	 * Encrypt password.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return the string
	 */
	public static String encryptPassword(SysUser user, String password) {
		PasswordEncryptService service = getPasswordMatchService();
		if (service != null) {
			return service.encryptPassword(user, password);
		}
		return StringUtils.encryptPassword(user.getLoginName(), password, user.getSalt());
	}
}
