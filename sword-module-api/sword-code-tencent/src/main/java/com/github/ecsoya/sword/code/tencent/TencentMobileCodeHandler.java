package com.github.ecsoya.sword.code.tencent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.ecsoya.sword.code.handler.SendMobileCodeHandler;
import com.github.ecsoya.sword.code.registry.IMobileCodeHandlerRegistry;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.google.auto.service.AutoService;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

@Component
@AutoService(IMobileCodeHandlerRegistry.class)
public class TencentMobileCodeHandler implements SendMobileCodeHandler, IMobileCodeHandlerRegistry {

	@Autowired
	private TencentCodeProperties config;

	@Autowired
	private TencentMobileCodeService servie;

	@Override
	public SendMobileCodeHandler get() {
		return SpringUtils.getBean(TencentMobileCodeHandler.class);
	}

	@Override
	public int getPriority() {
		return config.getPriority();
	}

	@Override
	public CommonResult<?> sendCode(String mobile, String code) {
		final SendSmsResponse sent = servie.sendCode(config.getTemplateId(), code, mobile);
		if (sent != null) {
			return CommonResult.success(sent);
		}
		return CommonResult.fail("发送失败");
	}

	@Override
	public CommonResult<?> sendMessage(String mobile, String message) {
		return CommonResult.fail("暂不支持");
	}

}
