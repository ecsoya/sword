package com.soyatec.sword.common.password;

import com.soyatec.sword.common.core.domain.entity.SysUser;

public interface PasswordEncryptService {

	String encryptPassword(SysUser user, String password);

	boolean matchesPassword(SysUser user, String password);

}
