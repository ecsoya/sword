package com.soyatec.sword.message.service;

import java.util.List;

import com.soyatec.sword.message.IMessageService;
import com.soyatec.sword.message.domain.UserMessage;

/**
 * 用户消息Service接口
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-05-20
 */
public interface IUserMessageService extends IMessageService {
	/**
	 * 查询用户消息
	 * 
	 * @param id 用户消息ID
	 * @return 用户消息
	 */
	public UserMessage selectUserMessageById(Long id);

	/**
	 * 查询用户消息列表
	 * 
	 * @param userMessage 用户消息
	 * @return 用户消息集合
	 */
	public List<UserMessage> selectUserMessageList(UserMessage userMessage);

	/**
	 * 新增用户消息
	 * 
	 * @param userMessage 用户消息
	 * @return 结果
	 */
	public int insertUserMessage(UserMessage userMessage);

	/**
	 * 修改用户消息
	 * 
	 * @param userMessage 用户消息
	 * @return 结果
	 */
	public int updateUserMessage(UserMessage userMessage);

	/**
	 * 批量删除用户消息
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	public int deleteUserMessageByIds(String ids);

	/**
	 * 删除用户消息信息
	 * 
	 * @param id 用户消息ID
	 * @return 结果
	 */
	public int deleteUserMessageById(Long id);

	public int selectUserMessageUnreadCount(Long userId);

	public int publishUserMessages(Long messageId, Integer type, Long... userIds);

	public int readUserMessageByIds(Long userId, String ids);

	public int readUserMessageByUserId(Long userId);
}
