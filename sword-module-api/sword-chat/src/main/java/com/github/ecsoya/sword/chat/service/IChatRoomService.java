package com.github.ecsoya.sword.chat.service;

import java.util.List;

import com.github.ecsoya.sword.chat.domain.ChatRoom;

/**
 * The Interface IChatRoomService.
 */
public interface IChatRoomService {

	/**
	 * Select chat room by id.
	 *
	 * @param id the id
	 * @return the chat room
	 */
	public ChatRoom selectChatRoomById(Long id);

	/**
	 * Select chat room list.
	 *
	 * @param chatRoom the chat room
	 * @return the list
	 */
	public List<ChatRoom> selectChatRoomList(ChatRoom chatRoom);

	/**
	 * Insert chat room.
	 *
	 * @param chatRoom the chat room
	 * @return the int
	 */
	public int insertChatRoom(ChatRoom chatRoom);

	/**
	 * Update chat room.
	 *
	 * @param chatRoom the chat room
	 * @return the int
	 */
	public int updateChatRoom(ChatRoom chatRoom);

	/**
	 * Delete chat room by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteChatRoomByIds(String ids);

	/**
	 * Delete chat room by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteChatRoomById(Long id);
}
