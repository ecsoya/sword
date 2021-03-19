package com.soyatec.sword.code.service.impl;

import java.time.Duration;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.soyatec.sword.code.handler.SendMailCodeHandler;
import com.soyatec.sword.code.service.IMailService;
import com.soyatec.sword.common.core.domain.CommonResult;
import com.soyatec.sword.common.utils.MessageUtils;
import com.soyatec.sword.common.utils.StringUtils;

@Service
public class MailServiceImpl implements IMailService {

	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private RedisTemplate<String, Object> redis;

	@Autowired(required = false)
	private SendMailCodeHandler sendMailHandler;

	@Override
	public CommonResult<?> sendEmail(String email, String subject, String content) {
		if (sendMailHandler != null) {
			return sendMailHandler.sendEmail(email, subject, content);
		}
		return CommonResult.fail("暂不支持");
	}

	@Override
	public CommonResult<?> sendCode(String email) {
		if (sendMailHandler == null) {
			return CommonResult.fail("暂不支持");
		}
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

		return sendMailHandler.sendCode(email, code.toString());

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

}
