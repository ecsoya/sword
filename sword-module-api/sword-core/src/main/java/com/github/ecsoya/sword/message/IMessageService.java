package com.github.ecsoya.sword.message;

/**
 * The Interface IMessageService.
 */
public interface IMessageService {

	/**
	 * Publish user messages.
	 *
	 * @param messageId the message id
	 * @param type      the type
	 * @param userIds   the user ids
	 * @return the int
	 */
	public int publishUserMessages(Long messageId, Integer type, Long... userIds);

	/**
	 * Publish user messages.
	 *
	 * @param message the message
	 * @param userIds the user ids
	 * @return the int
	 */
	public int publishUserMessages(String message, Long[] userIds);
}
