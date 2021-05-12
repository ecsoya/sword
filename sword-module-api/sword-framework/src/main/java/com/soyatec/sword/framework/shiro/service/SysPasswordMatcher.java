package com.soyatec.sword.framework.shiro.service;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.soyatec.sword.common.core.domain.entity.SysUser;
import com.soyatec.sword.common.utils.StringUtils;

public class SysPasswordMatcher {

	private static final ServiceLoader<SysPasswordEncryptService> loader = ServiceLoader
			.load(SysPasswordEncryptService.class);

	private static SysPasswordEncryptService getPasswordMatchService() {
		Iterator<SysPasswordEncryptService> it = loader.iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	public static boolean matches(SysUser user, String newPassword) {
		SysPasswordEncryptService service = getPasswordMatchService();
		if (service != null) {
			return service.matchesPassword(user, newPassword);
		}
		return user.getPassword().equals(encryptPassword(user, newPassword));
	}

	public static String encryptPassword(SysUser user, String password) {
		SysPasswordEncryptService service = getPasswordMatchService();
		if (service != null) {
			return service.encryptPassword(user, password);
		}
		return StringUtils.encryptPassword(user.getLoginName(), password, user.getSalt());
	}
}
