package com.soyatec.sword.message;

public interface IMessageService {

	public int publishUserMessages(Long messageId, Integer type, Long... userIds);

	public int publishUserMessages(String message, Long[] userIds);
}
