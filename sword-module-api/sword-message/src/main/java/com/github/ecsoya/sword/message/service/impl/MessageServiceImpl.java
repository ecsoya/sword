package com.github.ecsoya.sword.message.service.impl;

import org.springframework.beans.BeansException;

import com.github.ecsoya.sword.common.utils.spring.SpringUtils;
import com.github.ecsoya.sword.message.IMessageService;
import com.github.ecsoya.sword.message.service.IUserMessageService;
import com.google.auto.service.AutoService;

@AutoService(IMessageService.class)
public class MessageServiceImpl implements IMessageService {

	@Override
	public int publishUserMessages(Long messageId, Integer type, Long... userIds) {
		try {
			IUserMessageService bean = SpringUtils.getBean(IUserMessageService.class);
			return bean.publishUserMessages(messageId, type, userIds);
		} catch (BeansException e) {
			return 0;
		}
	}

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
