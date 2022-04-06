package com.github.ecsoya.sword.framework.shiro.web.filter.captcha;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;

import com.github.ecsoya.sword.common.constant.ShiroConstants;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.framework.shiro.util.ShiroUtils;
import com.google.code.kaptcha.Constants;

/**
 * The Class CaptchaValidateFilter.
 */
public class CaptchaValidateFilter extends AccessControlFilter {

	/** The captcha enabled. */
	private boolean captchaEnabled = false;

	/** The captcha type. */
	private String captchaType = "math";

	/**
	 * Sets the captcha enabled.
	 *
	 * @param captchaEnabled the new captcha enabled
	 */
	public void setCaptchaEnabled(boolean captchaEnabled) {
		this.captchaEnabled = captchaEnabled;
	}

	/**
	 * Sets the captcha type.
	 *
	 * @param captchaType the new captcha type
	 */
	public void setCaptchaType(String captchaType) {
		this.captchaType = captchaType;
	}

	/**
	 * On pre handle.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param mappedValue the mapped value
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		request.setAttribute(ShiroConstants.CURRENT_ENABLED, captchaEnabled);
		request.setAttribute(ShiroConstants.CURRENT_TYPE, captchaType);
		return super.onPreHandle(request, response, mappedValue);
	}

	/**
	 * Checks if is access allowed.
	 *
	 * @param request     the request
	 * @param response    the response
	 * @param mappedValue the mapped value
	 * @return true, if is access allowed
	 * @throws Exception the exception
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		// 验证码禁用 或不是表单提交 允许访问
		if (captchaEnabled == false || !"post".equals(httpServletRequest.getMethod().toLowerCase())) {
			return true;
		}
		return validateResponse(httpServletRequest,
				httpServletRequest.getParameter(ShiroConstants.CURRENT_VALIDATECODE));
	}

	/**
	 * Validate response.
	 *
	 * @param request      the request
	 * @param validateCode the validate code
	 * @return true, if successful
	 */
	public boolean validateResponse(HttpServletRequest request, String validateCode) {
		final Object obj = ShiroUtils.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		final String code = String.valueOf(obj != null ? obj : "");
		// 验证码清除，防止多次使用。
		request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		if (StringUtils.isEmpty(validateCode) || !validateCode.equalsIgnoreCase(code)) {
			return false;
		}
		return true;
	}

	/**
	 * On access denied.
	 *
	 * @param request  the request
	 * @param response the response
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		request.setAttribute(ShiroConstants.CURRENT_CAPTCHA, ShiroConstants.CAPTCHA_ERROR);
		return true;
	}
}
