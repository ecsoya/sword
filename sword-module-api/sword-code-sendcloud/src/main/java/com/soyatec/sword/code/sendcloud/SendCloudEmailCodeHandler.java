package com.soyatec.sword.code.sendcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sendcloud.sdk.util.ResponseData;
import com.soyatec.sword.code.handler.SendMailCodeHandler;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.StringUtils;

@Service
public class SendCloudEmailCodeHandler implements SendMailCodeHandler {
	private static final String DEFAULT_TEMPLATE = "亲爱的用户，您的验证码为：{}，请勿告诉他人，5分钟内有效！";

	@Autowired
	private SendCloudProperties properties;

	@Autowired
	private SendCloudService sendCloudService;

	@Override
	public CommonResult<?> sendEmail(String email, String subject, String content) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(content)) {
			return CommonResult.fail("参数错误");
		}
		try {
			ResponseData sent = sendCloudService.sendCommon(email, subject, content);
			if (sent.statusCode == 200) {
				return CommonResult.success();
			}
			return CommonResult.fail(sent.getInfo());
		} catch (Throwable e) {
			return CommonResult.fail(e.getLocalizedMessage());
		}
	}

	@Override
	public CommonResult<?> sendCode(String email, String code) {
		if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
			return CommonResult.fail("参数错误");
		}
		String subject = properties.getSubject();
		String template = properties.getTemplate();
		if (StringUtils.isEmpty(template)) {
			template = DEFAULT_TEMPLATE;
		}
		String content = StringUtils.format(template, code, code);
		return sendEmail(email, subject, content);
	}

}
