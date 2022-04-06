package com.github.ecsoya.sword.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.ecsoya.sword.common.annotation.Log;
import com.github.ecsoya.sword.common.config.GlobalConfig;
import com.github.ecsoya.sword.common.core.controller.BaseController;
import com.github.ecsoya.sword.common.core.domain.AjaxResult;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.enums.BusinessType;
import com.github.ecsoya.sword.common.enums.OperatorType;
import com.github.ecsoya.sword.common.utils.ServletUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.service.IMailCodeService;
import com.github.ecsoya.sword.service.IMobileCodeService;

/**
 * The Class SysLoginController.
 */
@Controller
public class SysLoginController extends BaseController {

	/** The config. */
	@Autowired
	private GlobalConfig config;

	/** The mail code service. */
	@Autowired(required = false)
	private IMailCodeService mailCodeService;

	/** The mobile code service. */
	@Autowired(required = false)
	private IMobileCodeService mobileCodeService;

	/**
	 * Login.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param path     the path
	 * @param mmap     the mmap
	 * @return the string
	 */
	@GetMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, String path, ModelMap mmap) {
		// 如果是Ajax请求，返回Json字符串。
		if (ServletUtils.isAjaxRequest(request)) {
			return ServletUtils.renderString(response, "{\"code\":\"1\",\"msg\":\"未登录或登录超时。请重新登录\"}");
		}
		mmap.put("config", config);
		mmap.put("path", path);
		return "login";
	}

	/**
	 * Ajax login.
	 *
	 * @param username   the username
	 * @param password   the password
	 * @param rememberMe the remember me
	 * @param notifyCode the notify code
	 * @return the ajax result
	 */
	@PostMapping("/login")
	@ResponseBody
	@Log(isSaveRequestData = true, title = "后台登录", businessType = BusinessType.GRANT, operatorType = OperatorType.MANAGE)
	public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe, String notifyCode) {
		if (GlobalConfig.isNotifyEnabled()) {
			if (GlobalConfig.getEmailCode()) {
				final CommonResult<?> verifyCodeByUsername = mailCodeService.verifyCodeByUsername(username, notifyCode);
				if (!verifyCodeByUsername.isSuccess()) {
					return error("邮箱验证码错误");
				}
			} else {
				final CommonResult<?> verifyCodeByUsername = mobileCodeService.verifyCodeByUsername(username,
						notifyCode);
				if (!verifyCodeByUsername.isSuccess()) {
					return error("短信验证码错误");
				}
			}
		}
		final UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		final Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return success();
		} catch (final AuthenticationException e) {
			String msg = "用户或密码错误";
			if (StringUtils.isNotEmpty(e.getMessage())) {
				msg = e.getMessage();
			}
			return error(msg);
		}
	}

	/**
	 * Unauth.
	 *
	 * @return the string
	 */
	@GetMapping("/unauth")
	public String unauth() {
		return "error/unauth";
	}
}
