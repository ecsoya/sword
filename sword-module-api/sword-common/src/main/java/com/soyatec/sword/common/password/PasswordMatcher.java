package com.soyatec.sword.common.password;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.StringUtils;

public class PasswordMatcher {

	private static final ServiceLoader<PasswordEncryptService> loader = ServiceLoader
			.load(PasswordEncryptService.class);

	private static PasswordEncryptService getPasswordMatchService() {
		Iterator<PasswordEncryptService> it = loader.iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	public static boolean matches(SysUser user, String newPassword) {
		PasswordEncryptService service = getPasswordMatchService();
		if (service != null) {
			return service.matchesPassword(user, newPassword);
		}
		return user.getPassword().equals(encryptPassword(user, newPassword));
	}

	public static String encryptPassword(SysUser user, String password) {
		PasswordEncryptService service = getPasswordMatchService();
		if (service != null) {
			return service.encryptPassword(user, password);
		}
		return StringUtils.encryptPassword(user.getLoginName(), password, user.getSalt());
	}
}
