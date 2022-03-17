package com.github.ecsoya.sword.message.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;

public class AbstractMessageController extends BaseController {

	protected Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	protected Long getUserId() {
		return getSysUser().getUserId().longValue();
	}

	protected SysUser getSysUser() {
		SysUser user = null;
		final Object obj = getSubject().getPrincipal();
		if (StringUtils.isNotNull(obj)) {
			user = new SysUser();
			BeanUtils.copyBeanProp(user, obj);
		}
		return user;
	}

	protected String getUserName() {
		return getSysUser().getUserName();
	}
}
