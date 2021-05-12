package com.soyatec.sword.framework.shiro.service;

import com.soyatec.sword.common.core.domain.entity.SysUser;

public interface SysPasswordEncryptService {

	String encryptPassword(SysUser user, String password);

	boolean matchesPassword(SysUser user, String password);

}
