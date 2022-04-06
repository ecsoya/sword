package com.github.ecsoya.sword.message.service.impl;

import org.springframework.beans.BeansException;

import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.message.IMessageService;
import com.github.ecsoya.sword.message.service.IUserMessageService;
import com.google.auto.service.AutoService;

/**
 * The Class MessageServiceImpl.
 */
@AutoService(IMessageService.class)
public class MessageServiceImpl implements IMessageService {

	/**
	 * Publish user messages.
	 *
	 * @param messageId the message id
	 * @param type      the type
	 * @param userIds   the user ids
	 * @return the int
	 */
	@Override
	public int publishUserMessages(Long messageId, Integer type, Long... userIds) {
		try {
			IUserMessageService bean = SpringUtils.getBean(IUserMessageService.class);
			return bean.publishUserMessages(messageId, type, userIds);
		} catch (BeansException e) {
			return 0;
		}
	}

	/**
	 * Publish user messages.
	 *
	 * @param message the message
	 * @param userIds the user ids
	 * @return the int
	 */
	@Override
	public int publishUserMessages(String message, Long[] userIds) {
		try {
			IUserMessageService bean = SpringUtils.getBean(IUserMessageService.class);
			return bean.publishUserMessages(message, userIds);
		} catch (BeansException e) {
			return 0;
		}
	}

}
