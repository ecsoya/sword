package com.github.ecsoya.sword.chat.service;

import java.util.List;

import com.github.ecsoya.sword.chat.domain.ChatMember;

/**
 * The Interface IChatMemberService.
 */
public interface IChatMemberService {

	/**
	 * Select chat member by id.
	 *
	 * @param id the id
	 * @return the chat member
	 */
	public ChatMember selectChatMemberById(Long id);

	/**
	 * Select chat member list.
	 *
	 * @param chatMember the chat member
	 * @return the list
	 */
	public List<ChatMember> selectChatMemberList(ChatMember chatMember);

	/**
	 * Insert chat member.
	 *
	 * @param chatMember the chat member
	 * @return the int
	 */
	public int insertChatMember(ChatMember chatMember);

	/**
	 * Update chat member.
	 *
	 * @param chatMember the chat member
	 * @return the int
	 */
	public int updateChatMember(ChatMember chatMember);

	/**
	 * Delete chat member by ids.
	 *
	 * @param ids the ids
	 * @return the int
	 */
	public int deleteChatMemberByIds(String ids);

	/**
	 * Delete chat member by id.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteChatMemberById(Long id);
}
