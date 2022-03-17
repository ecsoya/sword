package com.github.ecsoya.sword.message;

public interface IMessageService {

	public int publishUserMessages(Long messageId, Integer type, Long... userIds);

	public int publishUserMessages(String message, Long[] userIds);
}
