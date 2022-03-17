package com.github.ecsoya.sword.chat.service;

import java.util.List;

import com.github.ecsoya.sword.chat.domain.ChatRoom;

/**
 * 聊天室Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
public interface IChatRoomService {
	/**
	 * 查询聊天室
	 * 
	 * @param id 聊天室ID
	 * @return 聊天室
	 */
	public ChatRoom selectChatRoomById(Long id);

	/**
	 * 查询聊天室列表
	 * 
	 * @param chatRoom 聊天室
	 * @return 聊天室集合
	 */
	public List<ChatRoom> selectChatRoomList(ChatRoom chatRoom);

	/**
	 * 新增聊天室
	 * 
	 * @param chatRoom 聊天室
	 * @return 结果
	 */
	public int insertChatRoom(ChatRoom chatRoom);

	/**
	 * 修改聊天室
	 * 
	 * @param chatRoom 聊天室
	 * @return 结果
	 */
	public int updateChatRoom(ChatRoom chatRoom);

	/**
	 * 批量删除聊天室
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteChatRoomByIds(String ids);

	/**
	 * 删除聊天室信息
	 * 
	 * @param id 聊天室ID
	 * @return 结果
	 */
	public int deleteChatRoomById(Long id);
}
