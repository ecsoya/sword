package com.soyatec.sword.code.tencent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.code.handler.SendMailCodeHandler;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.tencentcloudapi.ses.v20201002.models.SendEmailResponse;

@Service
public class TencentCodeHandler implements SendMailCodeHandler {

	@Autowired
	private TencentCodeProperties config;

	@Autowired
	private TencentCodeService servie;

	@Override
	public CommonResult<?> sendEmail(String email, String subject, String content) {
		return CommonResult.fail("尚未实现");
	}

	@Override
	public CommonResult<?> sendCode(String email, String code) {
		SendEmailResponse sent = servie.sendCode(email, config.getSubject(), code, config.getHtmlTemplateId() != null);
		if (sent != null) {
			return CommonResult.success(sent);
		}
		return CommonResult.fail("发送失败");
	}

}
