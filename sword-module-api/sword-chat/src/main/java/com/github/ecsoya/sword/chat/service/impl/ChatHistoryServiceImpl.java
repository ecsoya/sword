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
 * 聊天记录Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
@Service
public class ChatHistoryServiceImpl implements IChatHistoryService {
	@Autowired
	private ChatHistoryMapper chatHistoryMapper;

	/**
	 * 查询聊天记录
	 * 
	 * @param id 聊天记录ID
	 * @return 聊天记录
	 */
	@Override
	public ChatHistory selectChatHistoryById(Long id) {
		return chatHistoryMapper.selectChatHistoryById(id);
	}

	/**
	 * 查询聊天记录列表
	 * 
	 * @param chatHistory 聊天记录
	 * @return 聊天记录
	 */
	@Override
	public List<ChatHistory> selectChatHistoryList(ChatHistory chatHistory) {
		return chatHistoryMapper.selectChatHistoryList(chatHistory);
	}

	/**
	 * 新增聊天记录
	 * 
	 * @param chatHistory 聊天记录
	 * @return 结果
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
	 * 修改聊天记录
	 * 
	 * @param chatHistory 聊天记录
	 * @return 结果
	 */
	@Override
	public int updateChatHistory(ChatHistory chatHistory) {
		chatHistory.setUpdateTime(DateUtils.getNowDate());
		return chatHistoryMapper.updateChatHistory(chatHistory);
	}

	/**
	 * 删除聊天记录对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteChatHistoryByIds(String ids) {
		return chatHistoryMapper.deleteChatHistoryByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除聊天记录信息
	 * 
	 * @param id 聊天记录ID
	 * @return 结果
	 */
	@Override
	public int deleteChatHistoryById(Long id) {
		return chatHistoryMapper.deleteChatHistoryById(id);
	}
}
