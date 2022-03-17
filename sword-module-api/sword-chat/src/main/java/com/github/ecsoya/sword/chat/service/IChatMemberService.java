package com.github.ecsoya.sword.chat.service;

import java.util.List;

import com.github.ecsoya.sword.chat.domain.ChatMember;

/**
 * 聊天成员Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-06-03
 */
public interface IChatMemberService {
	/**
	 * 查询聊天成员
	 * 
	 * @param id 聊天成员ID
	 * @return 聊天成员
	 */
	public ChatMember selectChatMemberById(Long id);

	/**
	 * 查询聊天成员列表
	 * 
	 * @param chatMember 聊天成员
	 * @return 聊天成员集合
	 */
	public List<ChatMember> selectChatMemberList(ChatMember chatMember);

	/**
	 * 新增聊天成员
	 * 
	 * @param chatMember 聊天成员
	 * @return 结果
	 */
	public int insertChatMember(ChatMember chatMember);

	/**
	 * 修改聊天成员
	 * 
	 * @param chatMember 聊天成员
	 * @return 结果
	 */
	public int updateChatMember(ChatMember chatMember);

	/**
	 * 批量删除聊天成员
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteChatMemberByIds(String ids);

	/**
	 * 删除聊天成员信息
	 * 
	 * @param id 聊天成员ID
	 * @return 结果
	 */
	public int deleteChatMemberById(Long id);
}
