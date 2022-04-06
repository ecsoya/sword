package com.github.ecsoya.sword.message.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.bean.BeanUtils;

/**
 * The Class AbstractMessageController.
 */
public class AbstractMessageController extends BaseController {

	/**
	 * Gets the subject.
	 *
	 * @return the subject
	 */
	protected Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	protected Long getUserId() {
		return getSysUser().getUserId().longValue();
	}

	/**
	 * Gets the sys user.
	 *
	 * @return the sys user
	 */
	protected SysUser getSysUser() {
		SysUser user = null;
		final Object obj = getSubject().getPrincipal();
		if (StringUtils.isNotNull(obj)) {
			user = new SysUser();
			BeanUtils.copyBeanProp(user, obj);
		}
		return user;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	protected String getUserName() {
		return getSysUser().getUserName();
	}
}
