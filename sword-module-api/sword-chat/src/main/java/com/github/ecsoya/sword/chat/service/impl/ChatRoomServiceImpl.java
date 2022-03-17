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
 * 聊天室Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
@Service
public class ChatRoomServiceImpl implements IChatRoomService {
	@Autowired
	private ChatRoomMapper chatRoomMapper;

	/**
	 * 查询聊天室
	 * 
	 * @param id 聊天室ID
	 * @return 聊天室
	 */
	@Override
	public ChatRoom selectChatRoomById(Long id) {
		return chatRoomMapper.selectChatRoomById(id);
	}

	/**
	 * 查询聊天室列表
	 * 
	 * @param chatRoom 聊天室
	 * @return 聊天室
	 */
	@Override
	public List<ChatRoom> selectChatRoomList(ChatRoom chatRoom) {
		return chatRoomMapper.selectChatRoomList(chatRoom);
	}

	/**
	 * 新增聊天室
	 * 
	 * @param chatRoom 聊天室
	 * @return 结果
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
	 * 修改聊天室
	 * 
	 * @param chatRoom 聊天室
	 * @return 结果
	 */
	@Override
	public int updateChatRoom(ChatRoom chatRoom) {
		chatRoom.setUpdateTime(DateUtils.getNowDate());
		return chatRoomMapper.updateChatRoom(chatRoom);
	}

	/**
	 * 删除聊天室对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteChatRoomByIds(String ids) {
		return chatRoomMapper.deleteChatRoomByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除聊天室信息
	 * 
	 * @param id 聊天室ID
	 * @return 结果
	 */
	@Override
	public int deleteChatRoomById(Long id) {
		return chatRoomMapper.deleteChatRoomById(id);
	}
}
