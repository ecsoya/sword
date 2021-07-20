package com.soyatec.sword.controller.system;

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

import com.soyatec.sword.common.annotation.Log;
import com.soyatec.sword.common.config.GlobalConfig;
import com.soyatec.sword.common.core.controller.BaseController;
import com.soyatec.sword.common.core.domain.AjaxResult;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.enums.BusinessType;
import com.soyatec.sword.common.enums.OperatorType;
import com.soyatec.sword.common.utils.ServletUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.service.IMailCodeService;
import com.soyatec.sword.service.IMobileCodeService;

/**
 * 登录验证
 *
 * @author Jin Liu (angryred@qq.com)
 */
@Controller
public class SysLoginController extends BaseController {

	@Autowired
	private GlobalConfig config;

	@Autowired(required = false)
	private IMailCodeService mailCodeService;

	@Autowired(required = false)
	private IMobileCodeService mobileCodeService;

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

	@GetMapping("/unauth")
	public String unauth() {
		return "error/unauth";
	}
}
