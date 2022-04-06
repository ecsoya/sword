package com.github.ecsoya.sword.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.entity.SysUser;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.service.SysRegisterService;
import com.github.ecsoya.sword.system.service.ISysConfigService;

/**
 * The Class SysRegisterController.
 */
//@Controller
public class SysRegisterController extends BaseController {

	/** The register service. */
	@Autowired
	private SysRegisterService registerService;

	/** The config service. */
	@Autowired
	private ISysConfigService configService;

	/**
	 * Register.
	 *
	 * @return the string
	 */
	@GetMapping("/register")
	public String register() {
		return "register";
	}

	/**
	 * Ajax register.
	 *
	 * @param user the user
	 * @return the ajax result
	 */
	@PostMapping("/register")
	@ResponseBody
	public AjaxResult ajaxRegister(SysUser user) {
		if (!("true".equals(configService.selectConfigValueByKey("sys.account.registerUser")))) {
			return error("当前系统没有开启注册功能！");
		}
		final String msg = registerService.register(user);
		return StringUtils.isEmpty(msg) ? success() : error(msg);
	}
}
