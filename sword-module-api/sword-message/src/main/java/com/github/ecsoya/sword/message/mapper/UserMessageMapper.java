package com.github.ecsoya.sword.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.ecsoya.sword.message.domain.UserMessage;

/**
 * The Interface UserMessageMapper.
 */
public interface UserMessageMapper {

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
	 * Delete user message by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUserMessageById(Long id);

	/**
	 * Delete user message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteUserMessageByIds(String[] ids);

	/**
	 * Select user message unread count.
	 *
	 * @param userId the user id
	 * @return the integer
	 */
	public Integer selectUserMessageUnreadCount(Long userId);

	/**
	 * Insert user message batch.
	 *
	 * @param messages the messages
	 * @return the int
	 */
	public int insertUserMessageBatch(@Param("messages") List<UserMessage> messages);

	/**
	 * Read user message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int readUserMessageByIds(String ids);

	/**
	 * Read user message by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	public int readUserMessageByUserId(Long userId);

	/**
	 * Read user message by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int readUserMessageByIds(String[] ids);

	/**
	 * Delete user message by user id.
	 *
	 * @param userId the user id
	 */
	public void deleteUserMessageByUserId(Long userId);
}
