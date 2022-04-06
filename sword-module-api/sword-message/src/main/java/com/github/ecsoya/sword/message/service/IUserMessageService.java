package com.github.ecsoya.sword.message.service;

import java.util.List;

import com.github.ecsoya.sword.message.IMessageService;
import com.github.ecsoya.sword.message.domain.UserMessage;

/**
 * The Interface IUserMessageService.
 */
public interface IUserMessageService extends IMessageService {

	/**
	 * Select user message by id.
	 *
	 * @param id the id
	 * @return the user message
	 */
	public UserMessage selectUserMessageById(Long id);

	/**
	 * Select user message list.
	 *
	 * @param userMessage the user message
	 * @return the list
	 */
	public List<UserMessage> selectUserMessageList(UserMessage userMessage);

	/**
	 * Insert user message.
	 *
	 * @param userMessage the user message
	 * @return the int
	 */
	public int insertUserMessage(UserMessage userMessage);

	/**
	 * Update user message.
	 *
	 * @param userMessage the user message
	 * @return the int
	 */
	public int updateUserMessage(UserMessage userMessage);

	/**
	 * Delete user message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserMessageByIds(String ids);

	/**
	 * Delete user message by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserMessageById(Long id);

	/**
	 * Select user message unread count.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int selectUserMessageUnreadCount(Long userId);

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
	 * Read user message by ids.
	 *
	 * @param userId the user id
	 * @param ids    the ids
	 * @return the int
	 */
	public int readUserMessageByIds(Long userId, String ids);

	/**
	 * Read user message by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int readUserMessageByUserId(Long userId);

	/**
	 * Removes the all user message by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int removeAllUserMessageByUserId(Long userId);
}
