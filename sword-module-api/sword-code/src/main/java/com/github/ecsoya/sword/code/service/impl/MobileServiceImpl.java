package com.github.ecsoya.sword.code.service.impl;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.code.handler.SendMobileCodeHandler;
import com.github.ecsoya.sword.code.service.IMobileService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;

@Service
public class MobileServiceImpl implements IMobileService {

	private static final Logger log = LoggerFactory.getLogger(MobileServiceImpl.class);

	@Autowired
	private RedisTemplate<String, Object> redis;

	@Autowired(required = false)
	private SendMobileCodeHandler sendMobileHandler;

	@Override
	public CommonResult<?> sendMessage(String mobile, String message) {
		if (sendMobileHandler != null) {
			return sendMobileHandler.sendMessage(mobile, message);
		}
		return CommonResult.fail("暂不支持");
	}

	@Override
	public CommonResult<?> sendCode(String mobile) {
		if (sendMobileHandler == null) {
			return CommonResult.fail("暂不支持");
		}
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("手机号为空"); //$NON-NLS-1$
		}
		Object code = redis.opsForValue().get(mobile);
		if (code == null) {
			code = StringUtils.randomNum(6);
			redis.opsForValue().set(mobile, code, Duration.ofMinutes(5));
			log.debug("cacheCode: " + redis.opsForValue().get(mobile));
		}
		log.debug("sendCode: {} = {}", mobile, code);
		return sendMobileHandler.sendCode(mobile, code.toString());

	}

	@Override
	public CommonResult<?> verifyCode(String mobile, String code) {
		if (StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("手机号为空"); //$NON-NLS-1$
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail2")); //$NON-NLS-1$
		}
		final Object cache = redis.opsForValue().get(mobile);
		if (Objects.equals(cache, code)) {
			return CommonResult.success(code);
		}
		return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail4")); //$NON-NLS-1$
	}

}
