package com.github.ecsoya.sword.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.chat.domain.ChatMember;
import com.github.ecsoya.sword.chat.mapper.ChatMemberMapper;
import com.github.ecsoya.sword.chat.service.IChatMemberService;
import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.IdWorker;

/**
 * 聊天成员Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
@Service
public class ChatMemberServiceImpl implements IChatMemberService {
	@Autowired
	private ChatMemberMapper chatMemberMapper;

	/**
	 * 查询聊天成员
	 * 
	 * @param id 聊天成员ID
	 * @return 聊天成员
	 */
	@Override
	public ChatMember selectChatMemberById(Long id) {
		return chatMemberMapper.selectChatMemberById(id);
	}

	/**
	 * 查询聊天成员列表
	 * 
	 * @param chatMember 聊天成员
	 * @return 聊天成员
	 */
	@Override
	public List<ChatMember> selectChatMemberList(ChatMember chatMember) {
		return chatMemberMapper.selectChatMemberList(chatMember);
	}

	/**
	 * 新增聊天成员
	 * 
	 * @param chatMember 聊天成员
	 * @return 结果
	 */
	@Override
	public int insertChatMember(ChatMember chatMember) {
		if (chatMember.getId() == null) {
			chatMember.setId(IdWorker.getId());
		}
		return chatMemberMapper.insertChatMember(chatMember);
	}

	/**
	 * 修改聊天成员
	 * 
	 * @param chatMember 聊天成员
	 * @return 结果
	 */
	@Override
	public int updateChatMember(ChatMember chatMember) {
		return chatMemberMapper.updateChatMember(chatMember);
	}

	/**
	 * 删除聊天成员对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteChatMemberByIds(String ids) {
		return chatMemberMapper.deleteChatMemberByIds(Convert.toStrArray(ids));
	}

	/**
	 * 删除聊天成员信息
	 * 
	 * @param id 聊天成员ID
	 * @return 结果
	 */
	@Override
	public int deleteChatMemberById(Long id) {
		return chatMemberMapper.deleteChatMemberById(id);
	}
}
