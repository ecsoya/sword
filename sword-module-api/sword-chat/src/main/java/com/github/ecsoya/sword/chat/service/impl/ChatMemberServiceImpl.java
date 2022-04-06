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
 * The Class ChatMemberServiceImpl.
 */
@Service
public class ChatMemberServiceImpl implements IChatMemberService {

	/** The chat member mapper. */
	@Autowired
	private ChatMemberMapper chatMemberMapper;

	/**
	 * Select chat member by id.
	 *
	 * @param id the id
	 * @return the chat member
	 */
	@Override
	public ChatMember selectChatMemberById(Long id) {
		return chatMemberMapper.selectChatMemberById(id);
	}

	/**
	 * Select chat member list.
	 *
	 * @param chatMember the chat member
	 * @return the list
	 */
	@Override
	public List<ChatMember> selectChatMemberList(ChatMember chatMember) {
		return chatMemberMapper.selectChatMemberList(chatMember);
	}

	/**
	 * Insert chat member.
	 *
	 * @param chatMember the chat member
	 * @return the int
	 */
	@Override
	public int insertChatMember(ChatMember chatMember) {
		if (chatMember.getId() == null) {
			chatMember.setId(IdWorker.getId());
		}
		return chatMemberMapper.insertChatMember(chatMember);
	}

	/**
	 * Update chat member.
	 *
	 * @param chatMember the chat member
	 * @return the int
	 */
	@Override
	public int updateChatMember(ChatMember chatMember) {
		return chatMemberMapper.updateChatMember(chatMember);
	}

	/**
	 * Delete chat member by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	@Override
	public int deleteChatMemberByIds(String ids) {
		return chatMemberMapper.deleteChatMemberByIds(Convert.toStrArray(ids));
	}

	/**
	 * Delete chat member by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	@Override
	public int deleteChatMemberById(Long id) {
		return chatMemberMapper.deleteChatMemberById(id);
	}
}
