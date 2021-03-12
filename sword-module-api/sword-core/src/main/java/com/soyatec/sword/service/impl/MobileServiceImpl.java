package com.soyatec.sword.service.impl;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.handler.SendMobileCodeHandler;
import com.soyatec.sword.service.IMobileService;
import com.soyatec.sword.user.service.IUserProfileService;

@Service
public class MobileServiceImpl implements IMobileService {

	private static final Logger log = LoggerFactory.getLogger(MobileServiceImpl.class);

	@Autowired
	private IUserProfileService userService;

	@Autowired
	private RedisTemplate<String, Object> redis;

	@Autowired(required = false)
	private SendMobileCodeHandler sendMobileHandler;

	@Override
	public CommonResult<?> sendCode(String mobile, String content) {
		if (sendMobileHandler != null) {
			return sendMobileHandler.send(mobile, content);
		}
		return CommonResult.fail("没有实现");
	}

	@Override
	public CommonResult<?> sendCode(String mobile) {
		if (!StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("手机号为空"); //$NON-NLS-1$
		}
		Object code = redis.opsForValue().get(mobile);
		if (code == null) {
			code = StringUtils.randomNum(6);
			redis.opsForValue().set(mobile, code, Duration.ofMinutes(5));
			log.debug("cacheCode: " + redis.opsForValue().get(mobile));
		}
		log.debug("sendCode: {} = {}", mobile, code);
		return sendCode(mobile, code.toString());

	}

	@Override
	public CommonResult<?> verifyCode(String mobile, String code) {
		if (!StringUtils.isEmpty(mobile)) {
			return CommonResult.fail("手机号为空"); //$NON-NLS-1$
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail2")); //$NON-NLS-1$
		}
		Object cache = redis.opsForValue().get(mobile);
		if (Objects.equals(cache, code)) {
			return CommonResult.success(code);
		}
		return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail4")); //$NON-NLS-1$
	}

	@Override
	public CommonResult<?> sendCodeByUserId(Long userId) {
		String mobile = userService.selectUserMobileById(userId);
		return sendCode(mobile);
	}

	@Override
	public CommonResult<?> verifyCodeByUserId(Long userId, String code) {
		String mobile = userService.selectUserMobileById(userId);
		return verifyCode(mobile, code);
	}

	@Override
	public CommonResult<?> verifyCodeByUsername(String username, String code) {
		String mobile = userService.selectUserMobileByUsername(username);
		if (StringUtils.isBlank(mobile)) {
			mobile = username;
		}
		return verifyCode(mobile, code);
	}
}
