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
import com.soyatec.sword.handler.SendMailCodeHandler;
import com.soyatec.sword.service.IMailService;
import com.soyatec.sword.user.service.IUserProfileService;

@Service
public class MailServiceImpl implements IMailService {

	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private IUserProfileService userService;

	@Autowired
	private RedisTemplate<String, Object> redis;

	@Autowired(required = false)
	private SendMailCodeHandler sendMailHandler;

	@Override
	public CommonResult<?> sendCode(String email, String subject, String code) {
		if (sendMailHandler != null) {
			return sendMailHandler.send(email, subject, code);
		}
		return CommonResult.fail("没有实现");
	}

	@Override
	public CommonResult<?> sendCode(String email) {
		if (!StringUtils.isValidEmail(email)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail")); //$NON-NLS-1$
		}
		Object code = redis.opsForValue().get(email);
		if (code == null) {
			code = StringUtils.randomNum(6);
			redis.opsForValue().set(email, code, Duration.ofMinutes(5));
			log.debug("cacheCode: " + redis.opsForValue().get(email));
		}
		log.debug("sendCode: {} = {}", email, code);
		String subject = MessageUtils.message("mail.service.title");

		return sendCode(email, subject, code.toString());

	}

	@Override
	public CommonResult<?> verifyCode(String email, String code) {
		if (!StringUtils.isValidEmail(email)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail1")); //$NON-NLS-1$
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail2")); //$NON-NLS-1$
		}
		Object cache = redis.opsForValue().get(email);
		if (Objects.equals(cache, code)) {
			return CommonResult.success(code);
		}
		return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail4")); //$NON-NLS-1$
	}

	@Override
	public CommonResult<?> sendCodeByUserId(Long userId) {
		String email = userService.selectUserEmailById(userId);
		return sendCode(email);
	}

	@Override
	public CommonResult<?> verifyCodeByUserId(Long userId, String code) {
		String email = userService.selectUserEmailById(userId);
		return verifyCode(email, code);
	}

	@Override
	public CommonResult<?> verifyCodeByUsername(String username, String code) {
		String email = userService.selectUserEmailByUsername(username);
		if (StringUtils.isBlank(email)) {
			email = username;
		}
		return verifyCode(email, code);
	}
}
