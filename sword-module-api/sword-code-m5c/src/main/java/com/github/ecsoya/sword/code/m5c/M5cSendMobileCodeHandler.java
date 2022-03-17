package com.github.ecsoya.sword.code.m5c;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.code.handler.SendMobileCodeHandler;
import com.github.ecsoya.sword.code.registry.IMobileCodeHandlerRegistry;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.google.auto.service.AutoService;

@Component
@AutoService(IMobileCodeHandlerRegistry.class)
public class M5cSendMobileCodeHandler implements SendMobileCodeHandler, IMobileCodeHandlerRegistry {

	private static final Logger log = LoggerFactory.getLogger(SendMobileCodeHandler.class);

	private static final String CHINESE_MOBILE_REGEX = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	private static final String DEFAULT_TEMPLATE = "亲爱的用户，您的短信验证码为：{}，请勿告诉他人，5分钟内有效！";

	@Autowired
	private M5cProperties properties;

	@Autowired
	private M5cMobileService mobileService;

	@Override
	public SendMobileCodeHandler get() {
		return SpringUtils.getBean(M5cSendMobileCodeHandler.class);
	}

	@Override
	public int getPriority() {
		return properties.getPriority();
	}

	@Override
	public CommonResult<?> sendCode(String mobile, String code) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("参数错误");
		}
		String template = properties.getTemplate(LocaleContextHolder.getLocale());
		if (StringUtils.isEmpty(template)) {
			template = DEFAULT_TEMPLATE;
		}
		final String message = StringUtils.formatAll(template, code);
		return sendMessage(mobile, message);
	}

	@Override
	public CommonResult<?> sendMessage(String mobile, String message) {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(message)) {
			return CommonResult.fail("参数错误");
		}
		if (isChineseMobile(mobile)) {
			mobile = "86" + mobile;
		}
		final M5cResult result = mobileService.sendMessage(mobile, message);
		log.info("m5c send sms to mobile {} with message {}, and result is: {}", mobile, message, result);
		if (result != null && result.isSuccess()) {
			return CommonResult.success(message);
		}
		return CommonResult.fail(result.getError());
	}

	private boolean isChineseMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		if (mobile.length() == 11) {
			final Pattern p = Pattern.compile(CHINESE_MOBILE_REGEX);
			final java.util.regex.Matcher m = p.matcher(mobile);
			return m.matches();
		}
		return false;
	}
}
