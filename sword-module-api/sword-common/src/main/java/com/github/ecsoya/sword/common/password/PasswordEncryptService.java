package com.github.ecsoya.sword.common.password;

import com.github.ecsoya.sword.common.core.domain.entity.SysUser;

public interface PasswordEncryptService {

	String encryptPassword(SysUser user, String password);

	boolean matchesPassword(SysUser user, String password);

}
