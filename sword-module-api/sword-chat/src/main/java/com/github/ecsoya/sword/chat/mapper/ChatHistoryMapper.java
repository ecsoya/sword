package com.github.ecsoya.sword.chat.mapper;

import java.util.List;

import com.github.ecsoya.sword.chat.domain.ChatHistory;

/**
 * 聊天记录Mapper接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
public interface ChatHistoryMapper {
	/**
	 * 查询聊天记录
	 * 
	 * @param id 聊天记录ID
	 * @return 聊天记录
	 */
	public ChatHistory selectChatHistoryById(Long id);

	/**
	 * 查询聊天记录列表
	 * 
	 * @param chatHistory 聊天记录
	 * @return 聊天记录集合
	 */
	public List<ChatHistory> selectChatHistoryList(ChatHistory chatHistory);

	/**
	 * 新增聊天记录
	 * 
	 * @param chatHistory 聊天记录
	 * @return 结果
	 */
	public int insertChatHistory(ChatHistory chatHistory);

	/**
	 * 修改聊天记录
	 * 
	 * @param chatHistory 聊天记录
	 * @return 结果
	 */
	public int updateChatHistory(ChatHistory chatHistory);

	/**
	 * 删除聊天记录
	 * 
	 * @param id 聊天记录ID
	 * @return 结果
	 */
	public int deleteChatHistoryById(Long id);

	/**
	 * 批量删除聊天记录
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteChatHistoryByIds(String[] ids);
}
