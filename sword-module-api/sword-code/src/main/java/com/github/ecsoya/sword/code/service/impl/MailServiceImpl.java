package com.github.ecsoya.sword.code.service.impl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.code.handler.SendMailCodeHandler;
import com.github.ecsoya.sword.code.registry.IMailCodeHandlerRegistry;
import com.github.ecsoya.sword.code.service.IMailService;
import com.github.ecsoya.sword.common.core.domain.CommonResult;
import com.github.ecsoya.sword.common.utils.MessageUtils;
import com.github.ecsoya.sword.common.utils.StringUtils;

/**
 * The Class MailServiceImpl.
 */
@Service
public class MailServiceImpl implements IMailService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

	/** The redis. */
	@Autowired
	private RedisTemplate<String, Object> redis;

	/** The Constant loader. */
	private static final ServiceLoader<IMailCodeHandlerRegistry> loader = ServiceLoader
			.load(IMailCodeHandlerRegistry.class);

	/** The handlers. */
	private List<SendMailCodeHandler> handlers;

	/**
	 * Gets the handlers.
	 *
	 * @return the handlers
	 */
	private List<SendMailCodeHandler> getHandlers() {
		if (handlers == null) {
			handlers = new ArrayList<SendMailCodeHandler>();
			final Iterator<IMailCodeHandlerRegistry> iterator = loader.iterator();
			while (iterator.hasNext()) {
				final IMailCodeHandlerRegistry registry = iterator.next();
				final SendMailCodeHandler handler = registry.get();
				if (handler != null) {
					handlers.add(handler);
				}
			}
			handlers.sort(new Comparator<SendMailCodeHandler>() {

				@Override
				public int compare(SendMailCodeHandler o1, SendMailCodeHandler o2) {
					return -Integer.compare(o1.getPriority(), o2.getPriority());
				}
			});
		}
		return handlers;
	}

	/**
	 * Without handlers.
	 *
	 * @return true, if successful
	 */
	private boolean withoutHandlers() {
		return getHandlers().isEmpty();
	}

	/**
	 * Send email.
	 *
	 * @param email   the email
	 * @param subject the subject
	 * @param content the content
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendEmail(String email, String subject, String content) {
		if (withoutHandlers()) {
			return CommonResult.fail("暂不支持");
		}
		boolean success = false;
		for (final SendMailCodeHandler handler : handlers) {
			final CommonResult<?> sent = handler.sendEmail(email, subject, content);
			if (sent != null && sent.isSuccess()) {
				success = true;
				break;
			}
		}
		if (success) {
			return CommonResult.success("发送成功");
		}
		return CommonResult.fail("发送失败");
	}

	/**
	 * Send code.
	 *
	 * @param email the email
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCode(String email) {
		return sendCode(email, null, null);
	}

	/**
	 * Send code.
	 *
	 * @param email    the email
	 * @param subject  the subject
	 * @param template the template
	 * @return the common result
	 */
	@Override
	public CommonResult<?> sendCode(String email, String subject, String template) {
		if (withoutHandlers()) {
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
		boolean success = false;
		for (final SendMailCodeHandler handler : handlers) {
			final CommonResult<?> sent = handler.sendCode(email, code.toString(), subject, template);
			if (sent != null && sent.isSuccess()) {
				success = true;
				break;
			}
		}
		if (success) {
			return CommonResult.success("发送成功");
		}
		return CommonResult.fail("发送失败");
	}

	/**
	 * Verify code.
	 *
	 * @param email the email
	 * @param code  the code
	 * @return the common result
	 */
	@Override
	public CommonResult<?> verifyCode(String email, String code) {
		if (!StringUtils.isValidEmail(email)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail1")); //$NON-NLS-1$
		}
		if (StringUtils.isEmpty(code)) {
			return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail2")); //$NON-NLS-1$
		}
		final Object cache = redis.opsForValue().get(email);
		if (Objects.equals(cache, code)) {
			return CommonResult.success(code);
		}
		return CommonResult.fail(MessageUtils.message("mail.service.error.invalidEmail4")); //$NON-NLS-1$
	}

}
