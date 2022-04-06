package com.github.ecsoya.sword.chat.service;

import java.util.List;

import com.github.ecsoya.sword.chat.domain.ChatHistory;

/**
 * The Interface IChatHistoryService.
 */
public interface IChatHistoryService {

	/**
	 * Select chat history by id.
	 *
	 * @param id the id
	 * @return the chat history
	 */
	public ChatHistory selectChatHistoryById(Long id);

	/**
	 * Select chat history list.
	 *
	 * @param chatHistory the chat history
	 * @return the list
	 */
	public List<ChatHistory> selectChatHistoryList(ChatHistory chatHistory);

	/**
	 * Insert chat history.
	 *
	 * @param chatHistory the chat history
	 * @return the int
	 */
	public int insertChatHistory(ChatHistory chatHistory);

	/**
	 * Update chat history.
	 *
	 * @param chatHistory the chat history
	 * @return the int
	 */
	public int updateChatHistory(ChatHistory chatHistory);

	/**
	 * Delete chat history by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteChatHistoryByIds(String ids);

	/**
	 * Delete chat history by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteChatHistoryById(Long id);
}
