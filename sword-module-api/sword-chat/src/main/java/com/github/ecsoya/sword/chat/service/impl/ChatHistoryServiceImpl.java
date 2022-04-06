package com.github.ecsoya.sword.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.chat.domain.ChatHistory;
import com.github.ecsoya.sword.chat.mapper.ChatHistoryMapper;
import com.github.ecsoya.sword.chat.service.IChatHistoryService;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;

/**
 * The Class ChatHistoryServiceImpl.
 */
@Service
public class ChatHistoryServiceImpl implements IChatHistoryService {

	/** The chat history mapper. */
	@Autowired
	private ChatHistoryMapper chatHistoryMapper;

	/**
	 * Select chat history by id.
	 *
	 * @param id the id
	 * @return the chat history
	 */
	@Override
	public ChatHistory selectChatHistoryById(Long id) {
		return chatHistoryMapper.selectChatHistoryById(id);
	}

	/**
	 * Select chat history list.
	 *
	 * @param chatHistory the chat history
	 * @return the list
	 */
	@Override
	public List<ChatHistory> selectChatHistoryList(ChatHistory chatHistory) {
		return chatHistoryMapper.selectChatHistoryList(chatHistory);
	}

	/**
	 * Insert chat history.
	 *
	 * @param chatHistory the chat history
	 * @return the int
	 */
	@Override
	public int insertChatHistory(ChatHistory chatHistory) {
		if (chatHistory.getId() == null) {
			chatHistory.setId(IdWorker.getId());
		}
		if (chatHistory.getCreateTime() == null) {
			chatHistory.setCreateTime(DateUtils.getNowDate());
		}
		return chatHistoryMapper.insertChatHistory(chatHistory);
	}

	/**
	 * Update chat history.
	 *
	 * @param chatHistory the chat history
	 * @return the int
	 */
	@Override
	public int updateChatHistory(ChatHistory chatHistory) {
		chatHistory.setUpdateTime(DateUtils.getNowDate());
		return chatHistoryMapper.updateChatHistory(chatHistory);
	}

	/**
	 * Delete chat history by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteChatHistoryByIds(String ids) {
		return chatHistoryMapper.deleteChatHistoryByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete chat history by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteChatHistoryById(Long id) {
		return chatHistoryMapper.deleteChatHistoryById(id);
	}
}
