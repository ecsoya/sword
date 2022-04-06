package com.github.ecsoya.sword.message.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ecsoya.sword.common.core.text.Convert;
import com.github.ecsoya.sword.common.utils.DateUtils;
import com.github.ecsoya.sword.common.utils.IdWorker;
import com.github.ecsoya.sword.common.utils.StringUtils;
import com.github.ecsoya.sword.common.utils.async.AsyncManager;
import com.github.ecsoya.sword.message.domain.SwordMessage;
import com.github.ecsoya.sword.message.domain.UserMessage;
import com.github.ecsoya.sword.message.mapper.UserMessageMapper;
import com.github.ecsoya.sword.message.service.ISwordMessageService;
import com.github.ecsoya.sword.message.service.IUserMessageService;
import com.github.ecsoya.sword.message.websocket.MessageWebSocketSender;
import com.github.ecsoya.sword.system.domain.SysNotice;
import com.github.ecsoya.sword.system.service.ISysNoticeService;
import com.github.ecsoya.sword.user.service.IUserProfileService;

/**
 * The Class UserMessageServiceImpl.
 */
@Service
public class UserMessageServiceImpl implements IUserMessageService {

	/** The message service. */
	@Autowired
	private ISwordMessageService messageService;

	/** The user message mapper. */
	@Autowired
	private UserMessageMapper userMessageMapper;

	/** The user service. */
	@Autowired
	private IUserProfileService userService;

	/** The message sender. */
	@Autowired
	private MessageWebSocketSender messageSender;

	/** The notice service. */
	@Autowired
	private ISysNoticeService noticeService;

	/**
	 * Select user message by id.
	 *
	 * @param id the id
	 * @return the user message
	 */
	@Override
	public UserMessage selectUserMessageById(Long id) {
		return updateMessageContent(userMessageMapper.selectUserMessageById(id));
	}

	/**
	 * Select user message list.
	 *
	 * @param userMessage the user message
	 * @return the list
	 */
	@Override
	public List<UserMessage> selectUserMessageList(UserMessage userMessage) {
		List<UserMessage> list = userMessageMapper.selectUserMessageList(userMessage);
		return list.parallelStream().map(msg -> updateMessageContent(msg)).collect(Collectors.toList());
	}

	/**
	 * Update message content.
	 *
	 * @param msg the msg
	 * @return the user message
	 */
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
					msg.setCreateBy(notice.getCreateBy());
					msg.setCreateTime(notice.getCreateTime());
				}
			} else {
				SwordMessage m = messageService.selectSwordMessageById(messageId);
				if (m != null) {
					msg.setContent(m.getContent());
					msg.setTitle(m.getTitle());
					msg.setCreateBy(m.getCreateBy());
					msg.setCreateTime(m.getCreateTime());
				}
			}
		}
		return msg;
	}

	/**
	 * Insert user message.
	 *
	 * @param userMessage the user message
	 * @return the int
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
	 * Update user message.
	 *
	 * @param userMessage the user message
	 * @return the int
	 */
	@Override
	public int updateUserMessage(UserMessage userMessage) {
		userMessage.setUpdateTime(DateUtils.getNowDate());
		return userMessageMapper.updateUserMessage(userMessage);
	}

	/**
	 * Delete user message by ids.
	 *
	 * @param ids the ids
	 * @return the int
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
	 * Delete user message by id.
	 *
	 * @param id the id
	 * @return the int
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

	/**
	 * Select user message unread count.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int selectUserMessageUnreadCount(Long userId) {
		if (userId == null) {
			return 0;
		}
		Integer count = userMessageMapper.selectUserMessageUnreadCount(userId);
		return count == null ? 0 : count.intValue();
	}

	/**
	 * Publish user messages.
	 *
	 * @param messageId the message id
	 * @param type      the type
	 * @param userIds   the user ids
	 * @return the int
	 */
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

	/**
	 * Notify message sender.
	 *
	 * @param message the message
	 * @param userIds the user ids
	 */
	private void notifyMessageSender(String message, Long[] userIds) {
		if (messageSender == null) {
			return;
		}
		AsyncManager.me().execute(new Runnable() {

			@Override
			public void run() {
				if (userIds != null && userIds.length > 0) {
					messageSender.asyncDispatchMessage(message, userIds);
				} else {
					messageSender.asyncDispatchMessage(message);
				}
			}
		});
	}

	/**
	 * Publish user messages.
	 *
	 * @param message the message
	 * @param userIds the user ids
	 * @return the int
	 */
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

	/**
	 * Read user message by ids.
	 *
	 * @param userId the user id
	 * @param ids    the ids
	 * @return the int
	 */
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

	/**
	 * Read user message by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
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

	/**
	 * Removes the all user message by user id.
	 *
	 * @param userId the user id
	 * @return the int
	 */
	@Override
	public int removeAllUserMessageByUserId(Long userId) {
		if (userId == null) {
			return 0;
		}
		userMessageMapper.deleteUserMessageByUserId(userId);
		return 1;
	}
}
