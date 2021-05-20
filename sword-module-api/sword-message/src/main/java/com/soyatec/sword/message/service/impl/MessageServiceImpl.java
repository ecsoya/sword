package com.soyatec.sword.message.service.impl;

import org.springframework.beans.BeansException;

import com.google.auto.service.AutoService;
import com.soyatec.sword.common.utils.spring.SpringUtils;
import com.soyatec.sword.message.IMessageService;
import com.soyatec.sword.message.service.IUserMessageService;

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
