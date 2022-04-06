package com.github.ecsoya.sword.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.chat.domain.ChatRoom;
import com.github.ecsoya.sword.chat.mapper.ChatRoomMapper;
import com.github.ecsoya.sword.chat.service.IChatRoomService;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;

/**
 * The Class ChatRoomServiceImpl.
 */
@Service
public class ChatRoomServiceImpl implements IChatRoomService {

	/** The chat room mapper. */
	@Autowired
	private ChatRoomMapper chatRoomMapper;

	/**
	 * Select chat room by id.
	 *
	 * @param id the id
	 * @return the chat room
	 */
	@Override
	public ChatRoom selectChatRoomById(Long id) {
		return chatRoomMapper.selectChatRoomById(id);
	}

	/**
	 * Select chat room list.
	 *
	 * @param chatRoom the chat room
	 * @return the list
	 */
	@Override
	public List<ChatRoom> selectChatRoomList(ChatRoom chatRoom) {
		return chatRoomMapper.selectChatRoomList(chatRoom);
	}

	/**
	 * Insert chat room.
	 *
	 * @param chatRoom the chat room
	 * @return the int
	 */
	@Override
	public int insertChatRoom(ChatRoom chatRoom) {
		if (chatRoom.getId() == null) {
			chatRoom.setId(IdWorker.getId());
		}
		if (chatRoom.getCreateTime() == null) {
			chatRoom.setCreateTime(DateUtils.getNowDate());
		}
		return chatRoomMapper.insertChatRoom(chatRoom);
	}

	/**
	 * Update chat room.
	 *
	 * @param chatRoom the chat room
	 * @return the int
	 */
	@Override
	public int updateChatRoom(ChatRoom chatRoom) {
		chatRoom.setUpdateTime(DateUtils.getNowDate());
		return chatRoomMapper.updateChatRoom(chatRoom);
	}

	/**
	 * Delete chat room by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteChatRoomByIds(String ids) {
		return chatRoomMapper.deleteChatRoomByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete chat room by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteChatRoomById(Long id) {
		return chatRoomMapper.deleteChatRoomById(id);
	}
}
