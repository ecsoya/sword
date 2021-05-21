package com.soyatec.sword.message.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soyatec.sword.common.core.text.Convert;
import com.soyatec.sword.common.utils.DateUtils;
import com.soyatec.sword.common.utils.IdWorker;
import com.soyatec.sword.common.utils.StringUtils;
import com.soyatec.sword.common.utils.async.AsyncManager;
import com.soyatec.sword.message.domain.SwordMessage;
import com.soyatec.sword.message.domain.UserMessage;
import com.soyatec.sword.message.mapper.UserMessageMapper;
import com.soyatec.sword.message.service.ISwordMessageService;
import com.soyatec.sword.message.service.IUserMessageService;
import com.soyatec.sword.message.websocket.MessageWebSocketSender;
import com.soyatec.sword.system.domain.SysNotice;
import com.soyatec.sword.system.service.ISysNoticeService;
import com.soyatec.sword.user.service.IUserProfileService;

/**
 * 用户消息Service业务层处理
 * 
 * @author Jin Liu (angryred@qq.com)
 * @date 2021-05-20
 */
@Service
public class UserMessageServiceImpl implements IUserMessageService {

	@Autowired
	private ISwordMessageService messageService;

	@Autowired
	private UserMessageMapper userMessageMapper;

	@Autowired
	private IUserProfileService userService;

	@Autowired
	private MessageWebSocketSender messageSender;

	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * 查询用户消息
	 * 
	 * @param id 用户消息ID
	 * @return 用户消息
	 */
	@Override
	public UserMessage selectUserMessageById(Long id) {
		return updateMessageContent(userMessageMapper.selectUserMessageById(id));
	}

	/**
	 * 查询用户消息列表
	 * 
	 * @param userMessage 用户消息
	 * @return 用户消息
	 */
	@Override
	public List<UserMessage> selectUserMessageList(UserMessage userMessage) {
		List<UserMessage> list = userMessageMapper.selectUserMessageList(userMessage);
		return list.parallelStream().map(msg -> updateMessageContent(msg)).collect(Collectors.toList());
	}

	private UserMessage updateMessageContent(UserMessage msg) {
		Integer type = msg.getType();
		Long messageId = msg.getMessageId();
		if (type != null && messageId != null) {
			if (UserMessage.TYPE_INFORM.equals(type) || UserMessage.TYPE_NOTICE.equals(type)) {
				SysNotice notice = noticeService.selectNoticeById(messageId);
				if (notice != null) {
					if (UserMessage.TYPE_INFORM.equals(type)) {
						msg.setContent(notice.getNoticeContent());
						msg.setTitle(notice.getNoticeTitle());
					} else {
						msg.setTitle("系统公告");
						msg.setContent(notice.getNoticeTitle());
					}
				}
			} else {
				SwordMessage m = messageService.selectSwordMessageById(messageId);
				if (m != null) {
					msg.setContent(m.getContent());
					msg.setTitle(m.getTitle());
				}
			}
		}
		return msg;
	}

	/**
	 * 新增用户消息
	 * 
	 * @param userMessage 用户消息
	 * @return 结果
	 */
	@Override
	public int insertUserMessage(UserMessage userMessage) {
		if (userMessage.getId() == null) {
			userMessage.setId(IdWorker.getId());
		}
		if (userMessage.getCreateTime() == null) {
			userMessage.setCreateTime(DateUtils.getNowDate());
		}
		return userMessageMapper.insertUserMessage(userMessage);
	}

	/**
	 * 修改用户消息
	 * 
	 * @param userMessage 用户消息
	 * @return 结果
	 */
	@Override
	public int updateUserMessage(UserMessage userMessage) {
		userMessage.setUpdateTime(DateUtils.getNowDate());
		return userMessageMapper.updateUserMessage(userMessage);
	}

	/**
	 * 删除用户消息对象
	 * 
	 * @param ids 需要删除的数据ID
	 * @return 结果
	 */
	@Override
	public int deleteUserMessageByIds(String ids) {
		String[] array = Convert.toStrArray(ids);
		if (array.length == 0) {
			return 0;
		}
		UserMessage msg = userMessageMapper.selectUserMessageById(Long.parseLong(array[0]));
		int rows = userMessageMapper.deleteUserMessageByIds(array);
		if (rows > 0 && msg != null) {
			notifyMessageSender("删除消息", new Long[] { msg.getUserId() });
		}
		return rows;
	}

	/**
	 * 删除用户消息信息
	 * 
	 * @param id 用户消息ID
	 * @return 结果
	 */
	@Override
	public int deleteUserMessageById(Long id) {
		UserMessage msg = userMessageMapper.selectUserMessageById(id);
		int rows = userMessageMapper.deleteUserMessageById(id);
		if (rows > 0 && msg != null) {
			notifyMessageSender("删除消息", new Long[] { msg.getUserId() });
		}
		return rows;
	}

	@Override
	public int selectUserMessageUnreadCount(Long userId) {
		if (userId == null) {
			return 0;
		}
		Integer count = userMessageMapper.selectUserMessageUnreadCount(userId);
		return count == null ? 0 : count.intValue();
	}

	@Override
	public int publishUserMessages(Long messageId, Integer type, Long... userIds) {
		if (messageId == null || type == null) {
			return 0;
		}
		List<Long> users = null;
		if (userIds != null && userIds.length != 0) {
			users = Arrays.asList(userIds);
		} else {
			users = userService.selectUserIdsByType(null);
		}
		if (users.isEmpty()) {
			return -1;
		}
		List<UserMessage> messages = users.parallelStream().map(userId -> {
			UserMessage msg = new UserMessage();
			msg.setUserId(userId);
			msg.setMessageId(messageId);
			msg.setType(type);
			return msg;
		}).collect(Collectors.toList());
		int rows = userMessageMapper.insertUserMessageBatch(messages);
		if (rows > 0) {
			notifyMessageSender("群发消息", users.toArray(new Long[users.size()]));
		}
		return rows;
	}

	private void notifyMessageSender(String message, Long[] userIds) {
		if (messageSender == null) {
			return;
		}
		AsyncManager.me().execute(new Runnable() {

			@Override
			public void run() {
				if (userIds != null && userIds.length > 0) {
					messageSender.dispatchMessage(message, userIds);
				} else {
					messageSender.dispatchMessage(message);
				}
			}
		});
	}

	@Override
	public int publishUserMessages(String message, Long[] userIds) {
		if (StringUtils.isEmpty(message)) {
			return 0;
		}
		SwordMessage msg = new SwordMessage();
		msg.setContent(message);
		msg.setType(SwordMessage.TYPE_MESSAGE);
		int rows = messageService.insertSwordMessage(msg);
		if (rows > 0) {
			return publishUserMessages(msg.getId(), UserMessage.TYPE_MESSAGE, userIds);
		}
		return rows;
	}

	@Override
	public int readUserMessageByIds(Long userId, String ids) {
		if (StringUtils.isEmpty(ids)) {
			return 0;
		}
		int rows = userMessageMapper.readUserMessageByIds(Convert.toStrArray(ids));
		if (rows > 0 && userId != null) {
			notifyMessageSender("已读消息", new Long[] { userId });
		}
		return rows;
	}

	@Override
	public int readUserMessageByUserId(Long userId) {
		if (userId == null) {
			return 0;
		}
		int rows = userMessageMapper.readUserMessageByUserId(userId);
		if (rows > 0) {
			notifyMessageSender("已读消息", new Long[] { userId });
		}
		return rows;
	}
}
